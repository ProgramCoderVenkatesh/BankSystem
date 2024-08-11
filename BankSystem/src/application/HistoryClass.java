package application;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.Node;

public class HistoryClass {
	
	private String holderName;
	private String accountNo;
	
	@FXML
    private AnchorPane rootPane; // Inject the parent container
	
	@FXML
	private Label historyHolderNameLabel;
	
	@FXML
	private Label historyAccountNoLabel;

	@FXML
    private ComboBox<String> startMonthComboBox;
	@FXML
	private ComboBox<String> endMonthComboBox;
	
	@FXML
	GridPane gridPane;
	@FXML
	Pane headerPane;
	
	String startMonth;
	String endMonth;
	
	ArrayList<String> monthName = new ArrayList<>(Arrays.asList("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"));
	
	@FXML
    public void initialize() {
				
        // Add values to the ComboBox
        startMonthComboBox.getItems().addAll(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        );
        
        // Add values to the ComboBox
        endMonthComboBox.getItems().addAll(
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        );
        
        // Add listener to ComboBox to update label when selection changes
        startMonthComboBox.setOnAction(event -> {
        	startMonth = startMonthComboBox.getValue();
        });
        
        // Add listener to ComboBox to update label when selection changes
        endMonthComboBox.setOnAction(event -> {
        	endMonth = endMonthComboBox.getValue();
        });
    }
	
	public void setValue(String holderName, String accountNo) {
		
		this.holderName = holderName;
		this.accountNo = accountNo;
		
		historyHolderNameLabel.setText(holderName);
		historyAccountNoLabel.setText(accountNo);
		historyFunc();
	}
	
	public void historyFunc() {
	    
	    System.out.println(startMonth);
	    System.out.println(endMonth);
	    
	    if(monthName.indexOf(endMonth) < monthName.indexOf(startMonth)  && endMonth != null) {
	    	
	    	System.out.println("Months are selected wrongly..");
	    	return;
	    }
	    
	    Conn conn = new Conn();
    	conn.dbConnection();
    	ResultSet rs = null;
	    
	    if(startMonth == null && endMonth == null) {
	    	
	    	String query = "SELECT FromAccount, FromHolder, ToAccount, ToHolder, Amount, SenderBalance, ReceiverBalance, TransactionDate, TransactionTime FROM transaction WHERE FromAccount = ? OR FromHolder = ? OR ToAccount = ? OR ToHolder = ?";
	    	rs = conn.readTransactions(holderName, accountNo, query);
	    }
	    
	    if(startMonth != null && endMonth == null) {
	    	
	    	// Clear previous content from rootPane and contentPane
	        clearContent();
	    	
	    	// Extract the sublist from "February" to the end
	        ArrayList<String> subList = new ArrayList<>(monthName.subList(monthName.indexOf(startMonth), monthName.size()));
	        System.out.println(subList);
	        
	        StringBuilder placeholders = toBuildPlaceholders(subList);
	        
	        String query = "SELECT FromAccount, FromHolder, ToAccount, ToHolder, Amount, SenderBalance, ReceiverBalance, TransactionDate, TransactionTime FROM transaction WHERE (FromAccount = ? OR FromHolder = ? OR ToAccount = ? OR ToHolder = ?) AND MONTH IN (" + placeholders.toString() + ")";
	        
	        rs = conn.readTransactionsByMonth(holderName, accountNo, query, subList);
	    }
	    
	    if(startMonth == null && endMonth != null) {
	    	
	    	// Clear previous content from rootPane and contentPane
	        clearContent();
	    	
	    	// Extract the sublist from "February" to the end
	        ArrayList<String> subList = new ArrayList<>(monthName.subList(0, monthName.indexOf(endMonth) + 1));
	        System.out.println(subList);
	        
	        StringBuilder placeholders = toBuildPlaceholders(subList);
	        
	        String query = "SELECT FromAccount, FromHolder, ToAccount, ToHolder, Amount, SenderBalance, ReceiverBalance, TransactionDate, TransactionTime FROM transaction WHERE (FromAccount = ? OR FromHolder = ? OR ToAccount = ? OR ToHolder = ?) AND MONTH IN (" + placeholders.toString() + ")";
	        
	        rs = conn.readTransactionsByMonth(holderName, accountNo, query, subList);
	    }

	    if(startMonth != null && endMonth != null) {
	    	
	    	// Clear previous content from rootPane and contentPane
	        clearContent();
	
	    	// Extract the sublist from "February" to the end
	        ArrayList<String> subList = new ArrayList<>(monthName.subList(monthName.indexOf(startMonth), monthName.indexOf(endMonth) + 1));
	        System.out.println(subList);
	        
	        StringBuilder placeholders = toBuildPlaceholders(subList);
	        
	        String query = "SELECT FromAccount, FromHolder, ToAccount, ToHolder, Amount, SenderBalance, ReceiverBalance, TransactionDate, TransactionTime FROM transaction WHERE (FromAccount = ? OR FromHolder = ? OR ToAccount = ? OR ToHolder = ?) AND MONTH IN (" + placeholders.toString() + ")";
	        
	        rs = conn.readTransactionsByMonth(holderName, accountNo, query, subList);
	    }
	    
	    // Create a new Pane for the scrollable content
	    Pane contentPane = new Pane();
	    contentPane.getChildren().clear();
	    contentPane.setPrefSize(569.0, 280.0); // Set preferred size for the content pane
	    contentPane.setLayoutX(15);
	    contentPane.setLayoutY(117);
	    rootPane.getChildren().add(contentPane);
	    
	    
	    System.out.println("Rs : " + rs);
	    int rowCount = -1;
	    
	    // Move cursor to the last row
	    try {
			rs.last();
		
	    
	    // Get the total row count
		rowCount = rs.getRow();
	    
	    // Move cursor back to before the first row
	    rs.beforeFirst();
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    System.out.println("Rs rowCount : " + rowCount);
		if(rowCount == 0) {
				
			System.out.println("Rs is null");
			Label label = new Label("No Transactions available.");
			label.setStyle("-fx-font-size: 20px;");
		    label.setLayoutX(190);
		    label.setLayoutY(125);
			contentPane.getChildren().add(label);
			return;
		}
	    
	    
	    
	    if(rowCount > 4) {
	    	
		    // Create a new ScrollPane
		    ScrollPane scrollPane = new ScrollPane(contentPane);
		    scrollPane.setPrefSize(581.0, 281.0); // Set preferred size for ScrollPane
		    scrollPane.setLayoutX(15.0);
		    scrollPane.setLayoutY(117.0);
		    scrollPane.setFitToWidth(true); // Make the ScrollPane fit to the width of the content
		    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scrollbar as needed
		    
		    // Add the ScrollPane to the rootPane
		    rootPane.getChildren().add(scrollPane);
	    }
	    
	    int startPositionOfPane = 0;
	    
	    try {
			while(rs.next()) {
				Pane pane = new Pane();
		        pane.setPrefSize(569, 70); // Set preferred size
		        pane.setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;");
		        pane.setLayoutX(0);
		        pane.setLayoutY(startPositionOfPane); // Position panes vertically
		        contentPane.getChildren().add(pane);
		        
		        String dateTime = String.valueOf(rs.getDate("TransactionDate")) + "\n" + String.valueOf(rs.getTime("TransactionTime"));
		        Label labelForDateTime = new Label(dateTime);
		        labelForDateTime.setLayoutY(15);
		        labelForDateTime.setFont(new Font(13));
		        pane.getChildren().add(labelForDateTime);
		        
		        Label labelForParticulars = new Label();
		        labelForParticulars.setFont(new Font(13));
		        labelForParticulars.setLayoutX(90);
		        labelForParticulars.setLayoutY(20);
		        pane.getChildren().add(labelForParticulars);
		        
		        Label labelForBalance = new Label();
		        labelForBalance.setLayoutX(505);
		        labelForBalance.setLayoutY(20);
		        labelForBalance.setFont(new Font(13));
		        pane.getChildren().add(labelForBalance);
		        
		        if(rs.getString("FromAccount").equals(accountNo) && rs.getString("FromHolder").equals(holderName)) {
		        	
		        	Label labelForWithdraw = new Label(String.valueOf(Integer.parseInt(rs.getString("Amount"))));
		        	labelForWithdraw.setLayoutX(310);
		        	labelForWithdraw.setLayoutY(20);
		        	labelForWithdraw.setFont(new Font(13));
		        	pane.getChildren().add(labelForWithdraw);
		        	
		        	labelForParticulars.setText(rs.getString("ToHolder"));
		        	labelForBalance.setText(rs.getString("SenderBalance"));
		        } else {
		        	
		        	Label labelForDeposit = new Label(String.valueOf(Integer.parseInt(rs.getString("Amount"))));
		        	labelForDeposit.setLayoutX(415);
		        	labelForDeposit.setLayoutY(20);
		        	labelForDeposit.setFont(new Font(13));
		        	pane.getChildren().add(labelForDeposit);
		        	
		        	labelForParticulars.setText(rs.getString("FromHolder"));
		        	labelForBalance.setText(rs.getString("ReceiverBalance"));
		        }
		        
		        startPositionOfPane += 70;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    contentPane.setPrefHeight(rowCount * 70); // Total height for all panes
	}
	
	private StringBuilder toBuildPlaceholders(ArrayList<String> subList) {
		
		// Create a query string with a placeholder for the month values
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < subList.size(); i++) {
            placeholders.append("?");
            if (i < subList.size() - 1) {
                placeholders.append(", ");
            }
        }
        
        System.out.println(placeholders);
        return placeholders;
	}
	
	private void clearContent() {
	    // Remove existing ScrollPane and contentPan from rootPane
	    List<Node> nodesToRemove = new ArrayList<>();
	    for (Node node : rootPane.getChildren()) {
	        if (node instanceof ScrollPane || node instanceof Pane) {
	            // Exclude headerPane from removal
	            if (!"headerPane".equals(node.getId())) {
	                nodesToRemove.add(node);
	            } else if (node instanceof ScrollPane) {
	                ScrollPane scrollPane = (ScrollPane) node;
	                if (scrollPane.getContent() instanceof Pane) {
	                    ((Pane) scrollPane.getContent()).getChildren().clear();
	                }
	            }
	        }
	    }
	    
	    rootPane.getChildren().removeAll(nodesToRemove);
	}
}
