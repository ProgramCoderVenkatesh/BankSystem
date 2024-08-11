package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AlertDialogBoxClass {
	
	@FXML
	Label alertMsgLabel;
	
	public void alertDialog(String alertMsg) {
		
		try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlertDialogBox.fxml"));
            Parent root = fxmlLoader.load();
            
            // Get the controller instance from the FXMLLoader
            AlertDialogBoxClass controller = fxmlLoader.getController();
            controller.setAlertMsg(alertMsg);  // Call the method to set the alert message
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Alert Box");
            dialogStage.setScene(new Scene(root));
            
            dialogStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// Method to set the alert message
    public void setAlertMsg(String alertMsg) {
        if (alertMsgLabel != null) {
            alertMsgLabel.setText(alertMsg);
            
//          Create a Timeline to close the stage after 2 seconds
            Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(1500), // 1.5 seconds
                ae -> {
                    // Get the current stage and close it
                    Stage stage = (Stage) alertMsgLabel.getScene().getWindow();
                    stage.close();
                }
            ));
            timeline.setCycleCount(1); // Run only once
            timeline.play(); // Start the timeline
        } else {
            System.out.println("alertMsgLabel is null");
        }
    }
    
    public void alertDialogForAccountNo(String alertMsg) {
    	
    	try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AlertDialogBox.fxml"));
            Parent root = fxmlLoader.load();
            
            // Get the controller instance from the FXMLLoader
            AlertDialogBoxClass controller = fxmlLoader.getController();
            controller.setAlertMsgForAccountNo(alertMsg);  // Call the method to set the alert message
            
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Alert Box");
            dialogStage.setScene(new Scene(root));
            
            dialogStage.show();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private void setAlertMsgForAccountNo(String alertMsg) {
		if (alertMsgLabel != null) {
            alertMsgLabel.setText(alertMsg);            
        } else {
            System.out.println("alertMsgLabel is null");
        }
	}
}
