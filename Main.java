import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private JPanel gamePanel;
    private JButton[][] buttons;
    private int numRows = 3;
    private int[] numCols = {5, 4, 3};
    private JButton turnButton;
    private int currentPlayer = 1;
    private JComboBox<String> themeDropdown;
    private String[] themes = {"Light Mode", "Dark Mode"};
    private Color[] themeColors = {Color.WHITE, Color.BLACK};
    private Color[] buttonColors = {Color.BLUE, Color.YELLOW};
    private String player1Name;
    private String player2Name;
    private int remainingBoxes;
    private int lastClickedRow;
    private int lastClickedCol;

    public Main() {
        getPlayerNames();

        setTitle("5-4-3 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        gamePanel = new JPanel(new GridLayout(numRows, getMaxColumns()));
        buttons = new JButton[numRows][];

        remainingBoxes = 0; // Initialize remainingBoxes

        for (int i = 0; i < numRows; i++) {
            buttons[i] = new JButton[numCols[i]];
            for (int j = 0; j < numCols[i]; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setBackground(buttonColors[currentPlayer - 1]);
                buttons[i][j].addActionListener(new BoxClickListener(i, j));
                gamePanel.add(buttons[i][j]);
                remainingBoxes++; // Increment remainingBoxes for each button
            }
            // Add empty buttons to fill remaining cells in the row
            for (int j = numCols[i]; j < getMaxColumns(); j++) {
                gamePanel.add(new JPanel());
            }
        }

        turnButton = new JButton(player1Name + "'s Turn");
        turnButton.addActionListener(new TurnButtonListener());

        themeDropdown = new JComboBox<>(themes);
        themeDropdown.addActionListener(new ThemeDropdownListener());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(turnButton);
        topPanel.add(themeDropdown);

        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    private void getPlayerNames() {
        String rulesMessage = "Welcome to the 5-4-3 Game!\n\n"
                + "Rules:\n"
                + " Each player takes turns clicking on boxes.\n"
                + " When a box is clicked, it disappears.\n"
                + " The player who clicks the last box wins!\n"
                + "  Enter Player Names:";

        JTextField player1Field = new JTextField(10);
        JTextField player2Field = new JTextField(10);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel rulesLabel = new JLabel(rulesMessage);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(rulesLabel, gbc);

        JLabel player1Label = new JLabel("Player 1:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(player1Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        player1Field.setPreferredSize(new Dimension(player1Field.getPreferredSize().width / 5, player1Field.getPreferredSize().height));
        panel.add(player1Field, gbc);

        JLabel player2Label = new JLabel("Player 2:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(player2Label, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        player2Field.setPreferredSize(new Dimension(player2Field.getPreferredSize().width / 5, player2Field.getPreferredSize().height));
        panel.add(player2Field, gbc);

        int result = JOptionPane.showConfirmDialog(null, panel, "5-4-3 Game",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            player1Name = player1Field.getText();
            player2Name = player2Field.getText();
        } else {
            System.exit(0);
        }
    }



    private int getMaxColumns() {
        int maxCols = 0;
        for (int cols : numCols) {
            if (cols > maxCols) {
                maxCols = cols;
            }
        }
        return maxCols;
    }

    private class BoxClickListener implements ActionListener {
        private int row;
        private int col;

        public BoxClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            buttons[row][col].setVisible(false);
            remainingBoxes--;

            lastClickedRow = row;
            lastClickedCol = col;

            // Check if it's the last box clicked
            if (remainingBoxes == 0) {
                // Game is over, display the winner's name
                String winnerName = (currentPlayer == 1) ? player2Name : player1Name;
                displayGameOverDialog(winnerName);
            } else {
                // Disable buttons in other rows if the game is not over
                for (int i = 0; i < numRows; i++) {
                    if (i != row) {
                        for (int j = 0; j < numCols[i]; j++) {
                            buttons[i][j].setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    private class TurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentPlayer = (currentPlayer == 1) ? 2 : 1;
            String playerName = (currentPlayer == 1) ? player1Name : player2Name;
            turnButton.setText(playerName + "'s Turn");

            // Enable all buttons for the current player's turn
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols[i]; j++) {
                    buttons[i][j].setEnabled(true);
                    buttons[i][j].setBackground(buttonColors[currentPlayer - 1]);
                }
            }
        }
    }

    private class ThemeDropdownListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = themeDropdown.getSelectedIndex();
            Color backgroundColor = themeColors[selectedIndex];
            Color buttonColor = buttonColors[currentPlayer - 1];

            getContentPane().setBackground(backgroundColor);
            gamePanel.setBackground(backgroundColor);
            turnButton.setBackground(buttonColor);

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols[i]; j++) {
                    buttons[i][j].setBackground(buttonColor);
                }
            }
        }
    }

    private void displayGameOverDialog(String winnerName) {
        String winner = (currentPlayer == 1) ? player1Name : player2Name;
        JOptionPane.showMessageDialog(this, "Congratulations, " + winner + "! You win!",
                "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}