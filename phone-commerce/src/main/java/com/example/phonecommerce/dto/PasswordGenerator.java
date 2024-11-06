package com.example.phonecommerce.dto;

import java.util.Random;

public class PasswordGenerator {


    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "@";


    public static String generate(int number) {

        Random random = new Random();

        String characterSet = LOWERCASE;
        characterSet += UPPERCASE;
        characterSet += DIGITS;
        characterSet += SPECIAL_CHARACTERS;


        StringBuilder newPassword = new StringBuilder();


        int length = characterSet.length();


        for (int i = 0; i < number; i++) {
            newPassword.append(String.valueOf(characterSet.charAt(random.nextInt(length))));
        }

        return newPassword.toString();


    }
}
