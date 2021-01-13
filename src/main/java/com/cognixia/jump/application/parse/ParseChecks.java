package com.cognixia.jump.application.parse;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseChecks {
	
	//checks
	
	public static String isPhoneNumber(String text) throws NumberFormatException {
		if (text == null) {
			throw new NumberFormatException();
		}
		if (text.length() == 10) {
			try {
				@SuppressWarnings("unused")
				long l = Long.parseLong(text);
				return text;
			} catch (NumberFormatException e) {
				throw new NumberFormatException();
			}
		}
		throw new NumberFormatException();
	}
	
	public static String isStrongPassword(String text) throws NumberFormatException {
		if (text == null) {
			throw new NumberFormatException();
		}
		if (text.length() == 8) {
			if (match(".*[a-z].*", text) && match(".*[A-Z].*", text) && match(".*[!@#$%^&*()\\-+].*", text)) {
				return text;
			}
		}
		throw new NumberFormatException();
	}
	
	public static int isCheckingSavings(String text) {
		if (text == null) {
			throw new NumberFormatException();
		}
		String input = text.toLowerCase();
		if (input.equals("checking")) {
			return 1;
		} else if (input.equals("savings")) {
			return 2;
		}
		throw new NumberFormatException();
	}
	
	public static long parseWithdrawal(String text) {
		if (text == null) {
			throw new NumberFormatException();
		}
		long input = Long.parseLong(text) * -1;
		if (input < 0) {
			return input;
		}
		throw new NumberFormatException();
	}
	
	
	//utility methods

	static boolean match(String pattern, String text) {
		Pattern pat = Pattern.compile(pattern);
		Matcher mat = pat.matcher(text);
		if (mat.find()) {
			return true;
		} else {
			return false;
		}
	}

	
}
