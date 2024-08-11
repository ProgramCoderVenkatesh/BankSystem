package application;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BankMenu {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	Label holderNameLabel;
	@FXML
	Label accountNoLabel;
	@FXML
	Label balanceLabel;

	@FXML
	ImageView moneyTransferImageView;
	
	String holderName;
	String accountNo;
	String phone;
	String email;
	String pin;
	String balance;
	ResultSet rs;
	
	public void menu(ResultSet rs) {
		
		this.rs = rs;
		try {
            if (rs.next()) {
                this.holderName = rs.getString("holderName");
                this.accountNo = rs.getString("accountNo");
                this.phone = rs.getString("phone");
                this.email = rs.getString("email");
                this.pin = rs.getString("pin");
                this.balance = rs.getString("balance");

                holderNameLabel.setText(holderName);
                accountNoLabel.setText(accountNo);
                
//                DecimalFormat df = new DecimalFormat("#0.00");
                balanceLabel.setText(balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void details(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Details.fxml"));
		root = loader.load();
		
		DetailsClass details = loader.getController();
		details.detailsFunc(holderName, accountNo, phone, email, balance);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void transfer(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EnterPin.fxml"));
		root = loader.load();
		
		EnterPinClass enterPin = loader.getController();
		enterPin.setValue(holderName, accountNo, pin, balance, "transfer");
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void history(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("History.fxml"));
		root = loader.load();
		
		HistoryClass history = loader.getController();
		history.setValue(holderName, accountNo);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void withdraw(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EnterPin.fxml"));
		root = loader.load();
		
		EnterPinClass enterPin = loader.getController();
		enterPin.setValue(holderName, accountNo, pin, balance, "withdraw");
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void deposit(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("EnterPin.fxml"));
		root = loader.load();
		
		EnterPinClass enterPin = loader.getController();
		enterPin.setValue(holderName, accountNo, pin, balance, "deposit");
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void setOrChangePin(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePinConfirmation.fxml"));
		root = loader.load();
		
		SetOrChangePinClass setOrChangePin = loader.getController();
		setOrChangePin.setValue(holderName, accountNo, phone, email);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void changePhone(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePhone.fxml"));
		root = loader.load();
		
		ChangePhoneClass changePhone = loader.getController();
		changePhone.setPhone(holderName, accountNo, phone);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
	
	public void changeEmail(MouseEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeEmail.fxml"));
		root = loader.load();
		
		ChangeEmailClass changeEmail = loader.getController();
		changeEmail.setEmail(holderName, accountNo, email);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
