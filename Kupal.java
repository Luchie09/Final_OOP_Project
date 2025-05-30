/* To those player who fallen to the trap. */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Kupal extends JFrame {

    // Custom cursor for the buttons
    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("pussy.png").getImage(), new Point(0, 0), "custom cursor");

    public Kupal(float bank, int totalGames, int totalWins, int totalLosses, int totalTies) {
        setCursor(cursor); // Set the cursor to hand when hovering over buttons
        System.out.println("Kupal window constructor called!");
        setTitle("Bossing!");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Load background image into a final variable
        final BufferedImage kImage;
        {
            BufferedImage tempImage = null;
            try {
                tempImage = ImageIO.read(new File("kupal.jpg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            kImage = tempImage;
        }

        // Background panel with image
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (kImage != null) {
                    g.drawImage(kImage, 0, 0, getWidth(), getHeight(), this);
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
        JLabel messageLabel = new JLabel("KUPAL KA BA BOSS!", JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        titlePanel.add(messageLabel);

        // Button panel (transparent)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Poppins", Font.PLAIN, 25));
        exitButton.setBackground(new Color(139, 0, 0)); // Dark red
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusable(false);
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.addActionListener(e -> {
            dispose();
            new SummaryScoreFrame(bank, totalGames, totalWins, totalLosses, totalTies);
        });

        buttonPanel.add(exitButton);

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add mainPanel to backgroundPanel
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

}
