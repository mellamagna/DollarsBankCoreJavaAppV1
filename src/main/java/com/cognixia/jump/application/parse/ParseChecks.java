package com.cognixia.jump.application.parse;

public class ParseChecks {
	
	public static String isPhoneNumber(String text) throws NumberFormatException {
		if (text == null) {
			throw new NumberFormatException();
		}
		if (text.length() == 10) {
			try {
				long l = Long.parseLong(text);
				return text;
			} catch (NumberFormatException e) {
				throw new NumberFormatException();
			}
		}
		throw new NumberFormatException();
	}

}
