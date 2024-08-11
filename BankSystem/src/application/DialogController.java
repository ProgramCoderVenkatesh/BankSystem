package application;

import java.io.IOException;
import java.text.DecimalFormat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DialogController {
	
	private Stage mainStage;

	@FXML
	Label receiverNameDisplay;
	@FXML
	Label recieverAccountDisplay;
	
	private String senderHolderName;
	private String senderAccountNo;
	private String senderBalance;
	
	private String receiverHolderName;
	private String receiverAccountNo;
	private String receiverBalance;
	
	public void setReceiverDetails(String senderHolderName, String senderAccountNo, String senderBalance, String receiverHolderName, String receiverAccountNo, String receiverBalance, Stage mainStage) {
		this.senderHolderName = senderHolderName;
		this.senderAccountNo = senderAccountNo;
		this.senderBalance = senderBalance;
		this.receiverHolderName = receiverHolderName;
		this.receiverAccountNo = receiverAccountNo;
		this.receiverBalance = receiverBalance;
		this.mainStage = mainStage;
		
		
		receiverNameDisplay.setText(receiverHolderName);
        recieverAccountDisplay.setText(receiverAccountNo);
	}
	
	@FXML
    public void dialogOk(ActionEvent event) throws IOException {
		// Close the dialog stage
        Stage dialogStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        dialogStage.close();

        // After the dialog is closed, switch to the MoneyTransfer scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MoneyTransfer.fxml"));
        Parent moneyTransferRoot = loader.load();

        MoneyTransferClass moneyTransferController = loader.getController();
        moneyTransferController.setValue(senderHolderName, senderAccountNo, senderBalance); // Call setValue with the sender's details
        moneyTransferController.moneyTransferFunc(receiverHolderName, receiverAccountNo, receiverBalance);

        Scene scene = new Scene(moneyTransferRoot);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        mainStage.setScene(scene);
        mainStage.show();
    }
	
	@FXML
    private void dialogCancel(ActionEvent event) {
        // Close the stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
