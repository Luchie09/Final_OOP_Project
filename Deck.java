/* Deck class represents a standard deck of playing cards */
import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards; // List to hold the cards in the deck

    // Constructor to initialize the deck
    public Deck() {
        buildDeck();
        shuffle();
    }

    // Initializes the card their values and types
    private void buildDeck() {
        cards = new ArrayList<>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        // Nested loop to create cards for each value and type
        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                cards.add(new Card(values[j], types[i]));
            }
        }
    }

    // Shuffles the deck using Collections.shuffle
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Resets the deck to a new shuffled state
    public Card draw() {
        return cards.remove(cards.size() - 1); // Draws the top card from the deck
    }

    // Returns the number of cards left in the deck
    public int size() {
        return cards.size(); // Returns the size of the deck
    }
}
