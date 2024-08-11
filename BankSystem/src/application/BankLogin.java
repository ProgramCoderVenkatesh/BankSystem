package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BankLogin {

	@FXML
	TextField nameTextField;
	@FXML
	TextField accountNoTextField;
	Button loginbtn;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void login(ActionEvent event) {
		String holderName = nameTextField.getText();
        String accountNoText = accountNoTextField.getText();
        
        if (holderName.isEmpty() || accountNoText.isEmpty()) {
            System.out.println("Please fill in both fields.");
            
            String alertMsg = "Please fill in both fields.";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
            return;
        }
        
        if (!holderName.matches("[a-zA-Z ]+")) {
            System.out.println("Please enter a valid Holder Name.");
            
            String alertMsg = "Please enter a valid Holder Name.";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
            return;
        }
        
        int accountNo;
        try {
            accountNo = Integer.parseInt(accountNoText);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid Account No.");
            
            String alertMsg = "Please enter a valid Account No.";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
            return;
        }
        
        try {
            Conn connection = new Conn();
            connection.dbConnection();
            ResultSet rs = connection.readAllDetails(holderName, String.valueOf(accountNo));

            if (rs == null) {
                System.out.println("Error executing query.");
                
                String alertMsg = "Error executing query.";
                AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
                alertDialog.alertDialog(alertMsg);
                return;
            }

            if (!rs.isBeforeFirst()) {
                System.out.println("No matching records found.");
                
                String alertMsg = "No matching records found.";
                AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
                alertDialog.alertDialog(alertMsg);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                root = loader.load();

                BankMenu bankMenu = loader.getController();
                bankMenu.menu(rs);

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                stage.setScene(scene);
                stage.show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public void redirectToSignUp(ActionEvent event) {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
