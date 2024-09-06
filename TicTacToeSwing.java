import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeSwing extends JFrame {
    private static final int SIZE = 3;
    private JButton[][] buttons = new JButton[SIZE][SIZE];
    private char currentPlayer = 'X';
    private JLabel statusLabel = new JLabel("Player X's turn");

    public TicTacToeSwing() {
        setTitle("Tic-Tac-Toe");
        setSize(320, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Set up status label
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(statusLabel, BorderLayout.NORTH);

        // Set up grid panel
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SIZE, SIZE, 5, 5));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(gridPanel, BorderLayout.CENTER);

        // Create buttons for the game grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.PLAIN, 24));
                button.setBackground(Color.LIGHT_GRAY);
                button.setPreferredSize(new Dimension(100, 100));
                button.addActionListener(new ButtonClickListener());
                buttons[row][col] = button;
                gridPanel.add(button);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (!button.getText().isEmpty()) {
                return; // Ignore if button is already clicked
            }

            button.setText(String.valueOf(currentPlayer));

            // Set color based on current player
            if (currentPlayer == 'X') {
                button.setBackground(Color.RED);
            } else {
                button.setBackground(Color.GREEN);
            }

            if (checkWin()) {
                statusLabel.setText("Player " + currentPlayer + " wins!");
                statusLabel.setFont(new Font("Arial", Font.BOLD, 24));
                statusLabel.setForeground(Color.RED);
                int option = JOptionPane.showOptionDialog(
                        TicTacToeSwing.this,
                        "Do you want to start a new game or exit?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[] {"Start New Game", "End Game"},
                        "Start New Game"
                );

                if (option == 0) { // Start New Game
                    resetGame();
                } else if (option == 1) { // End Game
                    System.exit(0);
                }
                return;
            }

            if (isBoardFull()) {
                statusLabel.setText("The game is a draw!");
                statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                statusLabel.setForeground(Color.BLACK);

                int option = JOptionPane.showOptionDialog(
                        TicTacToeSwing.this,
                        "Do you want to start a new game or exit?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[] {"Start New Game", "End Game"},
                        "Start New Game"
                );

                if (option == 0) { // Start New Game
                    resetGame();
                } else if (option == 1) { // End Game
                    System.exit(0);
                }
                return;
            }

            // Switch player and update status label
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            statusLabel.setText("Player " + currentPlayer + "'s turn");
            statusLabel.setFont(new Font("Arial", Font.PLAIN, 18));
            statusLabel.setForeground(Color.BLACK);
        }
    }

    private boolean checkWin() {
        // Check rows and columns
        for (int i = 0; i < SIZE; i++) {
            if (buttons[i][0].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][1].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[i][2].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
            if (buttons[0][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[1][i].getText().equals(String.valueOf(currentPlayer)) &&
                buttons[2][i].getText().equals(String.valueOf(currentPlayer))) {
                return true;
            }
        }
        // Check diagonals
        if (buttons[0][0].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][2].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }
        if (buttons[0][2].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[1][1].getText().equals(String.valueOf(currentPlayer)) &&
            buttons[2][0].getText().equals(String.valueOf(currentPlayer))) {
            return true;
        }

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void resetGame() {
        currentPlayer = 'X';
        statusLabel.setText("Player X's turn");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(Color.BLACK);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeSwing game = new TicTacToeSwing();
            game.setVisible(true);
        });
    }
}
