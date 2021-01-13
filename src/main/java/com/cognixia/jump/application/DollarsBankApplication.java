package com.cognixia.jump.application;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

import org.fusesource.jansi.AnsiConsole;

import com.cognixia.jump.application.parse.InputField;
import com.cognixia.jump.application.parse.ParseChecks;
import com.cognixia.jump.dao.AccountDAO;
import com.cognixia.jump.dao.CheckingAccountDAO;
import com.cognixia.jump.dao.CustomerDAO;
import com.cognixia.jump.dao.SavingsAccountDAO;
import com.cognixia.jump.dao.TransactionDAO;
import com.cognixia.jump.dao.exceptions.NotFoundException;
import com.cognixia.jump.dao.exceptions.UsernameUnavailableException;
import com.cognixia.jump.dao.impl.AccountDAOImpl;
import com.cognixia.jump.dao.impl.CheckingAccountDAOImpl;
import com.cognixia.jump.dao.impl.CustomerDAOImpl;
import com.cognixia.jump.dao.impl.SavingsAccountDAOImpl;
import com.cognixia.jump.dao.impl.TransactionDAOImpl;
import com.cognixia.jump.model.Account;
import com.cognixia.jump.model.CheckingAccount;
import com.cognixia.jump.model.Customer;
import com.cognixia.jump.model.SavingsAccount;
import com.cognixia.jump.model.Transaction;

public class DollarsBankApplication {
	
	private static AccountDAO accountDAO = new AccountDAOImpl();
	private static CheckingAccountDAO checkingAccountDAO = new CheckingAccountDAOImpl();
	private static CustomerDAO customerDAO = new CustomerDAOImpl();
	private static SavingsAccountDAO savingsAccountDAO = new SavingsAccountDAOImpl();
	private static TransactionDAO transactionDAO = new TransactionDAOImpl();
	
	private static Scanner sc;

	public static void main(String[] args) {
		/*********************************************
		 * RESOURCE INITIALIZATION
		 *********************************************/
		AnsiConsole.systemInstall();
		sc = new Scanner(System.in);
		String header;
		LinkedHashMap<InputField, Function<String, Object>> fields = new LinkedHashMap<InputField, Function<String, Object>>();
		List<Object> fieldInput;
		ArrayList<String> menu = new ArrayList<String>();
		ArrayList<String> results = new ArrayList<String>();
		Account currentSession = null;
		CheckingAccount currentChecking = null;
		SavingsAccount currentSavings = null;
		Customer currentCustomer = null;
		int accountType = 0;
		long amount = 0;
		/*********************************************
		 * BEGIN MAIN BODY
		 *********************************************/
		
		
		
		boolean sessionEnd = false;
		while (!sessionEnd) {
			if (currentSession != null) {
				try {
					if (currentChecking == null) {
						currentChecking = checkingAccountDAO.getCheckingAccountByUserId(currentSession.getUserId());
					}
					if (currentSavings == null) {
						currentSavings = savingsAccountDAO.getSavingsAccountByUserId(currentSession.getUserId());
					}
					if (currentCustomer == null) {
						currentCustomer = customerDAO.getCustomerByUserId(currentSession.getUserId());
					}
				} catch (NotFoundException e) {
					printError("Internal error: matching user data not found for user \"" + currentSession.getUserId() + "\". Exiting session...");
					pauseEnter();
					currentSession = null;
					currentChecking = null;
					currentSavings = null;
					currentCustomer = null;
					continue;
				}
				header = "WELCOME " + currentSession.getUserId() + "!!!";
				menu.clear();
				menu.add("Deposit Amount");
				menu.add("Withdraw Amount");
				menu.add("Funds Transfer");
				menu.add("View 5 Recent Transactions");
				menu.add("Display Customer Info");
				menu.add("Sign Out");
				int i = printMenuPage(header, menu);
				switch (i) {
				case 1:
					header = "Deposit Amount";
					fields.clear();
					fields.put(new InputField("Amount", 0, 3000000000l), (Long::parseLong));
					fields.put(new InputField("Account Type (checking or savings)"), (ParseChecks::isCheckingSavings));
					fieldInput = printFieldPage(header, fields);
					accountType = (Integer)fieldInput.get(1);
					Transaction td = new Transaction(0, currentSession.getUserId(), new Date(System.currentTimeMillis()), 1, accountType, (Long)fieldInput.get(0));
					if (accountType == 1) {
						currentChecking.setBalance(currentChecking.getBalance() + (Long)fieldInput.get(0));
						checkingAccountDAO.updateCheckingAccount(currentChecking);
					} else {
						currentSavings.setBalance(currentSavings.getBalance() + (Long)fieldInput.get(0));
						savingsAccountDAO.updateSavingsAccount(currentSavings);
					}
					transactionDAO.addTransaction(td);
					printSuccess("Deposit successful.");
					pauseEnter();
					break;
				case 2:
					header = "Withdraw Amount";
					fields.clear();
					fields.put(new InputField("Amount", -3000000000l, 0), (ParseChecks::parseWithdrawal));
					fields.put(new InputField("Account Type (checking or savings)"), (ParseChecks::isCheckingSavings));
					fieldInput = printFieldPage(header, fields);
					amount = (Long)fieldInput.get(0);
					accountType = (Integer)fieldInput.get(1);
					Transaction tw = new Transaction(0, currentSession.getUserId(), new Date(System.currentTimeMillis()), 2, accountType, amount);
					if (accountType == 1 && currentChecking.getBalance() >= amount * -1) {
						currentChecking.setBalance(currentChecking.getBalance() + amount);
						checkingAccountDAO.updateCheckingAccount(currentChecking);
						transactionDAO.addTransaction(tw);
						printSuccess("Withdrawal successful.");
					} else if (accountType == 2 && currentSavings.getBalance() >= amount * -1) {
						currentSavings.setBalance(currentSavings.getBalance() + amount);
						savingsAccountDAO.updateSavingsAccount(currentSavings);
						transactionDAO.addTransaction(tw);
						printSuccess("Withdrawal successful.");
					} else {
						printError("Insufficient balance. Please try again!");
					}
					pauseEnter();
					break;
				case 3:
					header = "Funds Transfer";
					fields.clear();
					fields.put(new InputField("Amount", -3000000000l, 0), (ParseChecks::parseWithdrawal));
					fields.put(new InputField("Account Source (checking or savings)"), (ParseChecks::isCheckingSavings));
					fieldInput = printFieldPage(header, fields);
					amount = (Long)fieldInput.get(0);
					accountType = (Integer)fieldInput.get(1);
					Transaction tt = new Transaction(0, currentSession.getUserId(), new Date(System.currentTimeMillis()), 3, accountType, amount);
					if (accountType == 1 && currentChecking.getBalance() >= amount * -1) {
						currentChecking.setBalance(currentChecking.getBalance() + amount);
						currentSavings.setBalance(currentSavings.getBalance() - amount);
						checkingAccountDAO.updateCheckingAccount(currentChecking);
						transactionDAO.addTransaction(tt);
						printSuccess("Funds successfully transferred from checking to savings.");
					} else if (accountType == 2 && currentSavings.getBalance() >= amount * -1) {
						currentSavings.setBalance(currentSavings.getBalance() + amount);
						currentChecking.setBalance(currentChecking.getBalance() - amount);
						savingsAccountDAO.updateSavingsAccount(currentSavings);
						transactionDAO.addTransaction(tt);
						printSuccess("Funds successfully transferred from savings to checking.");
					} else {
						printError("Insufficient balance. Please try again!");
					}
					pauseEnter();
					break;
				case 4:
					header = "View 5 Recent Transactions";
					List<Transaction> transList = transactionDAO.getTransactionsByUserId(currentSession.getUserId());
					List<Transaction> lastFive = transList.subList(Math.max(transList.size() - 5, 0), transList.size());
					results.clear();
					for (Transaction transaction : lastFive) {
						results.add(transaction.toString());
					}
					printResultPage(header, results);
					pauseEnter();
					break;
				case 5:
					header = "Display Customer Info";
					results.clear();
					results.add("Customer ID: " + currentCustomer.getCustomerId());
					results.add("User ID: " + currentCustomer.getUserId());
					results.add("Name: " + currentCustomer.getName());
					results.add("Address: " + currentCustomer.getAddress());
					results.add("Number: " + currentCustomer.getNumber());
					results.add("");
					results.add("Checking balance: $" + currentChecking.getBalance());
					results.add("Savings balance: $" + currentSavings.getBalance());
					printResultPage(header, results);
					pauseEnter();
					break;
				case 6:
					currentSession = null;
					currentChecking = null;
					currentSavings = null;
					currentCustomer = null;
					break;
				default:
					break;
				}
			} else {
				header = "DOLLARSBANK Welcomes You!";
				menu.clear();
				menu.add("Create New Account");
				menu.add("Login");
				menu.add("Exit");
				int i = printMenuPage(header, menu);
				switch (i) {
				case 1:
					header = "Enter Details For New Account";
					fields.clear();
					fields.put(new InputField("Customer Name"), (str -> str));
					fields.put(new InputField("Customer Address"), (str -> str));
					fields.put(new InputField("Customer Contact Number"), (ParseChecks::isPhoneNumber));
					fields.put(new InputField("User Id"), (str -> str));
					fields.put(new InputField("Password (8 characters with lower, upper, & special)"), (ParseChecks::isStrongPassword));
					fields.put(new InputField("Initial Deposit Amount (Checking)", 0, 3000000000l), (Long::parseLong));
					fields.put(new InputField("Initial Deposit Amount (Savings)", 0, 3000000000l), (Long::parseLong));
					fieldInput = printFieldPage(header, fields);
					Account account = new Account((String)fieldInput.get(3), (String)fieldInput.get(4));
					Customer customer = new Customer(0, (String)fieldInput.get(3), (String)fieldInput.get(0), (String)fieldInput.get(1), (String)fieldInput.get(2));
					CheckingAccount ca = new CheckingAccount(0, (String)fieldInput.get(3), (Long)fieldInput.get(5));
					SavingsAccount sa = new SavingsAccount(0, (String)fieldInput.get(3), (Long)fieldInput.get(6));
					Transaction tc = new Transaction(0, (String)fieldInput.get(3), new Date(System.currentTimeMillis()), 1, 1, (Long)fieldInput.get(5));
					Transaction ts = new Transaction(0, (String)fieldInput.get(3), new Date(System.currentTimeMillis()), 1, 2, (Long)fieldInput.get(6));
					try {
						accountDAO.addAccount(account);
						customerDAO.addCustomer(customer);
						checkingAccountDAO.addCheckingAccount(ca);
						savingsAccountDAO.addSavingsAccount(sa);
						if (tc.getAmount() != 0) {
							transactionDAO.addTransaction(tc);
						}
						if (ts.getAmount() != 0) {
							transactionDAO.addTransaction(ts);
						}
						currentSession = account;
						printSuccess("Account created successfully.");
					} catch (UsernameUnavailableException e) {
						printError("This user ID is already taken. Please try again!");
					}
					pauseEnter();
					break;
				case 2:
					header = "Enter Login Details";
					fields.clear();
					fields.put(new InputField("User Id"), (str -> str));
					fields.put(new InputField("Password"), (str -> str));
					fieldInput = printFieldPage(header, fields);
					try {
						Account attempt = accountDAO.getAccountByUserId((String)fieldInput.get(0));
						if (attempt != null) {
							if (attempt.getPassword().equals((String)fieldInput.get(1))) {
								currentSession = attempt;
							} else {
								printError("Incorrect password. Please try again!");
							}
						}
					} catch (NotFoundException e) {
						printError("This user does not exist. Please try again!");
					}
					break;
				case 3:
					printSuccess("Thank you for choosing DOLLARSBANK! Goodbye!");
					sessionEnd = true;
					break;
				default:
					break;
				}
			}
		}
		
		
		/*********************************************
		 * END MAIN BODY
		 *********************************************/
		AnsiConsole.systemUninstall();
		sc.close();
	}
	
	private static void printHeader(String text) {
		StringBuffer result = new StringBuffer();
		StringBuffer horiz = new StringBuffer("+-");
		for(int i = 0; i < text.length(); i++) {
			horiz.append("-");
		}
		horiz.append("-+\n");
		String content = "| " + text + " |\n";
		result.append(horiz.toString() + content + horiz.toString());
		System.out.println(result);
	}
	
	private static void printSuccess(String text) {
		System.out.println(text);
	}
	
	private static void printError(String text) {
		System.out.println(text);
	}
	
	private static void pauseEnter(String text) {
		if (text != "") {
			System.out.println(text + " Press Enter to continue...");
		} else {
			System.out.println("Press Enter to continue...");
		}
		try {
			System.in.read();
		}
		catch(IOException e){}
	}
	
	private static void pauseEnter() {
		pauseEnter("");
	}
	
	private static <T> T presentField(String prompt, Function<String, T> function, long lowerBound, long upperBound) {
		System.out.println(prompt + " :");
		while(true) {
			try {
				T result = function.apply(sc.nextLine().trim());
				if (result instanceof Integer || result instanceof Long) {
					if (((Number) result).longValue() >= lowerBound && ((Number) result).longValue() <= upperBound) {
						return result;
					} else {
						printError("Input not in valid range. Please try again!");
					}
				} else {
					return result;
				}
			} catch (NumberFormatException e) {
				printError("Invalid input. Please try again!");
			}
		}
	}
	
	private static <T> T presentField(InputField field, Function<String, T> function) {
		return presentField(field.getFieldName(), function, field.getLowerBound(), field.getUpperBound());
	}
	
	private static int presentMenuPrompt(int fields) {
		StringBuffer promptText = new StringBuffer("Enter choice (");
		for (int i = 1; i <= fields; i++) {
			if (i < fields) {
				promptText.append(i + ",");
			} else {
				promptText.append(" or " + i + ")");
			}
		}
		return presentField(promptText.toString(), (Integer::parseInt), 1, fields);
	}
	
	private static int presentMenu(List<String> choices) {
		int i = 1;
		for (String string : choices) {
			System.out.print(i++);
			System.out.println(". " + string);
		}
		System.out.println();
		return presentMenuPrompt(choices.size());
	}
	
	private static int printMenuPage(String header, List<String> menu) {
		ansi().eraseScreen();
		printHeader(header);
		return presentMenu(menu);
	}
	
	private static List<Object> presentFields(Map<InputField, Function<String, Object>> fields) {
		ArrayList<Object> result = new ArrayList<Object>();
		for (Map.Entry<InputField, Function<String, Object>> entry: fields.entrySet()) {
			result.add(presentField(entry.getKey(), entry.getValue()));
		}
		return result;
	}
	
	private static List<Object> printFieldPage(String header, Map<InputField, Function<String, Object>> fields) {
		ansi().eraseScreen();
		printHeader(header);
		return presentFields(fields);
	}
	
	private static void presentResults(List<String> items) {
		for (String string: items) {
			System.out.println(string);
		}
	}
	
	private static void printResultPage(String header, List<String> items) {
		ansi().eraseScreen();
		printHeader(header);
		presentResults(items);
	}

}
