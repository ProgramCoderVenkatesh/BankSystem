package application;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class WithdrawClass {
	
	@FXML
	TextField withdrawAmountTextField;
	
	private String holderName;
	private String accountNo;
	private String balance;

	public void setValue(String holderName, String accountNo, String balance) {
		this.holderName = holderName;
		this.accountNo = accountNo;
		this.balance = balance;
	}
	
	public void withdraw(MouseEvent event) {
		
		String enteredWithdrawAmount = withdrawAmountTextField.getText();
		
		if(!enteredWithdrawAmount.matches("\\d+")) {
			System.out.println("Alphabets not allowed for withdraw amount");
			
			String alertMsg = "Alphabets not allowed for withdraw amount";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(Integer.parseInt(enteredWithdrawAmount) > 50000) {
			System.out.println("Withdraw amount limited to 50000");
			
			String alertMsg = "Withdraw amount limited to 50000";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(Integer.parseInt(enteredWithdrawAmount) == 0) {
			System.out.println("Withdraw amount should be more than 0");
			
			String alertMsg = "Withdraw amount should be more than 0";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(Integer.parseInt(enteredWithdrawAmount) <= (int)Double.parseDouble(balance)) {
			
			String newBalance = String.valueOf(Double.parseDouble(balance) - Double.parseDouble(enteredWithdrawAmount));
			DecimalFormat df = new DecimalFormat("#0.00");
			newBalance = df.format(Double.parseDouble(newBalance));
			
			int rowsAffected = updateWithdraw(holderName, accountNo, newBalance);
			
			if (rowsAffected > 0) {
	            System.out.println("Withdraw balance updated successfully, " + rowsAffected + " rows affected");
	            balance = newBalance;
	            
	            String alertMsg = "Amount Withdrawn successfully";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
	        } else {
	            System.out.println("No rows were updated. Please check the holderName and accountNo.");
	            
	            String alertMsg = "Amount Withdraw Unsuccessful";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
	            return;
	        }
			
		} else {
			System.out.println("Your balance is low to withdraw..");
			
			String alertMsg = "Your balance is low to withdraw..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
	}

	public int updateWithdraw(String holderName, String accountNo, String newBalance) {
		
		Conn conn = new Conn();
		conn.dbConnection();
		
		int rowsAffected = conn.updateBalance(holderName, accountNo, newBalance);
		
		return rowsAffected;
	}
}
