package application;

import java.util.Random;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SignUpClass {

	@FXML
	TextField firstNameTextField;
	@FXML
	TextField middleNameTextField;
	@FXML
	TextField lastNameTextField;
	@FXML
	TextField phoneTextField;
	@FXML
	TextField emailTextField;
	
	public void submit() {
		
		String firstName = firstNameTextField.getText();
		String middleName = middleNameTextField.getText();
		String lastName = lastNameTextField.getText();
		
		if(firstName.matches("[a-zA-Z]+") && middleName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+")) {
			String phone = phoneTextField.getText();
			if(phone.matches("\\d+") && phone.length() == 10) {
				String email = emailTextField.getText();
				if(email.endsWith("@gmail.com") && email.matches("[a-z0-9._%+-@]+")) {
					
					String fullName = firstName + " " + middleName + " " + lastName;
					
					Random random = new Random();
					int generatedAccountNo = random.nextInt((999999999 - 900000000) + 1) + 900000000;
					
					String pin = "0000";
					
					String balance = "0.00";
					
					System.out.println(balance);
					System.out.println(fullName);
					System.out.println(generatedAccountNo);
					System.out.println(pin);
					
					Conn conn = new Conn();
					conn.dbConnection();
					
					int rowsAffected = conn.insertDataAtUsers(fullName, generatedAccountNo, phone, email, balance, pin);
					
					System.out.println(rowsAffected);
					if (rowsAffected > 0) {
			            System.out.println("Users data inserted successfully, " + rowsAffected + " rows affected");
			            
			            String alertMsg = "Your Account No : " + generatedAccountNo;
			            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
			            alertDialog.alertDialogForAccountNo(alertMsg);
			        } else if (rowsAffected == -1) {
			        	System.out.println("Duplicate entries found with same phone or email");
			        	
			        	String alertMsg = "Duplicate entries found with same phone or email";
			            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
			            alertDialog.alertDialog(alertMsg);
			        } else {
			            System.out.println("Failed to sign up");
			            
			            String alertMsg = "Failed to sign up";
			            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
			            alertDialog.alertDialog(alertMsg);
			        }
				} else {
					System.out.println("Invalid Email..");
					
					String alertMsg = "Invalid Email";
		            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
		            alertDialog.alertDialog(alertMsg);
				}
			} else {
				System.out.println("Invalid phone number..");
				
				String alertMsg = "Invalid phone number";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
			}
		} else {
			System.out.println("Name should not contain decimals or symbols..");
			
			String alertMsg = "Name should not contain decimals or symbols";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		}
	}
}
