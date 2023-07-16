package br.ifnmg.edu.partyrent.shared.utils;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Password {
    public static ArrayList<String> isValid(String password) {
        ArrayList<String> errorList = new ArrayList<String>();

        Pattern specialCharacters = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Pattern UpperCasePatten = Pattern.compile("[A-Z ]");
        Pattern lowerCasePatten = Pattern.compile("[a-z ]");
        Pattern digitCasePatten = Pattern.compile("[0-9 ]");

        if (password.length() < 8) {
            errorList.add("Password length must have at least 8 character.");
        }
        if (!specialCharacters.matcher(password).find()) {
            errorList.add("Password must have at least one special character.");
        }
        if (!UpperCasePatten.matcher(password).find()) {
            errorList.add("Password must have at least one uppercase character.");
        }
        if (!lowerCasePatten.matcher(password).find()) {
            errorList.add("Password must have at least one lowercase character.");
        }
        if (!digitCasePatten.matcher(password).find()) {
            errorList.add("Password must have at least one digit character.");
        }

        return errorList;
    }
}
