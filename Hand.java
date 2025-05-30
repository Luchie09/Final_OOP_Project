/* Hand class represents a player's or dealer's hand in the game */
import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cards;
    private int sum;
    private int aceCount;

    // Constructor initializes the hand and resets it
    public Hand() {
        cards = new ArrayList<>();
        reset();
    }

    // Adds a card to the hand and updates the sum and ace count
    public void addCard(Card card) {
        cards.add(card);
        sum += card.getValue();
        // Increment ace count if the card is an Ace
        if (card.isAce()) {
            aceCount++;
        }
    }

    // Returns the total value of the hand, adjusting for Aces if necessary
    public int getTotal() {
        int tempSum = sum;
        int tempAceCount = aceCount;
        // Adjust the sum for Aces if the total exceeds 21
        while (tempSum > 21 && tempAceCount > 0) {
            tempSum -= 10;
            tempAceCount--;
        }
        return tempSum;
    }

    // Returns the total value of the hand without adjusting for Aces
    public boolean isBust() {
        return getTotal() > 21;
    }

    // Returns the list of cards in the hand
    public ArrayList<Card> getCards() {
        return cards;
    }

    // Returns the number of cards in the hand
    public void reset() {
        cards.clear();
        sum = 0;
        aceCount = 0;
    }

    // Returns the raw sum of the hand without adjusting for Aces
    public int getRawSum() {
        return sum;
    }

    // Returns the number of Aces in the hand
    public int getAceCount() {
        return aceCount;
    }

    // Checks if the hand is a blackjack
    public boolean isBlackjack() {
        // Blackjack: exactly 2 cards, one is Ace, one is 10-value (10, J, Q, K)
        if (cards.size() == 2) {
            boolean hasAce = false;
            boolean hasTenValue = false;
            // Check for Ace and 10-value cards
            for (int i = 0; i < cards.size(); i++) {
                Card card = cards.get(i);
                if (card.isAce()) {
                    hasAce = true;
                } else if (card.getValue() == 10) {
                    hasTenValue = true;
                }
            }
            return hasAce && hasTenValue;
        }
        return false;
    }
}
