

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class HangmanGame extends JFrame {

    private ArrayList<String> words;
    private String currentWord;
    private StringBuilder displayedWord;
    private int attempts;
    private int wins;
    private int losses;
    private int maxAttempts;
    private JLabel wordLabel;
    private JLabel attemptsLabel;
    private JTextField guessField;
    private JButton guessButton;

    public HangmanGame() {
        words = new ArrayList<>();
        words.add("java");
        words.add("programming");
        words.add("swing");
        words.add("interface");
        words.add("random");
        words.add("object");
        words.add("method");
        words.add("variable");
        words.add("constructor");
        words.add("exception");

        currentWord = "";
        displayedWord = new StringBuilder();
        attempts = 0;
        wins = 0;
        losses = 0;
        maxAttempts = 6; // Default difficulty is set to 'medium'

        setTitle("Hangman Game");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        wordLabel = new JLabel("Word: ");
        attemptsLabel = new JLabel("Attempts left: " + maxAttempts);
        topPanel.add(wordLabel);
        topPanel.add(attemptsLabel);

        JPanel centerPanel = new JPanel();
        guessField = new JTextField(1);
        guessButton = new JButton("Guess");
        centerPanel.add(new JLabel("Enter your guess: "));
        centerPanel.add(guessField);
        centerPanel.add(guessButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        guessButton.addActionListener(new GuessButtonListener());

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem editWordsItem = new JMenuItem("Edit Words");
        JMenuItem statisticsItem = new JMenuItem("Statistics");
        JMenuItem exitItem = new JMenuItem("Exit");

        newGameItem.addActionListener(new NewGameListener());
        editWordsItem.addActionListener(new EditWordsListener());
        statisticsItem.addActionListener(new StatisticsListener());
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(newGameItem);
        gameMenu.add(editWordsItem);
        gameMenu.add(statisticsItem);
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);

        JMenu difficultyMenu = new JMenu("Difficulty");
        JMenuItem easyItem = new JMenuItem("Easy");
        JMenuItem mediumItem = new JMenuItem("Medium");
        JMenuItem hardItem = new JMenuItem("Hard");

        easyItem.addActionListener(e -> setDifficulty(10));
        mediumItem.addActionListener(e -> setDifficulty(6));
        hardItem.addActionListener(e -> setDifficulty(4));

        difficultyMenu.add(easyItem);
        difficultyMenu.add(mediumItem);
        difficultyMenu.add(hardItem);
        menuBar.add(difficultyMenu);

        setJMenuBar(menuBar);
    }

    private void setDifficulty(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        attemptsLabel.setText("Attempts left: " + maxAttempts);
    }

    private void startNewGame() {
        Random random = new Random();
        currentWord = words.get(random.nextInt(words.size()));
        displayedWord.setLength(0);
        for (int i = 0; i < currentWord.length(); i++) {
            displayedWord.append("_");
        }
        attempts = 0;
        wordLabel.setText("Word: " + displayedWord);
        attemptsLabel.setText("Attempts left: " + maxAttempts);
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
    }

    private void updateDisplayedWord(char guess) {
        boolean correctGuess = false;
        for (int i = 0; i < currentWord.length(); i++) {
            if (currentWord.charAt(i) == guess) {
                displayedWord.setCharAt(i, guess);
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            attempts++;
        }

        wordLabel.setText("Word: " + displayedWord);
        attemptsLabel.setText("Attempts left: " + (maxAttempts - attempts));

        if (displayedWord.toString().equals(currentWord)) {
            JOptionPane.showMessageDialog(this, "You win!");
            wins++;
            guessField.setEnabled(false);
            guessButton.setEnabled(false);
        } else if (attempts >= maxAttempts) {
            JOptionPane.showMessageDialog(this, "You lose! The word was: " + currentWord);
            losses++;
            guessField.setEnabled(false);
            guessButton.setEnabled(false);
        }
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String guess = guessField.getText();
            if (guess.length() == 1) {
                updateDisplayedWord(guess.charAt(0));
                guessField.setText("");
            } else {
                JOptionPane.showMessageDialog(HangmanGame.this, "Please enter a single letter.");
            }
        }
    }

    private class NewGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            startNewGame();
        }
    }

    private class EditWordsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newWord = JOptionPane.showInputDialog("Enter a new word:");
            if (newWord != null && !newWord.isEmpty()) {
                words.add(newWord);
                JOptionPane.showMessageDialog(HangmanGame.this, "Word added!");
            }
        }
    }

    private class StatisticsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(HangmanGame.this, "Wins: " + wins + "\nLosses: " + losses);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HangmanGame game = new HangmanGame();
            game.setVisible(true);
            game.startNewGame();
        });
    }
}

