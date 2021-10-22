package com.brewster.poker.utility;

import com.brewster.poker.card.Card;
import com.brewster.poker.dto.UserDto;
import com.brewster.poker.model.User;
import com.brewster.poker.player.ComputerPlayer;
import com.brewster.poker.player.Player;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Utils {
     private static final Random RANDOM = new SecureRandom();
     private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234568790";
     public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
             Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


     public static String generateUserId(int length){
          StringBuilder returnValue = new StringBuilder(length);
          for (int i = 0; i< length; i++){
               returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
          }
          return returnValue.toString();
     }

     public static boolean isEmailValid(User user){
          String email = user.getEmail();
          Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
          return matcher.find();
     }

     public static List<Player> generateNComputerPlayers(int n, UserDto computer){
          List<Player> players = new ArrayList<>();
          for (int i = 0; i < n; i++) {
               String displayName = "HAL" + RANDOM.nextInt(500);
               Player player = new ComputerPlayer(displayName, computer);
               players.add(player);
          }
          return players;
     }

     public static List<Card> shuffleCards(List<Card> cards){
          for (int count = cards.size() - 1; count > 0; count--){
               int otherCard = RANDOM.nextInt(count);
               Card card = cards.get(otherCard);
               cards.set(otherCard, cards.get(count));
               cards.set(count, card);
          }
          return cards;
     }
}