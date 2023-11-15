package Account;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AccountBook extends JFrame {

    private ArrayList<Transaction> transactions = new ArrayList<>();
    public JTable transactionTable; // Use JTable instead of JTextArea
    private JTextField typeField;
    private JTextField amountField;
    private JTextField fundField;
    private JLabel totalAmountLabel;
    private JLabel myFundLabel;
    private int myFund = 0;
    public JPanel newTransactionPanel;
    public AccountBook() {
        //super("가계부");
        setSize(455, 500);
        JPanel newTransactionPanel = new JPanel(new GridLayout(4, 2));
        newTransactionPanel.add(new JLabel("보유 금액 추가:"));
        fundField = new JTextField();
        newTransactionPanel.add(fundField);
        newTransactionPanel.add(new JLabel("품목:"));
        typeField = new JTextField();
        newTransactionPanel.add(typeField);
        newTransactionPanel.add(new JLabel("가격:"));
        amountField = new JTextField();
        newTransactionPanel.add(amountField);


        totalAmountLabel = new JLabel("총 지출: 0원");
        newTransactionPanel.add(totalAmountLabel);

        myFundLabel = new JLabel("현재 보유 금액: 0원");
        newTransactionPanel.add(myFundLabel);


        JButton addButton = new JButton("지출 내용 추가");
        addButton.addActionListener(new AddTransactionAction());

        JButton clearButton = new JButton("모두 지우기");
        clearButton.addActionListener(new ClearAction());

        JButton moneyButton = new JButton("보유 금액 추가");
        moneyButton.addActionListener(new ShowFundAction());


        JPanel newinform = new JPanel(new GridLayout(1,2));
        newinform.add(new JLabel("총 지출: 0원"));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(moneyButton);

        // Initialize the table with an empty model
        transactionTable = new JTable(new Object[0][0], new String[]{"품목", "가격"});
        transactionTable.setFillsViewportHeight(true);



        getContentPane().setLayout(new BorderLayout());


        getContentPane().add(newTransactionPanel, BorderLayout.NORTH);

        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(transactionTable), BorderLayout.SOUTH); // Add the table to a JScrollPane
        transactionTable.setShowGrid(true);

        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        //setVisible(true);
    }

    private class AddTransactionAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String type = typeField.getText();
            try {
                int amount = Integer.parseInt(amountField.getText());
                Transaction transaction = new Transaction(type, amount);
                transactions.add(transaction);
                updateTransactionTable();
                updateTotalAmountLabel();

                myFund -= amount;
                myFundLabel.setText("현재 보유 금액: " + myFund + "원");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer for amount", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                typeField.setText("");
                amountField.setText("");
            }
        }
    }

    private class ClearAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            transactions.clear();
            updateTransactionTable();
            updateTotalAmountLabel();

            myFund = 0;
            myFundLabel.setText("현재 보유 금액: " + myFund + "원");
        }
    }

    private class ShowFundAction implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                myFund = Integer.parseInt(fundField.getText());
                myFundLabel.setText("현재 보유 금액: " + myFund + "원");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer for My Funds", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                fundField.setText("");
            }
        }
    }

    private void updateTransactionTable() { // Method to update the table
        Object[][] data = new Object[transactions.size()][2];
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            data[i][0] = transaction.getType();
            data[i][1] = transaction.getAmount();
        }
        transactionTable.setModel(new DefaultTableModel(data, new String[]{"품목", "가격"}));
    }

    private void updateTotalAmountLabel() {
        int total = getTotalAmount();
        totalAmountLabel.setText("총 지출: " + total +"원");
    }

    private int getTotalAmount() {
        int total = 0;
        for (Transaction transaction : transactions) {
            total += transaction.getAmount();
        }
        return total;
    }

    public class Transaction {
        private String type;
        private int amount;

        public Transaction(String type, int amount) {
            this.type = type;
            this.amount = amount;
        }

        public String getType() {
            return type;
        }

        public int getAmount() {
            return amount;
        }
    }

   /* public static void main(String[] args) {
        new AccountBook();
    }*/
}
