package com.brewster.poker.utility;

import com.brewster.poker.model.User;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class Utils {
     private final Random RANDOM = new SecureRandom();
     private final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234568790";


     public String generateUserId(int length){
          return generateRandomString(length);
     }
     public String generateRandomString(int length){
          StringBuilder returnValue = new StringBuilder(length);
          for (int i = 0; i< length; i++){
               returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
          }
          return returnValue.toString();
     }
     public boolean isEmailValid(User user){
          String email = user.getEmail();
          Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
          return matcher.find();
     }

     public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
             Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
}