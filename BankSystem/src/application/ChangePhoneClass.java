package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ChangePhoneClass {

	@FXML
	TextField currentPhoneTextField;
	@FXML
	TextField newPhoneTextField;
	@FXML
	TextField confirmPhoneTextField;
	@FXML
	Button proccedBtn;
	
	private String currentPhone;
	private String holderName;
	private String accountNo;
	
	private String newNumber;
	private String confirmNumber;
	
	public void setPhone(String holderName, String accountNo, String phone) {
		this.holderName = holderName;
		this.accountNo = accountNo;
		this.currentPhone = phone;
	}
	
	public void changePhone(ActionEvent event) {
		if(!currentPhone.equals(currentPhoneTextField.getText())) {
			System.out.println("Phone Number Doesn't Exits..");
			
			String alertMsg = "Phone Number Doesn't Exits..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
            return;
		}
		
		newNumber = newPhoneTextField.getText();
		if(newNumber.matches("\\d+")) {
			System.out.println(newNumber);
			confirmNumber = confirmPhoneTextField.getText();
			if(!confirmNumber.matches("\\d+")) {
				System.out.println("Only Numbers Allowed..");
				
				String alertMsg = "Only Numbers Allowed..";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
	            return;
			}
		} else {
			System.out.println("Only Numbers Allowed..");
			
			String alertMsg = "Only Numbers Allowed..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(!(newNumber.length() == 10) || !(confirmNumber.length() == 10)) {
			System.out.println("Invalid Number..");
			
			String alertMsg = "Invalid Number..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		if(newNumber.equals(confirmNumber)) {
			System.out.println("Matched");
			
			Conn conn = new Conn();
			conn.dbConnection();
			int rowsAffected = conn.updateNumber(holderName, accountNo, newNumber);

			if (rowsAffected > 0) {
	            System.out.println("Phone number updated successfully, " + rowsAffected + " rows affected");
	            
	            String alertMsg = "Phone number updated successfully";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
	        } else {
	            System.out.println("No rows were updated. Please check the holderName and accountNo.");
	            
	            String alertMsg = "Phone number not updated";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
	        }
		} else {
			System.out.println("Confirm number doesn't match..");
			
			String alertMsg = "Confirm number doesn't match..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
	}
}
