package io.gnt.bmt.commons.utils;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidatorUtils {
    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }
}
