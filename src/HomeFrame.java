/*Home Page of the game, where handles user interactions to play or get help*/
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class HomeFrame extends JFrame {

    private BufferedImage bgImage;
    private JLabel title = new JLabel("BLACK JACK", JLabel.CENTER);
    private JPanel titlePanel = new JPanel(new BorderLayout());
    private JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 100));
    private JButton playBtn = createButton("PLAY");
    private JButton helpBtn = createButton("ⓘ");

    // Custom cursor for the buttons
    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
            new ImageIcon("pussy.png").getImage(), new Point(0, 0), "custom cursor");

    public HomeFrame() {
        setTitle("Home Page");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load background image
        try {
            bgImage = ImageIO.read(new File("Brg.jpg"));
        } catch (IOException e) {
            // Print the stack trace if the image fails to load
            e.printStackTrace();
        }

        // Set background panel
        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            // Override paintComponent to draw the background image
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    // Draw the background image if the file exists and scale it to fit the panel
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        setContentPane(backgroundPanel);

        setCursor(cursor);

        // Title
        title.setFont(new Font("Georgia", Font.BOLD, 50));
        title.setForeground(new Color(235, 177, 17));

        titlePanel.setOpaque(false);
        titlePanel.add(title, BorderLayout.CENTER);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        backgroundPanel.add(titlePanel, BorderLayout.NORTH);

        // Buttons panel to store play and help buttons
        buttonPanel.setOpaque(false);
        playBtn.setPreferredSize(new Dimension(180, 55));

        // Add action listener to the play button to start the game
        playBtn.addActionListener(e -> {
            new GameFrame();
            dispose();
        });

        helpBtn.setPreferredSize(new Dimension(70, 55));

        // Add action listener to the help button to show help information
        helpBtn.addActionListener(e -> {
            new HelpFrame();
            dispose();
        });

        buttonPanel.add(playBtn);
        buttonPanel.add(helpBtn);
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Create a button with custom styles
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(0, 30, 0)); // Darker green background
        btn.setForeground(new Color(235, 177, 17));
        btn.setFont(new Font("Poppins", Font.BOLD, 25));
        btn.setFocusable(false);
        return btn;
    }

    public static void main(String[] args) {
        new HomeFrame();
    }

}
