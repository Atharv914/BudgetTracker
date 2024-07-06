import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

public class BudgetTracker {
	private static final String FILE_NAME = "transactions.txt";

	public static void main(String[] args) {
		List<String> transactions = new ArrayList<>();
		loadTransactions(transactions);

		while (true) {
			String[] options = {"Add Transaction", "View Transactions", "Exit"};
			int choice = JOptionPane.showOptionDialog(null, "Choose an option", "Budget Tracker",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

			if (choice == 0) {
				addTransaction(transactions);
			} else if (choice == 1) {
				viewTransactions(transactions);
			} else if (choice == 2) {
				break;
			}
		}
	}

	private static void addTransaction(List<String> transactions) {
		String transaction = JOptionPane.showInputDialog("Enter transaction (type amount description):");
		if (transaction != null && !transaction.isEmpty()) {
			transactions.add(transaction);
			saveTransactions(transactions);
		}
	}

	private static void viewTransactions(List<String> transactions) {
		StringBuilder sb = new StringBuilder();
		for (String transaction : transactions) {
			sb.append(transaction).append("\n");
		}
		JOptionPane.showMessageDialog(null, sb.toString(), "Transactions", JOptionPane.INFORMATION_MESSAGE);
	}

	private static void loadTransactions(List<String> transactions) {
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
			String line;
			while ((line = br.readLine()) != null) {
				transactions.add(line);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error loading transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private static void saveTransactions(List<String> transactions) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
			for (String transaction : transactions) {
				bw.write(transaction);
				bw.newLine();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error saving transactions: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
