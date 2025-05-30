/* The main frame, which the black jack game is played on */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GameFrame extends BaseGameFrame {

    // Instance class variables for the game
    private Deck deck;
    private Hand dealerHand;
    private Hand playerHand;

    // Hidden card for the dealer
    private Card hiddenCard;

    // Buffered images for the game background and card back
    private BufferedImage gImage;
    private BufferedImage backCardImage;

    // Hand behavior for the dealer
    private HandBehavior dealerBehavior = new DealerBehavior();

    // UI components for the game
    private JPanel dealerHandValue = new JPanel();
    private JPanel dealerNamePanel = new JPanel();
    private JPanel dealerInfoPanel = new JPanel();

    private JPanel playerInfoPanel = new JPanel();
    private JPanel playerHandValue = new JPanel();
    private JPanel playerNamePanel = new JPanel();
    private JPanel rightInfoPanel = new JPanel(new BorderLayout());

    private JPanel moneyPanel = new JPanel();
    private JPanel leftInfoPanel = new JPanel(new BorderLayout());

    private JPanel buttonPanel = new JPanel();
    private JPanel optionPanel = new JPanel();

    private JLabel dealerNameLabel = new JLabel("Dealer", JLabel.LEFT);
    private JLabel playerNameLabel = new JLabel("Player", JLabel.LEFT);
    private JLabel playerBankLabel = new JLabel("Bank: 500", JLabel.CENTER);
    private JLabel playerBetLabel = new JLabel("Bet: 0", JLabel.CENTER);

    private JButton backButton = new JButton();
    private JButton resetButton = new JButton();
    private JButton hitButton = new JButton();
    private JButton standButton = new JButton();

    // Dimensions for the card images and spacing
    private int cardWidth = 145;
    private int cardHeight = 189;
    private int cardSpacing = 10;

    // Variables to keep track of game statistics
    private int totalGames = 0;
    private int totalWins = 0;
    private int totalLosses = 0;
    private int totalTies = 0;

    private float bank = 500;
    private float bet = 0;

    private boolean revealDealerTotal = false;

    private String gameResultMessage = null;
    private String playerName;

    // Custom cursor for the buttons
    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("pussy.png").getImage(), new Point(0, 0), "custom cursor");

    // The main game panel where cards and background are drawn
    private JPanel gPanel = new JPanel(new BorderLayout()) {
        // Override paintComponent to draw the background image and cards
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Draw the background image
            drawCardImage(g, gImage, 0, 0, getWidth(), getHeight(), this);

            // Draw dealer's cards
            ArrayList<Card> dealerCards = dealerHand.getCards();
            for (int i = 0; i < dealerCards.size(); i++) {
                BufferedImage cardImage;
                // Show back of the first dealer card if not revealing dealer's total
                if (i == 0 && !revealDealerTotal) {
                    cardImage = backCardImage;
                } else {
                    // Load the actual card image
                    cardImage = loadCardImage(dealerCards.get(i).getImagePath());
                }

                // Draw the card image
                if (cardImage != null) {
                    int x = 40 + (cardWidth + cardSpacing) * i;
                    drawCardImage(g, cardImage, x, 80, cardWidth, cardHeight, this);
                }
            }

            // Same logic in drawing player's cards
            ArrayList<Card> playerCards = playerHand.getCards();
            for (int i = 0; i < playerCards.size(); i++) {
                BufferedImage cardImage;
                // Load the actual card image
                cardImage = loadCardImage(playerCards.get(i).getImagePath());

                // Draw the card image
                if (cardImage != null) {
                    int x = 40 + (cardWidth + cardSpacing) * i;
                    drawCardImage(g, cardImage, x, 330, cardWidth, cardHeight, this);
                }
            }

        }
    };

    // Constructor for the GameFrame class
    public GameFrame() {
        super("Blackjack Game");

        setCursor(cursor); // Set the custom cursor for the buttons

        // Loop to get a valid player name 
        while (true) {
            playerName = JOptionPane.showInputDialog(null, "Enter your name:", "Player Name", JOptionPane.PLAIN_MESSAGE);
            playerName = playerName.trim();
            if (!playerName.isEmpty()) {
                break;
            }
            JOptionPane.showMessageDialog(null, "Please enter a name.", "Error Message", JOptionPane.ERROR_MESSAGE);
        }
        playerNameLabel.setText(playerName);

        // Call the method to start the game
        startGame();

        gImage = loadCardImage("bgGPanel.jpg"); // Load the background image
        backCardImage = loadCardImage("cards/BACK.png"); // Load the back card image

        setContentPane(gPanel); // Canvas for UI components

        // Set up the right info panel
        rightInfoPanel.setPreferredSize(new Dimension(250, 700));
        rightInfoPanel.setOpaque(false);

        // Dealer's hand value panel
        dealerInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 170)); // Align the dealer info to the left
        dealerInfoPanel.setOpaque(false);

        dealerHandValue.setPreferredSize(new Dimension(60, 50));
        dealerHandValue.setBackground(new Color(0, 30, 0));
        dealerHandValue.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        dealerNamePanel.setPreferredSize(new Dimension(150, 50));
        dealerNamePanel.setOpaque(false);
        dealerNameLabel.setForeground(Color.WHITE);
        dealerNameLabel.setFont(new Font("Poppins", Font.BOLD, 25));
        dealerNamePanel.setLayout(new BorderLayout());
        dealerNamePanel.add(dealerNameLabel, BorderLayout.CENTER);

        // Add dealer name and value panels to the dealer info panel
        dealerInfoPanel.add(dealerHandValue);
        dealerInfoPanel.add(dealerNamePanel);

        // Player's hand value panel
        playerInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 170));
        playerInfoPanel.setOpaque(false);

        playerHandValue.setPreferredSize(new Dimension(60, 50));
        playerHandValue.setBackground(new Color(0, 30, 0));
        playerHandValue.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));

        playerNamePanel.setPreferredSize(new Dimension(150, 50));
        playerNamePanel.setOpaque(false);
        playerNameLabel.setForeground(Color.WHITE);
        playerNameLabel.setFont(new Font("Poppins", Font.BOLD, 25));
        playerNamePanel.setLayout(new BorderLayout());
        playerNamePanel.add(playerNameLabel, BorderLayout.CENTER);

        // Add player name and value panels to the player info panel
        playerInfoPanel.add(playerHandValue);
        playerInfoPanel.add(playerNamePanel);

        // Add dealer and player info panels to the right info panel
        rightInfoPanel.add(dealerInfoPanel, BorderLayout.NORTH);
        rightInfoPanel.add(playerInfoPanel, BorderLayout.SOUTH);
        gPanel.add(rightInfoPanel, BorderLayout.EAST);

        // Set up the left info panel
        leftInfoPanel.setPreferredSize(new Dimension(200, 700)); // Reduce width to avoid overlap
        leftInfoPanel.setLayout(new BorderLayout());
        leftInfoPanel.setOpaque(false);

        // Set up the bank label
        playerBankLabel.setHorizontalAlignment(JLabel.LEFT);
        playerBankLabel.setForeground(Color.WHITE);
        playerBankLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        playerBankLabel.setForeground(new Color(235, 177, 17));

        // Set up the bet label
        playerBetLabel.setHorizontalAlignment(JLabel.LEFT);
        playerBetLabel.setForeground(Color.WHITE);
        playerBetLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        playerBetLabel.setForeground(new Color(235, 177, 17));

        // Set up the money panel
        moneyPanel.setLayout(new GridLayout(2, 1, 10, 0)); // Align labels vertically
        moneyPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 0, 0)); // Padding left/top
        moneyPanel.add(playerBankLabel);
        moneyPanel.add(playerBetLabel);
        moneyPanel.setOpaque(false);

        // Set up the back button
        backButton.setPreferredSize(new Dimension(60, 40));
        backButton.setBackground(new Color(0, 30, 0));
        backButton.setForeground(new Color(235, 177, 17));
        backButton.setFont(new Font("Dialog", Font.BOLD, 25));
        backButton.setText("x");
        backButton.setFocusable(false);

        // Add action listener to perform action when clicked
        backButton.addActionListener(e -> {
            dispose();
            new HomeFrame();
        });

        // Set up the reset button
        resetButton.setPreferredSize(new Dimension(60, 40));
        resetButton.setBackground(new Color(0, 30, 0));
        resetButton.setForeground(new Color(235, 177, 17));
        resetButton.setFont(new Font("Dialog", Font.BOLD, 25));
        resetButton.setText("\u21BB"); // Unicode for refresh icon
        resetButton.setFocusable(false);

        // Add action listener to perform action when clicked
        resetButton.addActionListener(e -> {
            restartGame();
        });

        // Set up the option panel
        optionPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));
        optionPanel.setOpaque(false);

        // add the buttons to the option panel
        optionPanel.add(backButton);
        optionPanel.add(resetButton);

        // Add the option panel and money panel to the left info panel
        leftInfoPanel.add(moneyPanel, BorderLayout.SOUTH);
        leftInfoPanel.add(optionPanel, BorderLayout.NORTH);
        gPanel.add(leftInfoPanel, BorderLayout.WEST);

        // Method to setup the hit and stand buttons
        setupButton(hitButton, "HIT");
        setupButton(standButton, "STAND");

        standButton.setPreferredSize(new Dimension(120, 40));

        // Set up the button panel
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20)); // Align buttons to the center

        // Add the buttons to the button panel
        buttonPanel.add(hitButton);
        buttonPanel.add(standButton);
        gPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Hit button action listener
        hitButton.addActionListener(e -> {
            playerHand.addCard(deck.draw()); // Add a card to the player's hand
            updatePlayerHandValue(); // Update the player's hand value
            gPanel.repaint(); // Repaint the panel to show the new card

            // Check if the player has busted
            if (playerHand.isBust()) {
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                revealDealerTotal = true;
                gameResultMessage = playerName + " Busts! Dealer Wins.";
                totalLosses++;
                totalGames++;
                updateDealerHandValue();
                gPanel.repaint();
                askReplay();
            }
        });

        // stand button action listener
        standButton.addActionListener(e -> {
            hitButton.setEnabled(false);
            standButton.setEnabled(false);

            // Loop to add cards to the dealer's hand until the total is 17 or more
            while (dealerBehavior.shouldHit(dealerHand)) {
                dealerHand.addCard(deck.draw());
            }

            revealDealerTotal = true; // Reveal the dealer's total
            updateDealerHandValue();  // Update the dealer's hand value
            gPanel.repaint(); // Repaint the panel to show the dealer's hand

            // Get the total values of dealer and player hands
            int dealerTotal = dealerHand.getTotal();
            int playerTotal = playerHand.getTotal();

            // Check for game results
            if (playerHand.isBust()) {
                gameResultMessage = playerName + " Busts! Dealer Wins.";
                totalLosses++;
                totalGames++;
            } else if (dealerHand.isBust()) {
                gameResultMessage = "Dealer Busts! " + playerName + " Wins.";
                totalWins++;
                totalGames++;
                bank += bet * 2;
                updateMoneyStatus();
            } else if (dealerTotal > playerTotal) {
                gameResultMessage = "Dealer Wins.";
                totalLosses++;
                totalGames++;
            } else if (dealerTotal < playerTotal) {
                gameResultMessage = playerName + " Wins.";
                totalWins++;
                totalGames++;
                bank += bet * 2;
                updateMoneyStatus();
            } else {
                gameResultMessage = "It's a Tie!";
                totalTies++;
                totalGames++;
                bank += bet;
                updateMoneyStatus();
            }

            gPanel.repaint(); // Repaint the panel to show the final results
            askReplay(); // Call the method to ask for replay
        });

        updateDealerHandValue(); // Update the dealer's hand value
        updatePlayerHandValue(); // Update the player's hand value
        updatePlayerBank(); // Update the player's bank
        updatePlayerBet(); // Update the player's bet
        gPanel.repaint(); // Repaint the panel to show the initial state
        setVisible(true);

        promptForBet(); // Call the method to prompt for bet amount
        checkForNaturals(); // Check for natural blackjack
    }

    // Method to start a new game
    private void startGame() {
        // Initialize deck, dealer's hand, and player's hand
        deck = new Deck();
        dealerHand = new Hand();
        playerHand = new Hand();

        // Add two cards to dealer's hand, the other is hidden
        hiddenCard = deck.draw();
        dealerHand.addCard(hiddenCard);
        dealerHand.addCard(deck.draw());

        // Add two cards to player's hand
        playerHand.addCard(deck.draw());
        playerHand.addCard(deck.draw());
    }

    // Method to prompt the user for a bet amount
    private void promptForBet() {
        boolean valid = false;

        // Loop until a valid bet is entered
        while (!valid) {
            String betInput = JOptionPane.showInputDialog(null, "Enter your bet (Bank: ₱ " + String.format("%.2f", bank) + "):", "Place Bet", JOptionPane.PLAIN_MESSAGE);

            if (betInput == null) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    dispose(); // Close the game frame
                    new SummaryScoreFrame(bank, totalGames, totalWins, totalLosses, totalTies); // Show summary score
                    return; // Exit the method
                } else {
                    continue; // If the user chooses not to exit, continue the loop
                }
            }

            // try-catch block to check if the input is a valid number
            try {
                float enteredBet = Float.parseFloat(betInput);
                // Check if the entered bet is valid
                if (enteredBet > 0 && enteredBet <= bank) {
                    bet = enteredBet; // Set the bet
                    bank -= bet; // Deduct the bet from the bank
                    valid = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid bet. Please enter a value between 1 and " + String.format("%.2f", bank) + ".", "Invalid Bet", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                //Error message if the input is not a valid number
                JOptionPane.showMessageDialog(null, "Enter a number you dumbass!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        // Update the money status display
        updateMoneyStatus();
    }

    // Method to check for natural blackjacks
    private void checkForNaturals() {
        // Check for natural blackjacks
        boolean playerBlackjack = playerHand.isBlackjack();
        boolean dealerBlackjack = dealerHand.isBlackjack();

        // If either player or dealer has a natural blackjack, handle the outcome
        if (playerBlackjack || dealerBlackjack) {
            revealDealerTotal = true;

            hitButton.setEnabled(false);
            standButton.setEnabled(false);

            //  if both player and dealer have blackjack
            if (playerBlackjack && dealerBlackjack) {
                gameResultMessage = "Both have Blackjack! It's a Tie!";
                totalTies++;
                totalGames++;
                bank += bet;
                // if only player has blackjack
            } else if (playerBlackjack) {
                gameResultMessage = playerName + " has Blackjack! Player Wins!";
                totalWins++;
                totalGames++;
                bank += bet * 2; // Player wins, double the bet is added
                updateMoneyStatus();
                // if only dealer has blackjack
            } else {
                gameResultMessage = "Dealer has Blackjack! Dealer Wins.";
                totalLosses++;
                totalGames++;
            }
            updateDealerHandValue();
            updatePlayerHandValue();
            gPanel.repaint();
            askReplay();
        }
    }

    // Method to update the money status display
    private void updateMoneyStatus() {
        playerBankLabel.setText("Bank: ₱ " + String.format("%.2f", bank));
        playerBetLabel.setText("Bet: ₱ " + String.format("%.2f", bet));
    }

    // Method to ask the player if they want to play again
    private void askReplay() {
        // Check for out of money BEFORE asking to play again
        if (bank <= 0) {
            JOptionPane.showMessageDialog(this, "You have no more money left! Game over.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            new Shrek(bank, totalGames, totalWins, totalLosses, totalTies);
            return;
        }

        // Show the game result message and ask if the player wants to play again
        int result = JOptionPane.showConfirmDialog(this, gameResultMessage + " Do you want to play again?", "New Round", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            promptForBet(); // Prompt for a new bet
            restartGame(); // Restart for a new round
        } else {
            // If the player chooses not to play again, show summary score
            dispose();
            new SummaryScoreFrame(bank, totalGames, totalWins, totalLosses, totalTies);
        }
    }

    // Method to restart the game
    private void restartGame() {
        gameResultMessage = null;
        startGame();
        revealDealerTotal = false;
        hitButton.setEnabled(true);
        standButton.setEnabled(true);
        updateDealerHandValue();
        updatePlayerHandValue();
        updateMoneyStatus();
        gPanel.repaint();
        checkForNaturals();
    }

    // Method to customize button styles
    private void setupButton(JButton button, String text) {
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(new Color(0, 30, 0));
        button.setForeground(new Color(235, 177, 17));
        button.setFont(new Font("Poppins", Font.BOLD, 20));
        button.setText(text);
    }

    // Loads the card image from the specified relative path
    private BufferedImage loadCardImage(String relativePath) {
        // Check if the image is already loaded
        try {
            File file = new File(relativePath);
            if (file.exists()) {
                return ImageIO.read(file);
            }

            URL resource = getClass().getClassLoader().getResource(relativePath);
            if (resource != null) {
                return ImageIO.read(resource);
            }

            throw new IOException("Image not found: " + relativePath);
        } catch (IOException e) {
            System.err.println("Error loading image: " + relativePath);
            e.printStackTrace();
            return null;
        }
    }

    // Draws the card image at the specified location
    private void drawCardImage(Graphics g, BufferedImage img, int x, int y, int width, int height, Component observer) {
        // Check if the image is not null before drawing
        if (img != null) {
            g.drawImage(img, x, y, width, height, observer);
        }
    }

    // Method to update the dealer's hand value display
    private void updateDealerHandValue() {
        dealerHandValue.removeAll(); // Remove the old label
        String labelText = "";

        // Check if the dealer's total should be revealed
        if (revealDealerTotal) {
            // Show the total value of the dealer's hand
            labelText = String.valueOf(dealerHand.getTotal());
        } else if (dealerHand.getCards().size() > 1) {
            // Show the value of the second card if the first is hidden
            labelText = String.valueOf(dealerHand.getCards().get(1).getValue());
        }

        // Create a new label with the updated text
        JLabel valueLabel = new JLabel(labelText, JLabel.CENTER);
        valueLabel.setForeground(new Color(235, 177, 17));
        valueLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        valueLabel.setOpaque(false);
        dealerHandValue.setLayout(new BorderLayout());
        dealerHandValue.add(valueLabel, BorderLayout.CENTER);
        dealerHandValue.revalidate(); // Revalidate the panel to update the layout
        dealerHandValue.repaint(); // Repaint the panel to show the new label
    }

    // Method to update the player's hand value display
    private void updatePlayerHandValue() {
        playerHandValue.removeAll(); // Remove the old label
        String labelText = String.valueOf(playerHand.getTotal()); // Get the total value of the player's hand

        // Create a new label with the updated text
        JLabel valueLabel = new JLabel(labelText, JLabel.CENTER);
        valueLabel.setForeground(new Color(235, 177, 17));
        valueLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        valueLabel.setOpaque(false);
        playerHandValue.setLayout(new BorderLayout());
        playerHandValue.add(valueLabel, BorderLayout.CENTER);
        playerHandValue.revalidate(); // Revalidate the panel to update the layout
        playerHandValue.repaint(); // Repaint the panel to show the new label
    }

    private void updatePlayerBank() {
        playerBankLabel.setText("Bank: ₱ " + String.format("%.2f", bank));
    }

    private void updatePlayerBet() {
        playerBetLabel.setText("Bet: ₱ " + String.format("%.2f", bet));
    }

}
