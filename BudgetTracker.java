import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/*
* Author: [Atharv]
* Date: [7/6/2024]
* Description:
* A simple Java application to track personal expenses and income.
* The app uses Swing for a user-friendly interface and `java.io` for data storage.
* It allows users to add, edit, delete, and categorize transactions, providing a clear summary of their financial status.
*/

public class BudgetTracker {
	private static final String FILE_NAME = "transactions.txt"; // File name to store transactions
	private static double balance = 0.0; // Variable to store the balance

	public static void main(String[] args) {
		List<Transaction> transactions = new ArrayList<>(); // List to store transactions
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

	private static void addTransaction(List<Transaction> transactions) {
		String amountStr = JOptionPane.showInputDialog("Enter transaction amount (positive for income, negative for expense):"); // Prompt user for transaction amount
		String description = JOptionPane.showInputDialog("Enter transaction description:"); // Prompt user for transaction description

		if (amountStr != null && !amountStr.isEmpty() && description != null && !description.isEmpty()) { // If inputs are valid
			try {
				double amount = Double.parseDouble(amountStr); // Parse the amount
				balance += amount; // Update the balance
				Transaction transaction = new Transaction(amount, description); // Create a new transaction
				transactions.add(transaction); // Add transaction to the list
				saveTransactions(transactions); // Save updated list to file
			} catch (NumberFormatException e) { // Handle invalid amount input
				JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE); // Show error message
			}
		}
	}

	private static void viewTransactions(List<Transaction> transactions) {
		StringBuilder sb = new StringBuilder(); // StringBuilder to create the transactions string
		for (Transaction transaction : transactions) { // Loop through all transactions
			sb.append(transaction).append("\n"); // Append each transaction to the StringBuilder
		}
		sb.append("\nFinal Balance: ").append(balance); // Append the final balance
		JOptionPane.showMessageDialog(null, sb.toString(), "Transactions", JOptionPane.INFORMATION_MESSAGE); // Show transactions in a message dialog
	}

	private static void loadTransactions(List<Transaction> transactions) {
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) { // BufferedReader to read the transactions file
			String line;
			while ((line = br.readLine()) != null) { // Read each line from the file
				String[] parts = line.split(","); // Split the line into amount and description
				double amount = Double.parseDouble(parts[0]); // Parse the amount
				String description = parts[1]; // Get the description
				Transaction transaction = new Transaction(amount, description); // Create a new transaction
				transactions.add(transaction); // Add transaction to the list
				balance += amount; // Update the balance
			}
		} catch (IOException e) { // Handle exceptions
			JOptionPane.showMessageDialog(null, "Error loading transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message
		}
	}

	private static void saveTransactions(List<Transaction> transactions) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) { // BufferedWriter to write to the transactions file
			for (Transaction transaction : transactions) { // Loop through all transactions
				bw.write(transaction.getAmount() + "," + transaction.getDescription()); // Write each transaction to the file
				bw.newLine(); // New line after each transaction
			}
		} catch (IOException e) { // Handle exceptions
			JOptionPane.showMessageDialog(null, "Error saving transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE); // Show error message
		}
	}

	// Inner class to represent a transaction
	private static class Transaction {
		private double amount; // Amount of the transaction
		private String description; // Description of the transaction

		public Transaction(double amount, String description) {
			this.amount = amount; // Initialize amount
			this.description = description; // Initialize description
		}

		public double getAmount() {
			return amount; // Return the amount
		}

		public String getDescription() {
			return description; // Return the description
		}

		@Override
		public String toString() {
			return "Amount: " + amount + ", Description: " + description; // Return string representation of the transaction
		}
	}
}
