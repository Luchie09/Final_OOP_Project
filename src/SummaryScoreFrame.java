/*Class dedicated for displaying the summary of the game scores */
import java.awt.*;
import javax.swing.*;

public class SummaryScoreFrame extends JFrame {

    // Custom cursor for the buttons
    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("pussy.png").getImage(), new Point(0, 0), "custom cursor");

    // Constructor to initialize the summary score frame
    public SummaryScoreFrame(float bank, int totalGames, int totalWins, int totalLosses, int totalTies) {
        setCursor(cursor);
        setTitle("Score Summary");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 30, 0));

        // Title section
        JLabel titleLabel = new JLabel("Score Summary", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        titleLabel.setForeground(new Color(235, 177, 17));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Center content panel with vertical BoxLayout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(0, 30, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 90, 10, 20));

        // Score labels
        centerPanel.add(createLabel("Money.............................. " + bank));
        centerPanel.add(createLabel("Wins................................ " + totalWins));
        centerPanel.add(createLabel("Losses............................. " + totalLosses));
        centerPanel.add(createLabel("Ties.................................. " + totalTies));
        centerPanel.add(createLabel("Total Games Played.......... " + totalGames));

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Buttons at the bottom
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(0, 30, 0));

        JButton newGButton = new JButton("New Game");
        newGButton.setFont(new Font("Poppins", Font.PLAIN, 16));
        newGButton.setBackground(new Color(200, 150, 10));
        newGButton.setForeground(Color.WHITE);
        newGButton.setFocusable(false);
        newGButton.addActionListener(e -> {
            dispose();
            new HomeFrame(); // Make sure HomeFrame class exists
        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Poppins", Font.PLAIN, 16));
        exitButton.setBackground(new Color(139, 0, 0));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusable(false);
        exitButton.addActionListener(e -> dispose());

        buttonPanel.add(newGButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    // Helper method to create consistently styled labels
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Poppins", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

}
