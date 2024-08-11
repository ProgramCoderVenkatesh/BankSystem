package application;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class DepositClass {

	@FXML
	TextField depositAmountTextField;
	
	private String holderName;
	private String accountNo;
	private String balance;
	
	public void setValue(String holderName, String accountNo, String balance) {
		
		this.holderName = holderName;
		this.accountNo = accountNo;
		this.balance = balance;
	}
	
	public void deposit(MouseEvent event) {
		
		String enteredDepositAmount = depositAmountTextField.getText();
		
		if(!enteredDepositAmount.matches("\\d+")) {
			System.out.println("Alphabets not allowed for deposit amount");
			
			String alertMsg = "Alphabets not allowed for deposit amount";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(Integer.parseInt(enteredDepositAmount) > 50000) {
			System.out.println("Deposit amount limited to 50000");
			
			String alertMsg = "Deposit amount limited to 50000";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(Integer.parseInt(enteredDepositAmount) == 0) {
			System.out.println("Deposit amount should be more than 0");
			
			String alertMsg = "Deposit amount should be more than 0";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(((int)Double.parseDouble(balance) + Integer.parseInt(enteredDepositAmount)) <= 10000000) {
			
			String newBalance = String.valueOf(Double.parseDouble(balance) + Double.parseDouble(enteredDepositAmount));
			DecimalFormat df = new DecimalFormat("#0.00");
			newBalance = df.format(Double.parseDouble(newBalance));
			
			int rowsAffected = updateDeposit(holderName, accountNo, newBalance);
			
			if (rowsAffected > 0) {
	            System.out.println("Deposit balance updated successfully, " + rowsAffected + " rows affected");
	            
	            String alertMsg = "Amount Deposited Successfully";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
	            balance = newBalance;
	        } else {
	            System.out.println("No rows were updated. Please check the holderName and accountNo.");
	            
	            String alertMsg = "Amount Deposit Unsuccessful";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
	            return;
	        }
		} else {
			System.out.println("Your balance already reached maxed amount..");
			
			String alertMsg = "Your balance already reached maxed amount..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
	}
	
	public int updateDeposit(String holderName, String accountNo, String newBalance) {
		
		Conn conn = new Conn();
		conn.dbConnection();
		
		int rowsAffected = conn.updateBalance(holderName, accountNo, newBalance);
		
		return rowsAffected;
	}
}
