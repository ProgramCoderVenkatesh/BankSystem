package application;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailsClass {

	@FXML
	Label holderNameLabel;
	@FXML
	Label accountNoLabel;
	@FXML
	Label phoneLabel;
	@FXML
	Label emailLabel;
	@FXML
	Label balanceLabel;
	
	public void detailsFunc(String holderName, String accountNo, String phone, String email, String balance) {
			
//		DecimalFormat df = new DecimalFormat("#0.00");
		
		holderNameLabel.setText("Holder Name: " + holderName);
		accountNoLabel.setText("Account No.: " + accountNo);
		phoneLabel.setText("Phone No.: " + phone);
		emailLabel.setText("Email: " + email);
		balanceLabel.setText("Balance: " + balance);
	}
}
