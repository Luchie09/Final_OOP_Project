/*A frame that prompts the user to bark for money */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Shrek extends JFrame {

    // Custom cursor for the buttons
    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("pussy.png").getImage(), new Point(0, 0), "custom cursor");

    public Shrek(float bank, int totalGames, int totalWins, int totalLosses, int totalTies) {
        setCursor(cursor); // Set the cursor to hand when hovering over buttons
        setTitle("Money Borrowing");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load background image into a final variable
        final BufferedImage sImage;
        {
            BufferedImage tempImage = null;
            try {
                tempImage = ImageIO.read(new File("Shrek.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            sImage = tempImage;
        }

        // Background panel with image
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (sImage != null) {
                    g.drawImage(sImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        setContentPane(backgroundPanel);

        // Main panel (transparent)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);

        // Title panel (transparent)
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel messageLabel = new JLabel("Bark! if you want another 100 pesos. ", JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        titlePanel.add(messageLabel);

        // Button panel (transparent)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton barkButton = new JButton("Bark");
        barkButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        barkButton.setBackground(new Color(200, 150, 10));
        barkButton.setForeground(Color.WHITE);
        barkButton.setFocusable(false);
        barkButton.setPreferredSize(new Dimension(100, 50));
        barkButton.addActionListener(e -> {
            System.out.println("Opening Kupal window...");
            dispose();
            SwingUtilities.invokeLater(() -> new Kupal(bank, totalGames, totalWins, totalLosses, totalTies));

        });

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Poppins", Font.PLAIN, 15));
        exitButton.setBackground(new Color(139, 0, 0));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusable(false);
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.addActionListener(e -> {
            dispose();
            new SummaryScoreFrame(bank, totalGames, totalWins, totalLosses, totalTies);
        });

        buttonPanel.add(barkButton);
        buttonPanel.add(exitButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add mainPanel to backgroundPanel
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Shrek(0, 0, 0, 0, 0);
    }
}
