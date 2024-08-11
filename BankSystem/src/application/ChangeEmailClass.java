package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChangeEmailClass {

	@FXML
	TextField currentEmailTextField;
	@FXML
	TextField newEmailTextField;
	@FXML
	TextField confirmEmailTextField;
	@FXML
	Button proccedBtn;
	
	private String currentEmail;
	private String holderName;
	private String accountNo;
	
	private String newEmail;
	private String confirmEmail;
	
	private Stage stage;
	
	public void setEmail(String holderName, String accountNo, String email) {
		this.holderName = holderName;
		this.accountNo = accountNo;
		currentEmail = email;
		this.stage = stage;
	}
	
	public void changeEmail() {
		
		if(!currentEmail.equals(currentEmailTextField.getText())) {
			System.out.println("Email Doesn't Exits..");
			
			alertMsgDisplay("Email Doesn't Exits");
			return;
		} 
		
		newEmail = newEmailTextField.getText();
		if(newEmail.matches("[a-z0-9._%+-@]+")) {
			confirmEmail = confirmEmailTextField.getText();
			if(!confirmEmail.matches("[a-z0-9._%+-@]+")) {
				System.out.println("Should only contain lowercase letters and numbers..");
				
				alertMsgDisplay("Should only contain lowercase letters and numbers");
	            return;
			} 
		} else {
			System.out.println("Should only contain lowercase letters and numbers..");
			
			alertMsgDisplay("Should only contain lowercase letters and numbers");
			return;
		}
		
		if((!newEmail.endsWith("@gmail.com")) || (!confirmEmail.endsWith("@gmail.com"))) {
			
			System.out.println("Invalid Email..");
			
			alertMsgDisplay("Invalid Email");
            return;
		} 
		
		if(newEmail.equals(confirmEmail)) {
			System.out.println("Email Matched..");
			
			Conn conn = new Conn();
			conn.dbConnection();
			int rowsAffected = conn.updateEmail(holderName, accountNo, newEmail);

			if (rowsAffected > 0) {
	            System.out.println("Email updated successfully, " + rowsAffected + " rows affected");
	            
	            alertMsgDisplay("Email updated successfully");
	        } else {
	            System.out.println("No rows were updated. Please check the holderName and accountNo.");
	            
	            alertMsgDisplay("Email not updated");
	        }
		} else {
			System.out.println("Email Doesn't Matched..");
			
			alertMsgDisplay("Email Doesn't Matched");
		}
	}
	
	public void alertMsgDisplay(String message) {
		
		String alertMsg = message;
        AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
        alertDialog.alertDialog(alertMsg);
	}
}
