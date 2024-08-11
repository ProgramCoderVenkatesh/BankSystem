package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;


public class MoneyTransferClass {
	
	String senderHolderName;
	String senderAccountNo;
	String senderBalance;
	
	String receiverHolderName;
	String receiverAccountNo;
	String receiverBalance;
	
	String transferAmount;

	@FXML
	TextField receiverAccountNoTextField;
	
	@FXML
	Label receiverHolderNameLabel;
	@FXML
	Label receiverAccountNoLabel;
	@FXML
	TextField transferAmountTextField;
	
	DecimalFormat df;
	
	public void setValue(String senderHolderName, String senderAccountNo, String senderBalance) {
		this.senderHolderName = senderHolderName;
		this.senderAccountNo = senderAccountNo;
		this.senderBalance = senderBalance;
	}
	
	public void moneyTransferFunc(String holderName, String accountNo, String balance) {
		receiverHolderName = holderName;
		receiverAccountNo = accountNo;
		receiverBalance = balance;
		
		receiverHolderNameLabel.setText("Holder Name : " + holderName);
		receiverAccountNoLabel.setText("Account No : " + receiverAccountNo);
	}
	
	public void fetchReceiverDetails(ActionEvent event) throws SQLException {
		
		String receiverAccountNo = receiverAccountNoTextField.getText();
		
		if(receiverAccountNo.equals(senderAccountNo)) {
			System.out.println("You can't transfer for yourself.");
			
			String alertMsg = "You can't transfer to yourself";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(receiverAccountNo.matches("\\d+")) {
			
			Conn conn = new Conn();
			conn.dbConnection();
			
			ResultSet rs = conn.readReceiverDetails(receiverAccountNo);
			
			try {
	            if (rs != null && rs.next()) {
	                receiverHolderName = rs.getString("holderName");
	                receiverAccountNo = rs.getString("accountNo");
	                receiverBalance = rs.getString("balance");

	                // Open the dialog box
	                try {
	                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Dialog.fxml"));
	                    Parent root = fxmlLoader.load();

	                    DialogController dialogController = fxmlLoader.getController();
	                    Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // Get the main stage
	                    dialogController.setReceiverDetails(senderHolderName, senderAccountNo, senderBalance, receiverHolderName, receiverAccountNo, receiverBalance, mainStage);

	                    Stage dialogStage = new Stage();
	                    dialogStage.setTitle("Dialog");
	                    dialogStage.setScene(new Scene(root, 400, 300));
	                    dialogStage.initModality(Modality.APPLICATION_MODAL); // Make it modal
	                    dialogStage.showAndWait(); // Wait for the dialog to close before continuing

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            } else {
	            	
	            	System.out.println("Account No. not found.");
	            	
	            	String alertMsg = "Account No. not found";
	                AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	                alertDialog.alertDialog(alertMsg);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
		}
	}
	
	public void transferAmountProceed() {
		
		transferAmount = transferAmountTextField.getText();
		
		if(!transferAmount.matches("\\d+")) {
			System.out.println("Alphabets not allowed for amount.");
			
			String alertMsg = "Alphabets not allowed for amount";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(Integer.parseInt(transferAmount) > 50000) {
			System.out.println("Transfer amount limited to 50000.");
			
			String alertMsg = "Transfer amount limited to 50000";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		// Check if sender has enough balance
	    if (Double.parseDouble(transferAmount) > Double.parseDouble(senderBalance)) {
	        System.out.println("Insufficient balance for transfer.");
	        
	        String alertMsg = "Insufficient balance for transfer";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
	        return;
	    }
	    
	    // Check if receiver has enough balance
	    if (Double.parseDouble(senderBalance) >= 10000000) {
	        System.out.println("Receiver dosen't have space to deposit.");
	        
	        String alertMsg = "Receiver dosen't have space to deposit";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
	        return;
	    }
		
		if(Integer.parseInt(transferAmount) == 0) {
			System.out.println("Transfer amount should be greater than 0.");
			
			String alertMsg = "Transfer amount should be greater than 0";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		DepositClass deposit = new DepositClass();
		WithdrawClass withdraw = new WithdrawClass();
		
		receiverBalance = String.valueOf(Double.parseDouble(receiverBalance) + Double.parseDouble(transferAmount));
		Double receiverBalanceDouble = Double.parseDouble(receiverBalance);
		df = new DecimalFormat("#0.00");
		receiverBalance = df.format(receiverBalanceDouble);
		
		int rowsAffectedWhileDeposit = deposit.updateDeposit(receiverHolderName, receiverAccountNo, receiverBalance);
		System.out.println("rowsAffectedWhileDeposit : " + rowsAffectedWhileDeposit);
		
		if (rowsAffectedWhileDeposit > 0) {
            System.out.println("Deposit balance updated successfully, " + rowsAffectedWhileDeposit + " rows affected");
            
        } else {
        	System.out.println("Transfer unsuccessful while deposit");
        	
        	String alertMsg = "Transfer unsuccessful";
        	AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
        	alertDialog.alertDialog(alertMsg);
        	
        	return;
        }
		
		senderBalance = String.valueOf(Double.parseDouble(senderBalance) - Double.parseDouble(transferAmount));
		Double senderBalanceDouble = Double.parseDouble(senderBalance);
		senderBalance = df.format(senderBalanceDouble);
		System.out.println(senderBalance);
		
		int rowsAffectedWhileWithdraw = withdraw.updateWithdraw(senderHolderName, senderAccountNo, senderBalance);
		System.out.println("rowsAffectedWhileWithdraw : " + rowsAffectedWhileWithdraw);
		
		if (rowsAffectedWhileWithdraw > 0) {
            System.out.println("Withdraw balance updated successfully, " + rowsAffectedWhileWithdraw + " rows affected");
            
        } else {
        	
        	receiverBalance = String.valueOf(Double.parseDouble(receiverBalance) - Double.parseDouble(transferAmount));
    		Double receiverBalanceDoubleWhileReverse = Double.parseDouble(senderBalance);
    		receiverBalance = df.format(receiverBalanceDoubleWhileReverse);
    		System.out.println(receiverBalance);
    		
        	int rowsAffectedWhileReverseDeposit = withdraw.updateWithdraw(receiverHolderName, receiverAccountNo, receiverBalance);
        	System.out.println("Transfer unsuccessful while withdraw");
        	
        	if(rowsAffectedWhileReverseDeposit > 0) {
        		System.out.println("Transfer unsuccessful while senders withdraw so, deposited amount reversed.");
        	}
        	
        	String alertMsg = "Transfer unsuccessful";
        	AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
        	alertDialog.alertDialog(alertMsg);
        	
        	return;
        }
		
		if((rowsAffectedWhileDeposit == 1) && (rowsAffectedWhileWithdraw == 1)) {
			
			Conn conn = new Conn();
			conn.dbConnection();
			int rowsAffectedWhileTransfer = conn.insertTransaction(senderHolderName, senderAccountNo, receiverHolderName, receiverAccountNo, transferAmount, senderBalance, receiverBalance);
			System.out.println("rowsAffectedWhileTransfer : " + rowsAffectedWhileTransfer);
			
			
			System.out.println("rowsAffectedWhileTransfer : " + rowsAffectedWhileTransfer);
			if(rowsAffectedWhileTransfer >= 1) {
				System.out.println("Amount transfer successful.");
				
				String alertMsg = "Amount transfer successful";
	        	AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	        	alertDialog.alertDialog(alertMsg);
			} else if(rowsAffectedWhileTransfer < 0 ) {
				
				reverseProcessTransactionFailed(deposit, withdraw);
				
				System.out.println("Amount transfer unsuccessful so, withdraw and deposit reversed");
				
				String alertMsg = "Amount transfer unsuccessful";
	        	AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	        	alertDialog.alertDialog(alertMsg);
			} else {
				
				reverseProcessTransactionFailed(deposit, withdraw);
				
				System.out.println("Amount transfer unsuccessful.");
				
				String alertMsg = "Amount transfer unsuccessful";
	        	AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	        	alertDialog.alertDialog(alertMsg);
			}
		}
		
		transferAmount = "";
	}
	
	public void reverseProcessTransactionFailed(DepositClass deposit, WithdrawClass withdraw) {
		
		receiverBalance = String.valueOf(Double.parseDouble(receiverBalance) - Double.parseDouble(transferAmount));
		Double receiverBalanceDoubleWhileReverse = Double.parseDouble(receiverBalance);
		receiverBalance = df.format(receiverBalanceDoubleWhileReverse);
		System.out.println(receiverBalance);
		
		senderBalance = String.valueOf(Double.parseDouble(senderBalance) + Double.parseDouble(transferAmount));
		Double senderBalanceDoubleWhileReverse = Double.parseDouble(senderBalance);
		senderBalance = df.format(senderBalanceDoubleWhileReverse);
		System.out.println(senderBalance);
		
		int rowsAffectedWhileReverseWithdraw = deposit.updateDeposit(senderHolderName, senderAccountNo, senderBalance);
		int rowsAffectedWhileReverseDeposit = withdraw.updateWithdraw(receiverHolderName, receiverAccountNo, receiverBalance);
	}
}

