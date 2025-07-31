import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;

public class WordScrambleGameGUI extends JFrame {

    private static final String[] WORD_LIST = {
        "python", "developer", "programming", "algorithm", "function",
        "variable", "debugging", "compiler", "codechef", "machine",
        "bitcoin", "operation"
    };

    private String originalWord;
    private String scrambledWord;

    private JLabel scrambledWordLabel;
    private JTextField inputField;
    private JButton submitButton;
    private JLabel resultLabel;
    private JButton playAgainButton;

    public WordScrambleGameGUI() {
        setTitle("Word Scramble Game");
        setSize(600, 500);  // Increased height
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel mainPanel = new BackgroundPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // Padding

        JLabel titleLabel = new JLabel("Word Scramble Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        scrambledWordLabel = new JLabel("Scrambled: ");
        scrambledWordLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        scrambledWordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrambledWordLabel.setForeground(Color.YELLOW);
        mainPanel.add(scrambledWordLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 22));
        inputField.setMaximumSize(new Dimension(400, 40));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(inputField);
        mainPanel.add(Box.createVerticalStrut(20));

        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 18));
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitButton.addActionListener(e -> checkAnswer());
        mainPanel.add(submitButton);
        mainPanel.add(Box.createVerticalStrut(20));

        resultLabel = new JLabel("", JLabel.CENTER);
        resultLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultLabel.setForeground(Color.WHITE);
        mainPanel.add(resultLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        playAgainButton = new JButton("Play Again");
        playAgainButton.setVisible(false);
        playAgainButton.setFont(new Font("Arial", Font.BOLD, 18));
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playAgainButton.addActionListener(e -> {
            generateNewWord();
            inputField.setText("");
            resultLabel.setText("");
            playAgainButton.setVisible(false);
            submitButton.setEnabled(true);
        });
        mainPanel.add(playAgainButton);

        add(mainPanel);
        generateNewWord();
    }

    private void generateNewWord() {
        Random rand = new Random();
        originalWord = WORD_LIST[rand.nextInt(WORD_LIST.length)];
        scrambledWord = scrambleWord(originalWord);
        scrambledWordLabel.setText("Scrambled: " + scrambledWord);
    }

    private String scrambleWord(String word) {
        java.util.List<Character> characters = new java.util.ArrayList<>();
        for (char ch : word.toCharArray()) {
            characters.add(ch);
        }
        do {
            Collections.shuffle(characters);
        } while (new StringBuilder().append(characters).toString().equals(word));

        StringBuilder sb = new StringBuilder();
        for (char ch : characters) {
            sb.append(ch);
        }
        return sb.toString();
    }

    private void checkAnswer() {
        String userGuess = inputField.getText().trim();
        if (userGuess.equalsIgnoreCase(originalWord)) {
            resultLabel.setText("✅ Correct! The word was: " + originalWord);
        } else {
            resultLabel.setText("❌ Wrong! Try again.");
            return;
        }

        submitButton.setEnabled(false);
        playAgainButton.setVisible(true);
    }

    // Custom JPanel with image background
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel() {
            try {
                URL imageUrl = new URL("https://images.unsplash.com/photo-1504384308090-c894fdcc538d?auto=format&fit=crop&w=1050&q=80");
                backgroundImage = new ImageIcon(imageUrl).getImage();
            } catch (Exception e) {
                backgroundImage = null; // Will fallback to color
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Fallback soft gradient color
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(70, 130, 180), 0, getHeight(), new Color(100, 149, 237));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WordScrambleGameGUI game = new WordScrambleGameGUI();
            game.setVisible(true);
        });
    }
}
