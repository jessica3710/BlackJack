/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jessica Zheng
 */
public class Hand {
    //Arraylist of cards
    List<Card> cards = null;

    public Hand() {
        cards = new ArrayList<>();
    }

    //getters and setters for cards and most recent card
    public List<Card> getCards() {
        return cards;
    }

    public Card getMostRecentCard() {
        if (cards == null || cards.isEmpty()) {
            return null;
        }

        return cards.get(cards.size() - 1);
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    
    //adds a card to the list
    public void addCard(Card c) {
        cards.add(c);
    }
    
    //calculates the sum of the cards
    public int getSum() {
        int sum = 0;
        int secondSum = 0;
        if (cards != null) {
            for (Card card : cards) {
                //gets card value and adds the appropriate value to the sum
                switch (card.getValueAsString()) {
                    //hidden card in the beginning has no sum
                    case "Hidden Card":
                        sum += 0;
                        break;
                    //ace is added as 11
                    case "Ace":
                        sum += 11;
                        break;
                    //face cards are 10
                    case "Jack":
                        sum += 10;
                        break;
                    case "Queen":
                        sum += 10;
                        break;
                    case "King":
                        sum += 10;
                        break;
                    //rest of the cards are the values of their own card
                    default:
                        sum += card.getValue();
                }

            }

        }
        //same function as the one above, but adds aces as 1 instead of 11
        if (cards != null) {
            for (Card card : cards) {
                switch (card.getValueAsString()) {
                    case "Hidden Card":
                        secondSum += 0;
                        break;
                    case "Ace":
                        secondSum += 1;

                        break;
                    case "Jack":
                        secondSum += 10;
                        break;
                    case "Queen":
                        secondSum += 10;
                        break;
                    case "King":
                        secondSum += 10;
                        break;
                    default:
                        secondSum += card.getValue();
                }

                
            }

        }
        //returns the value that is better for the player or dealer in the game
        if(sum > secondSum && sum <= 21){
            return sum;
        }else{
            return secondSum;
        }
    }

}
