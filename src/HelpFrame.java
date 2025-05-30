/* HelpFrame class displays the rules of the game */
import java.awt.*;
import javax.swing.*;

public class HelpFrame extends JFrame {

    // Custom cursor for the buttons
    private Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("pussy.png").getImage(), new Point(0, 0), "custom cursor");

    public HelpFrame() {
        setCursor(cursor); // Set the cursor to hand when hovering over buttons
        setTitle("Rules of the Game");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Green background color
        Color greenBackground = new Color(10, 40, 10); // Brighter green

        // JTextArea with game rules
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setMargin(new Insets(20, 20, 20, 20));
        textArea.setFont(new Font("Poppins", Font.PLAIN, 17));
        textArea.setForeground(Color.WHITE);
        textArea.setBackground(greenBackground);
        textArea.setEditable(false);

        // Multiline text with game rules
        textArea.setText("""
            OBJECT OF THE GAME: 
            Players compete against the dealer, aiming for a hand that totals 21 or less, but higher than the dealer's hand.

            CARD VALUES/SCORING: 
            It is up to the player if an ace is worth 1 or 11. Face cards are 10, and any other card is its pip value (2-10).

            NATURALS: 
            If a player's first two cards are an ace and a "ten-card" (a picture card or 10), giving a count of 21 in two cards, 
            this is a natural or "blackjack." If a player has a natural and the dealer does not, the player will automatically 
            win that round; vice versa if the dealer has a natural while the player does not have naturals. If the dealer and 
            player both have naturals, the game is a tie. 

            THE PLAY:
            The player must decide whether to "stand" (not ask for another card) or "hit" (ask for another card in an attempt to 
            get closer to a count of 21, or even hit 21 exactly). Thus, a player may stand on the two cards originally dealt to 
            them, or they may ask the dealer for additional cards, one at a time, until deciding to stand on the total (if it is 
            21 or under), or goes "bust" (if it is over 21). In the latter case, the player loses and the dealer collects the bet 
            wagered.  

            THE DEALER'S PLAY: 
            When the dealer has served the player, the dealer's face-down card is turned up. If the total is 17 or more, it must 
            stand. If the total is 16 or under, they must take a card. The dealer must newG to take cards until the total is 
            17 or more, at which point the dealer must stand. If the dealer has an ace, and counting it as 11 would bring the 
            total to 17 or more (but not over 21), the dealer must count the ace as 11 and stand. The dealer's decisions, then, 
            are automatic on all plays, whereas the player always has the option of taking one or more cards.

            SIGNALING INTENTIONS:
            Press the "Hit" button to ask the dealer for another card to potentially improve your hand's total. Press the "Stand" 
            button if you want to stand with your current hand, not asking for any more cards.

            BASIC STRATEGY:
            Winning tactics in Blackjack require that the player play each hand in the optimum way, and such strategy always 
            takes into account what the dealer's upcard is. When the dealer's upcard is a good one, a 7, 8, 9, 10-card, or ace 
            for example, the player should not stop drawing until a total of 17 or more is reached. When the dealer's upcard is 
            a poor one, 4, 5, or 6, the player should stop drawing as soon as he gets a total of 12 or higher. The strategy 
            here is never to take a card if there is any chance of going bust. The desire with this poor holding is to let the 
            dealer hit and hopefully go over 21. Finally, when the dealer's up card is a fair one, 2 or 3, the player should 
            stop with a total of 13 or higher. 

            With a soft hand, the general strategy is to keep hitting until a total of at least 18 is reached. Thus, with an 
            ace and a six (7 or 17), the player would not stop at 17, but would hit.
        """);

        // Scroll pane with the same green background
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(greenBackground);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        // Main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(greenBackground);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Return Home Button
        JButton returnHomeButton = new JButton("x");
        returnHomeButton.setPreferredSize(new Dimension(40, 40));
        returnHomeButton.setFont(new Font("Poppins", Font.BOLD, 25));
        returnHomeButton.setBackground(new Color(3, 26, 2));
        returnHomeButton.setForeground(new Color(216, 159, 0));
        returnHomeButton.setBorder(null);
        returnHomeButton.setFocusable(false);

        // Add action listener to the return home button
        returnHomeButton.addActionListener(e -> {
            dispose();
            new HomeFrame();
        });

        // Title Label
        JLabel titleLabel = new JLabel("BLACK JACK RULES", JLabel.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 25));
        titleLabel.setForeground(new Color(216, 159, 0));

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(3, 26, 2));
        topPanel.setPreferredSize(new Dimension(1000, 60));
        topPanel.add(returnHomeButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Add to frame
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
