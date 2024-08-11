package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class logout {

	public void log(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Lbank.fxml"));
	    root = loader.load();

	    BankMenu bankMenu = loader.getController();
	    bankMenu.menu(rs);

	    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    scene = new Scene(root);
	    scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	    stage.setScene(scene);
	    stage.show();
	}
}
