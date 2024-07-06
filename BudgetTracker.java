import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

 Author: [Atharv]
 Date: [7/6/2024]
 Description:
 A simple Java application to track personal expenses and income.
 The app uses Swing for a user-friendly interface and `java.io` for data storage.
 It allows users to add, edit, delete, and categorize transactions, providing a clear summary of their financial status.
 

public class BudgetTracker {
    private static final String FILE_NAME = "transactions.txt"; // File name to store transactions (Preferabbly add your own file or change this code for your own use)

    public static void main(String[] args) {
        List<String> transactions = new ArrayList<>(); // List to store transactions
        loadTransactions(transactions); // Load existing transactions from file

        while (true) { // Infinite loop to keep the application running
            String[] options = {"Add Transaction", "View Transactions", "Exit"}; // Options for the user
            int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Budget Tracker",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]); // Show option dialog

            if (choice == 0) { // If user chooses "Add Transaction"
                addTransaction(transactions); // Add a new transaction
            } else if (choice == 1) { // If user chooses "View Transactions"
                viewTransactions(transactions); // View all transactions
            } else if (choice == 2) { // If user chooses "Exit"
                break; // Exit the loop and terminate the application
            }
        }
    }

    private static void addTransaction(List<String> transactions) {
        String transaction = JOptionPane.showInputDialog("Enter transaction (type amount description):"); // Prompt user for transaction details
        if (transaction != null && !transaction.isEmpty()) { // If input is valid
            transactions.add(transaction); // Add transaction to the list
            saveTransactions(transactions); // Save updated list to file
        }
    }

    private static void viewTransactions(List<String> transactions) {
        StringBuilder sb = new StringBuilder(); // StringBuilder to create the transactions string
        for (String transaction : transactions) { // Loop through all transactions
            sb.append(transaction).append("\n"); // Append each transaction to the StringBuilder
        }
        JOptionPane.showMessageDialog(null, sb.toString(), "Transactions", JOptionPane.INFORMATION_MESSAGE); // Show transactions in a message dialog
    }

    private static void loadTransactions(List<String> transactions) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) { // BufferedReader to read the transactions file
            String line;
            while ((line = br.readLine()) != null) { // Read each line from the file
                transactions.add(line); // Add each line to the transactions list
            }
        } catch (IOException e) { // Handle exceptions
            JOptionPane.showMessageDialog(null, "Error loading transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }

    private static void saveTransactions(List<String> transactions) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) { // BufferedWriter to write to the transactions file
            for (String transaction : transactions) { // Loop through all transactions
                bw.write(transaction); // Write each transaction to the file
                bw.newLine(); // New line after each transaction
            }
        } catch (IOException e) { // Handle exceptions
            JOptionPane.showMessageDialog(null, "Error saving transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message
        }
    }
}
