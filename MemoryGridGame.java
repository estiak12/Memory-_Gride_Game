import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGridGame extends JFrame {
    private final int GRID_ROWS = 3;
    private final int GRID_COLS = 3;
    private final int GRID_SIZE = GRID_ROWS * GRID_COLS;
    private JButton[] gridButtons = new JButton[GRID_SIZE];
    private int[] correctNumbers = new int[GRID_SIZE];
    private JPanel gridPanel;
    private JLabel statusLabel;
    private int currentGuessNumber = 1;
    private Timer hideTimer;
    private JButton restartButton;

    public MemoryGridGame() {
        setTitle("Memory Grid Game");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(40, 40, 40));

        statusLabel = new JLabel("Memorize the numbers!", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 20));
        statusLabel.setForeground(Color.WHITE);
        add(statusLabel, BorderLayout.NORTH);

        gridPanel = new JPanel(new GridLayout(GRID_ROWS, GRID_COLS, 10, 10));
        gridPanel.setBackground(new Color(40, 40, 40));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(gridPanel, BorderLayout.CENTER);

        // Restart button (hidden initially)
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartButton.setVisible(false);
        restartButton.addActionListener(e -> restartGame());
        add(restartButton, BorderLayout.SOUTH);

        initializeGame();
        setVisible(true);
    }

    private void initializeGame() {
        currentGuessNumber = 1;
        restartButton.setVisible(false);
        statusLabel.setText("Memorize the numbers!");

        gridPanel.removeAll();

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= GRID_SIZE; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i < GRID_SIZE; i++) {
            correctNumbers[i] = numbers.get(i);
            JButton btn = new JButton(String.valueOf(correctNumbers[i]));
            btn.setFont(new Font("Arial", Font.BOLD, 36));
            btn.setBackground(Color.WHITE);
            btn.setEnabled(true);
            int index = i;
            btn.addActionListener(e -> handleGridClick(index));
            gridButtons[i] = btn;
            gridPanel.add(btn);
        }

        gridPanel.revalidate();
        gridPanel.repaint();

        // Start timer after UI renders
        SwingUtilities.invokeLater(() -> {
            if (hideTimer != null && hideTimer.isRunning()) {
                hideTimer.stop();
            }
            hideTimer = new Timer(5000, e -> hideNumbersAndStart());
            hideTimer.setRepeats(false);
            hideTimer.start();
        });
    }

    private void hideNumbersAndStart() {
        for (JButton btn : gridButtons) {
            btn.setText("");
            btn.setBackground(Color.BLACK);
        }
        statusLabel.setText("Find number: 1");
    }

    private void handleGridClick(int index) {
        int numberAtGrid = correctNumbers[index];
        if (numberAtGrid == currentGuessNumber) {
            gridButtons[index].setBackground(Color.GREEN);
            gridButtons[index].setText(String.valueOf(numberAtGrid));
            currentGuessNumber++;
            if (currentGuessNumber > GRID_SIZE) {
                gameOver(true);
            } else {
                statusLabel.setText("Find number: " + currentGuessNumber);
            }
        } else {
            gridButtons[index].setBackground(Color.RED);
            revealAllNumbers();
            gameOver(false);
        }
    }

    private void gameOver(boolean isWin) {
        disableAllButtons();
        restartButton.setVisible(true);

        int finalScore = isWin ? GRID_SIZE : currentGuessNumber - 1;
        String message;
        if (isWin) {
            message = "🎉 You found all numbers in order!\nFinal Score: " + finalScore + " / " + GRID_SIZE;
            statusLabel.setText("✅ You win! Final Score: " + finalScore + " / " + GRID_SIZE);
        } else {
            message = "❌ Wrong guess! Your score: " + finalScore + " / " + GRID_SIZE;
            statusLabel.setText("Game Over! Score: " + finalScore + " / " + GRID_SIZE);
        }

        JOptionPane.showMessageDialog(this, message);
    }

    private void revealAllNumbers() {
        for (int i = 0; i < GRID_SIZE; i++) {
            gridButtons[i].setText(String.valueOf(correctNumbers[i]));
        }
    }

    private void disableAllButtons() {
        for (JButton btn : gridButtons) {
            btn.setEnabled(false);
        }
    }

    private void restartGame() {
        initializeGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGridGame::new);
    }
}
