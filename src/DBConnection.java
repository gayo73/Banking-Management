import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.management.RuntimeErrorException;

public class DBConnection {
	private static final String url = "jdbc:oracle:thin:@192.168.0.141:1521:xe";
	private static final String user = "hr";
	private static final String password = "hr";
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(url, user, password);
	}
}
