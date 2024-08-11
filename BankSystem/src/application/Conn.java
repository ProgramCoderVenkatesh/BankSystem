package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.DecimalFormat;
import java.time.Year;
import java.util.ArrayList;

public class Conn {
	
	Connection conn;

	public void dbConnection() {
		
		try {
			String url = "jdbc:mysql://localhost:3306/bankSystem";
			String userName = "root";
			String password = "VK45@mysql";
			
//			Class.forName("oracle.jdbc.driver.OracleDriver"); // Not necessary
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Connected Successfully");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return conn;
	}
	
	public ResultSet readAllDetails(String holderName, String accountNo) {
		
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM users WHERE holderName = ? AND accountNo = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, holderName);
	        pstmt.setString(2, accountNo);
	        
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public ResultSet readReceiverDetails(String accountNo) {
		
		ResultSet rs = null;
		System.out.println("Resultset: " + rs);
		try {
			String query = "SELECT holderName, accountNo, balance FROM users WHERE accountNo = ?";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, accountNo);
	        
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public int updateNumber(String holderName, String accountNo, String phone) {
		
		int rowsAffected = 0;
		PreparedStatement pstmt = null;
		
		try {
			String query = "UPDATE users SET phone = ? WHERE holderName = ? AND accountNo = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, phone);
			pstmt.setString(2, holderName);
			pstmt.setString(3, accountNo);
	        
			rowsAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to update phone number..");
			
			String alertMsg = "Phone number updated successfully";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		} finally {
	        // Ensure PreparedStatement is closed
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		return rowsAffected;
	}
	
	public int updateEmail(String holderName, String accountNo, String email) {
		
		int rowsAffected = 0;
		PreparedStatement pstmt = null;
		
		try {
			String query = "UPDATE users SET email = ? WHERE holderName = ? AND accountNo = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			pstmt.setString(2, holderName);
			pstmt.setString(3, accountNo);
	        
			rowsAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to update email..");
			
			String alertMsg = "Failed to update email..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		} finally {
	        // Ensure PreparedStatement is closed
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		return rowsAffected;
	}

	public int updatePin(String holderName, String accountNo, String phone, String email, String newEnteredPin) {

		int rowsAffected = 0;
		PreparedStatement pstmt = null;
		
		System.out.println(holderName);
		System.out.println(accountNo);
		System.out.println(phone);
		System.out.println(email);
		System.out.println(newEnteredPin);
		
		try {
			String query = "UPDATE users SET pin = ? WHERE holderName = ? AND accountNo = ? AND phone = ? AND email = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newEnteredPin);
			pstmt.setString(2, holderName);
			pstmt.setString(3, accountNo);
			pstmt.setString(4, phone);
			pstmt.setString(5, email);
	        
			rowsAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to update security pin..");
			
			String alertMsg = "Failed to update security pin..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		} finally {
	        // Ensure PreparedStatement is closed
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		return rowsAffected;
	}

	public int updateBalance(String holderName, String accountNo, String newBalance) {
		
		int rowsAffected = 0;
		PreparedStatement pstmt = null;
		DecimalFormat df = new DecimalFormat("#0.00");
		
		try {
			String query = "UPDATE users SET balance = ? WHERE holderName = ? AND accountNo = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newBalance);
			pstmt.setString(2, holderName);
			pstmt.setString(3, accountNo);
	        
			rowsAffected = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to update new balance..");
			
			String alertMsg = "Failed to update new balance..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		} finally {
	        // Ensure PreparedStatement is closed
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		return rowsAffected;
	}

	public int insertDataAtUsers(String fullName, int generatedAccountNo, String phone, String email, String balance, String pin) {
		
		int rowsAffected = 0;
		PreparedStatement pstmt = null;
		
		try {
			String query = "INSERT INTO users (holderName, accountNo, phone, email, balance, pin) VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fullName);
			pstmt.setString(2, String.valueOf(generatedAccountNo));
			pstmt.setString(3, phone);
			pstmt.setString(4, email);
			pstmt.setString(5, balance);
			pstmt.setString(6, pin);
	        
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLIntegrityConstraintViolationException e) {
			
			return rowsAffected = -1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to insert users data..");
			
			String alertMsg = "Failed to insert users data..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		} finally {
	        // Ensure PreparedStatement is closed
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		return rowsAffected;
	}

	public int insertTransaction(String senderHolderName, String senderAccountNo, String receiverHolderName, String receiverAccountNo, String transferAmount, String senderBalance, String receiverBalance) {
		
		int rowsAffected = 0;
		PreparedStatement pstmt = null;
		
		int currentYear = Year.now().getValue();
		
		try {
			String query = "INSERT INTO transaction (FromAccount, FromHolder, ToAccount, ToHolder, Amount, SenderBalance, ReceiverBalance, TransactionDate, TransactionTime, Year) VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE(), CURTIME(), ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, senderAccountNo);
			pstmt.setString(2, senderHolderName);
			pstmt.setString(3, receiverAccountNo);
			pstmt.setString(4, receiverHolderName);
			pstmt.setString(5, transferAmount);
			pstmt.setString(6, senderBalance);
			pstmt.setString(7, receiverBalance);
			pstmt.setString(8, String.valueOf(currentYear));
	        
			rowsAffected = pstmt.executeUpdate();
		} catch(SQLIntegrityConstraintViolationException e) {
			
			rowsAffected = -1;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to insert users data..");
			
			String alertMsg = "Failed to insert users data..";
            AlertDialogBoxClass alertDialog = new AlertDialogBoxClass();
            alertDialog.alertDialog(alertMsg);
		} finally {
	        // Ensure PreparedStatement is closed
	        if (pstmt != null) {
	            try {
	                pstmt.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		
		return rowsAffected;
	}
	
	public ResultSet readTransactions(String holderName, String accountNo, String query) {
		
		ResultSet rs = null;
		try {
    		String passedQuery = query;
    		PreparedStatement pstmt = conn.prepareStatement(passedQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		pstmt.setString(1, accountNo);
    		pstmt.setString(2, holderName);
    		pstmt.setString(3, accountNo);
    		pstmt.setString(4, holderName);

    		rs = pstmt.executeQuery();
    		
    		// Move cursor to the last row
            rs.last();
            // Get the row number which represents the number of rows
            int rowCount = rs.getRow();
            // Move cursor back to before the first row
            rs.beforeFirst();

            System.out.println("Number of rows: " + rowCount);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ResultSet readTransactionsByMonth(String holderName, String accountNo, String query, ArrayList<String> array) {
		
		ResultSet rs = null;
		try {
    		String passedQuery = query;
    		PreparedStatement pstmt = conn.prepareStatement(passedQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    		pstmt.setString(1, accountNo);
    		pstmt.setString(2, holderName);
    		pstmt.setString(3, accountNo);
    		pstmt.setString(4, holderName);
    		
    		// Set the month parameters
	        for (int i = 0; i < array.size(); i++) {
	            pstmt.setString(5 + i, array.get(i)); // 5 is the starting index for months
	        }

    		rs = pstmt.executeQuery();
    		
    		// Move cursor to the last row
            rs.last();
            // Get the row number which represents the number of rows
            int rowCount = rs.getRow();
            // Move cursor back to before the first row
            rs.beforeFirst();

            System.out.println("Number of rows: " + rowCount);

//    		if (rs == null) {
//    		    System.out.println("ResultSet is null or query failed.");
//    		} else {
//    		    // Process the ResultSet
//    		    while (rs.next()) {
//    		        System.out.println("Hello");
//    		        System.out.println(rs.getString("FromHolder"));
//    		        System.out.println(rs.getString("amount"));
//    		        System.out.println(rs.getDate("TransactionDate"));
//    		        System.out.println(rs.getTime("TransactionTime"));
//    		    }
//    		}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}
}
