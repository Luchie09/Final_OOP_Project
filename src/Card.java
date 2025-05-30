/* Card class represents a playing card with a value and type (suit) */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Card {

    private final String value;
    private final String type;

    // Constructor to create a card with a specific value and type
    public Card(String value, String type) {
        this.value = value;
        this.type = type;
    }

    // Getters for value and type
    @Override
    public String toString() {
        return value + "-" + type;
    }

    // Get the value of the card as a string
    public int getValue() {
        // Convert cards to their respective values
        switch (value) {
            case "A":
                return 11;
            case "K":
            case "Q":
            case "J":
                return 10;
            default:
                return Integer.parseInt(value);
        }
    }

    // Get the type of the card
    public boolean isAce() {
        return "A".equals(value);
    }

    // Get the file path of the card image
    public String getImagePath() {
        return "cards/" + toString() + ".png";
    }

    // Get the BufferedImage of the card
    public BufferedImage getImage() {
        // try and catch block to handle image loading
        try {
            File file = new File(getImagePath());// Create a File object with the image path
            // Check if the file exists
            if (!file.exists()) {
                System.err.println("Image not found: " + getImagePath());
                return null;
            }
            return ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Failed to load image for: " + getImagePath());
            e.printStackTrace();
            return null;
        }
    }
}
