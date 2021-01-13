package com.cognixia.jump.application;

import static org.fusesource.jansi.Ansi.*;
import static org.fusesource.jansi.Ansi.Color.*;

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
import com.cognixia.jump.dao.impl.AccountDAOImpl;
import com.cognixia.jump.dao.impl.CheckingAccountDAOImpl;
import com.cognixia.jump.dao.impl.CustomerDAOImpl;
import com.cognixia.jump.dao.impl.SavingsAccountDAOImpl;
import com.cognixia.jump.dao.impl.TransactionDAOImpl;

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
		/*********************************************
		 * BEGIN MAIN BODY
		 *********************************************/
		
		
		
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
			fields.put(new InputField("Password"), (str -> str));
			fields.put(new InputField("Initial Deposit Amount", 0, 3000000000l), (Long::parseLong));
			fieldInput = printFieldPage(header, fields);
			break;
		case 2:
			header = "Enter Login Details";
			fields.clear();
			fields.put(new InputField("User Id"), (str -> str));
			fields.put(new InputField("Password"), (str -> str));
			fieldInput = printFieldPage(header, fields);
			break;
		case 3:
			return;
		default:
			break;
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
	
	private static void printError(String text) {
		System.out.println(text);
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
	
	private static <T> T presentField(String prompt, Function<String, T> function) {
		return presentField(prompt, function, Long.MIN_VALUE, Long.MAX_VALUE);
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

}
