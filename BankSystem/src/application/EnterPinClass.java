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

public class EnterPinClass {

	@FXML
    private TextField pinTextField1;
    @FXML
    private TextField pinTextField2;
    @FXML
    private TextField pinTextField3;
    @FXML
    private TextField pinTextField4;
    
    private Stage stage;
	private Scene scene;
	private Parent root;
	
	String holderName;
	String accountNo;
	String balance;
	
	String process;
	String pin;
    
    @FXML
    public void initialize() {
        // Focus the first TextField at the beginning
        pinTextField1.requestFocus();
    }
    
    @FXML
    private void handlePinTextField1() {
    	
    	if (!pinTextField1.getText().matches("\\d")) {
    		pinTextField1.setText("");
    	}
    	
        if (pinTextField1.getText().matches("\\d") && pinTextField1.getText().length() == 1) {
            pinTextField2.requestFocus();
        }
    }
    
    @FXML
    private void handlePinTextField2() {
    	
    	if (!pinTextField2.getText().matches("\\d")) {
    		pinTextField2.setText("");
    	}
    	
        if (pinTextField2.getText().matches("\\d") && pinTextField2.getText().length() == 1) {
            pinTextField3.requestFocus();
        } else if (pinTextField2.getText().length() < 1) {
        	pinTextField1.requestFocus();
        }
    }
    
    @FXML
    private void handlePinTextField3() {
    	
    	if (!pinTextField3.getText().matches("\\d")) {
    		pinTextField3.setText("");
    	}
    	
        if (pinTextField3.getText().matches("\\d") && pinTextField3.getText().length() == 1) {
            pinTextField4.requestFocus();
        } else if (pinTextField3.getText().length() < 1) {
        	pinTextField2.requestFocus();
        }
    }
    
    @FXML
    private void handlePinTextField4() {
    	
        if (pinTextField3.getText().matches("\\d") && pinTextField4.getText().length() > 1) {
            pinTextField4.setText(Character.toString(pinTextField4.getText().charAt(1)));
            pinTextField4.positionCaret(pinTextField4.getText().length()); // Position the caret at the end
        }
        else if (pinTextField4.getText().length() < 1) {
        	pinTextField3.requestFocus();
        }
        
        if (!pinTextField4.getText().matches("\\d")) {
    		pinTextField4.setText("");
    		pinTextField3.requestFocus();
    	}
    }
	
	public void setValue(String holderName, String accountNo, String pin, String balance, String process) {

		this.holderName = holderName;
		this.accountNo = accountNo;
		this.pin = pin;
		this.balance = balance;
		this.process = process;
	}
	
	public void proceedTo(ActionEvent event) throws IOException {
		
		if(pin.equals("0000")) {
			System.out.println("Pin is not set..");
			
			String alertMsg = "Pin is not set";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
			return;
		}
		
		String getEnteredPin = pinTextField1.getText() + pinTextField2.getText() + pinTextField3.getText() + pinTextField4.getText();
		
		if(process.equals("withdraw") && pin.equals(getEnteredPin)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Withdraw.fxml"));
			root = loader.load();
			
			WithdrawClass withdraw = loader.getController();
			withdraw.setValue(holderName, accountNo, balance);
			
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
		else if(process.equals("deposit") && pin.equals(getEnteredPin)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Deposit.fxml"));
			root = loader.load();
			
			DepositClass deposit = loader.getController();
			deposit.setValue(holderName, accountNo, balance);
			
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		}
		else if(process.equals("transfer") && pin.equals(getEnteredPin)) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceiverAccountNo.fxml"));
			root = loader.load();
			
			MoneyTransferClass moneyTransfer = loader.getController();
			moneyTransfer.setValue(holderName, accountNo, balance);
			
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
		} else {
			System.out.println("Pin is incorrect");
			
			String alertMsg = "Pin is incorrect";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		}
	}
}
