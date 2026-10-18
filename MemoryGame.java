import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame {

    private JPanel boardPanel;
    private JLabel scoreLabel, movesLabel, timerLabel;

    private ArrayList<MemoryCard> cards;

    private MemoryCard firstCard, secondCard;

    private int score = 0;
    private int moves = 0;
    private int seconds = 0;
    private int matchedPairs = 0;

    private Timer clock;
    private Timer flipTimer;

    private String[] fruits = {
        "🍎", "🍌", "🍇", "🍊",
        "🍓", "🍍", "🥝", "🍉"
    };

    public MemoryGame() {

        setTitle("Flip & Find - Memory Game");
        setSize(750, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Heading
        JLabel heading = new JLabel(
            "Flip & Find - Memory Challenge",
            JLabel.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 26));
        heading.setForeground(Color.RED);
        add(heading, BorderLayout.NORTH);

        // Score, moves and timer
        JPanel topPanel = new JPanel();

        scoreLabel = new JLabel("Score: 0");
        movesLabel = new JLabel("Moves: 0");
        timerLabel = new JLabel("Time: 0 sec");

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        movesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timerLabel.setFont(new Font("Arial", Font.BOLD, 18));

        topPanel.add(scoreLabel);
        topPanel.add(movesLabel);
        topPanel.add(timerLabel);

        // Game board
        boardPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        boardPanel.setBackground(Color.PINK);
        boardPanel.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        );

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // New Game button
        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        newGameButton.setBackground(Color.ORANGE);

        newGameButton.addActionListener(e -> startNewGame());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(newGameButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Timer
        clock = new Timer(1000, e -> {
            seconds++;
            timerLabel.setText("Time: " + seconds + " sec");
        });

        // Hide cards after one second
        flipTimer = new Timer(1000, e -> hideCards());
        flipTimer.setRepeats(false);

        startNewGame();
        setVisible(true);
    }

    // Create a fruit item
    private GameItem createItem(String fruit) {
        return new GameItem(fruit) {
            public void displayItem() {
                System.out.println(getSymbol());
            }
        };
    }

    // Start a new game
    private void startNewGame() {

        clock.stop();
        flipTimer.stop();

        score = 0;
        moves = 0;
        seconds = 0;
        matchedPairs = 0;

        firstCard = null;
        secondCard = null;

        scoreLabel.setText("Score: 0");
        movesLabel.setText("Moves: 0");
        timerLabel.setText("Time: 0 sec");

        cards = new ArrayList<>();

        // Add each fruit twice
        for (String fruit : fruits) {
            cards.add(new MemoryCard(createItem(fruit)));
            cards.add(new MemoryCard(createItem(fruit)));
        }

        Collections.shuffle(cards);
        boardPanel.removeAll();

        for (MemoryCard card : cards) {
            card.addActionListener(e -> checkCard(card));
            boardPanel.add(card);
        }

        boardPanel.revalidate();
        boardPanel.repaint();

        clock.start();
    }

    // Check selected cards
    private void checkCard(MemoryCard card) {

        if (card.isMatched() || card.isRevealed()
                || flipTimer.isRunning()) {
            return;
        }

        card.reveal();

        if (firstCard == null) {
            firstCard = card;
        } else {
            secondCard = card;
            moves++;

            movesLabel.setText("Moves: " + moves);

            if (firstCard.getSymbol().equals(secondCard.getSymbol())) {

                firstCard.setMatched();
                secondCard.setMatched();

                score += 10;
                matchedPairs++;

                scoreLabel.setText("Score: " + score);

                firstCard = null;
                secondCard = null;

                if (matchedPairs == fruits.length) {
                    finishGame();
                }

            } else {
                flipTimer.start();
            }
        }
    }

    // Hide unmatched cards
    private void hideCards() {

        firstCard.hideCard();
        secondCard.hideCard();

        firstCard = null;
        secondCard = null;
    }

    // Show final score
    private void finishGame() {

        clock.stop();

        JOptionPane.showMessageDialog(
            this,
            "Congratulations! You Won!\n\n"
            + "Final Score: " + score + "\n"
            + "Total Moves: " + moves + "\n"
            + "Time: " + seconds + " seconds",
            "Game Completed",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}