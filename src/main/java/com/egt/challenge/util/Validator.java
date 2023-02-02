package com.egt.challenge.util;

import java.time.LocalDate;
import java.util.Objects;

public class Validator {
	
	public static boolean validateStringValue(String value) {
		if (Objects.isNull(value) || value.isEmpty() ||value.isBlank()) {
			return false;
		}
		return true;
	}
	
	public static boolean validateBirthdate(LocalDate value) {
		if (Objects.isNull(value)) {
			return false;
		}
		
		if(!value.isBefore(LocalDate.now())) {
			return false;
		}
		
		return true;
	}

}
