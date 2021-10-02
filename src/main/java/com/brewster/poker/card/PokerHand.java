package com.brewster.poker.card;

public class PokerHand {
     private PokerHandEnum pokerHandEnum;
     private Card primaryHighCard;
     private Card secondaryHighCard;

//     public int tempValue;
//     public int highPairCard;
//     public int highOtherCard;

     public PokerHand(PokerHandEnum pokerHandEnum, Card primaryHighCard, Card secondaryHighCard) {
          this.pokerHandEnum = pokerHandEnum;
          this.primaryHighCard = primaryHighCard;
          this.secondaryHighCard = secondaryHighCard;
     }
     public PokerHand(){}

     public PokerHandEnum getPokerHandEnum() {
          return pokerHandEnum;
     }

     public void setPokerHandEnum(PokerHandEnum pokerHandEnum) {
          this.pokerHandEnum = pokerHandEnum;
     }

     public Card getPrimaryHighCard() {
          return primaryHighCard;
     }

     public void setPrimaryHighCard(Card primaryHighCard) {
          this.primaryHighCard = primaryHighCard;
     }

     public Card getSecondaryHighCard() {
          return secondaryHighCard;
     }

     public void setSecondaryHighCard(Card secondaryHighCard) {
          this.secondaryHighCard = secondaryHighCard;
     }
}
