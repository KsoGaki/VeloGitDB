package persistence.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCManager {

    private static JDBCManager INSTANCE = null;
    private static Connection connection;
    
    private JDBCManager(){
    	
    }
    
    public static JDBCManager getInstance(){  
    	if(INSTANCE == null) {
    		INSTANCE = new JDBCManager();
    	}
    	return INSTANCE;
    }	    
    
    public Connection openConection() throws Exception {
    	Properties properties =	CfgManager.getInstance().getCfg();
		Class.forName(properties.getProperty("driverName"));		
		String url = properties.getProperty("url");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		connection =  DriverManager.getConnection(url, user, password);
		return connection;
	}
	
    public void closeConnection() throws SQLException {
			connection.close();
	}
}
