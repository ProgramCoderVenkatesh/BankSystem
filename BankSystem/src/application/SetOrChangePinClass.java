package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SetOrChangePinClass {

	@FXML
	TextField confirmPhoneTextField;
	@FXML
	TextField confirmEmailTextField;
	@FXML
	TextField newPinTextField;
	@FXML
	TextField confirmPinTextField;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private String currentPhone;
	private String currentEmail;
	private String holderName;
	private String accountNo;
	
	public void setValue(String holderName, String accountNo, String phone, String email) {
		this.holderName = holderName;
		this.accountNo = accountNo;
		currentPhone = phone;
		currentEmail = email;
	}
	
	public void confirmation(ActionEvent event) throws IOException {
		
		String phone = confirmPhoneTextField.getText();
		if(phone.matches("\\d+")) {
			if(phone.length() == 10) {
				if(phone.equals(currentPhone)) {
					System.out.println("Phone matched..");
				} else {
					System.out.println("Phone not matched..");
					
					String alertMsg = "Phone not matched";
		            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
		            alertDialog.alertDialog(alertMsg);
					return;
				}
			} else {
				System.out.println("Invalid number..");
				
				String alertMsg = "Invalid number";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
				return;
			}
		} else {
			System.out.println("Only numbers allowed..");
			
			String alertMsg = "Only numbers allowed";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		String email = confirmEmailTextField.getText();
		if(email.matches("[a-z0-9._%+-@]+")) {
			if(email.endsWith("@gmail.com")) {
				if(email.equals(currentEmail)) {
					System.out.println("Email matched..");
				} else {
					System.out.println("Email not matched..");
					
					String alertMsg = "Email not matched";
		            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
		            alertDialog.alertDialog(alertMsg);
					return;
				}
			} else {
				System.out.println("Invalid email..");
				
				String alertMsg = "Invalid email";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
				return;
			}
		} else {
			System.out.println("Only lowercase and numbers allowed..");
			
			String alertMsg = "Only lowercase and numbers allowed";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SetOrChangePin.fxml"));
		root = loader.load();
		
		SetOrChangePinClass setOrChangePin = loader.getController();
		setOrChangePin.setValue(holderName, accountNo, currentPhone, currentEmail);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void changePin(ActionEvent event) {
		
		String newEnteredPin = newPinTextField.getText();
		String confirmEnteredPin = confirmPinTextField.getText();
		
		if(newEnteredPin.matches("\\d+") && confirmEnteredPin.matches("\\d+")) {
			if(!newEnteredPin.equals("0000")) {
				if((newEnteredPin.length()) == 4 && (confirmEnteredPin.length() == 4)) {
					if(newEnteredPin.equals(confirmEnteredPin)) {
						
						Conn conn = new Conn();
						conn.dbConnection();
						int rowsAffected = conn.updatePin(holderName, accountNo, currentPhone, currentEmail, newEnteredPin);

						if (rowsAffected > 0) {
				            System.out.println("Securit pin updated successfully, " + rowsAffected + " rows affected");
				            
				            System.out.println("Pin set successfully..");
				            
				            String alertMsg = "Pin set successfully";
				            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
				            alertDialog.alertDialog(alertMsg);
				        } else {
				            System.out.println("No rows were updated. Please check the holderName and accountNo.");
				            
				            String alertMsg = "Pin Set Unsuccessful";
				            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
				            alertDialog.alertDialog(alertMsg);
				        }
					} else {
						System.out.println("Confirm pin not matched..");
						
						String alertMsg = "Confirm pin not matched";
			            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
			            alertDialog.alertDialog(alertMsg);
					}
				} else {
					System.out.println("Only 4-digits pin allowed..");
					
					String alertMsg = "Only 4-digits pin allowed";
		            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
		            alertDialog.alertDialog(alertMsg);
				}
			} else {
				System.out.println("Invalid pin..");
				
				String alertMsg = "Invalid pin";
	            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
	            alertDialog.alertDialog(alertMsg);
			}
		} else {
			System.out.println("Only numbers allowed..");
			
			String alertMsg = "Only numbers allowed";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		}
	}
}
