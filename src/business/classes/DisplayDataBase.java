package business.classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DisplayDataBase {

	private Connection connection;


	private List<List<String>> listValues = new ArrayList<List<String>>();
	private List<String> listTile = new ArrayList<String>();
	private String stringStatement = "";


	public void display() {
		displayValue(listValues);
		System.out.println();
	}

	public static List<String> getValues(ResultSet resultSet, int stop) throws SQLException {
		List<String> listValue = new ArrayList<String>();
		for (int i = 1; i <= stop ; i++) {
			listValue.add(resultSet.getString(i));
		}
		return listValue;
	}

	public static List<String> getMaxLong(List<List<String>> list, int stop) {
		List<String> listMaxLong = new ArrayList<String>();
		Integer max;
		for (int i = 0; i < stop; i++) {
			max = 0;
			for (List<String> listString : list) {
				if(max < listString.get(i).length()) {
					max = listString.get(i).length();
				}
			}
			listMaxLong.add(max.toString());
		}
		return listMaxLong;
	}

	public static void displayValue(List<List<String>> list) {
		List<String> listMaxLong = list.get(list.size() - 1);
		for (int i = 0; i < listMaxLong.size(); i++) {
			drawLine(listMaxLong, i);
		}
		System.out.println();
		for (int i = 0; i < list.get(0).size(); i++) {
			drawName(listMaxLong, list.get(0), i);
		}
		System.out.println();
		for (int i = 0; i < listMaxLong.size(); i++) {
			drawLine(listMaxLong, i);
		}
		System.out.println();
		for (int y = 1; y < list.size() - 1; y++) {
			for (int i = 0; i < list.get(y).size(); i++) {
				drawName(listMaxLong, list.get(y), i);
			}
			System.out.println();
		}
		for (int i = 0; i < listMaxLong.size(); i++) {
			drawLine(listMaxLong, i);
		}

	}

	public static void drawLine(List<String> listMaxLong, int x) {
		if(x == 0) {
			System.out.print("+");
		}
		for(int i=0; i <  Integer.parseInt(listMaxLong.get(x)) + 2 ;i++) {
			System.out.print("-");
		}
		System.out.print("+");
	}

	public static void drawName(List<String> listMaxLong, List<String> listName, int x) {
		if(x == 0) {
			System.out.print("|");
		}
		for(int i=0; i < (Integer.parseInt((listMaxLong.get(x))) + 2 - listName.get(x).length()) ;i++) {
			if(i == 0) {
				System.out.print(" " + listName.get(x));
			}
			else {
				System.out.print(" ");
			}
		}
		System.out.print("|");
	}
	
	public void initPreparedStatement(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			listTile.add(resultSetMetaData.getColumnName(i));
			if(i == resultSetMetaData.getColumnCount()) {
				listValues.add(listTile);
			}
		}
		while(resultSet.next()) {
			listValues.add(getValues(resultSet, resultSetMetaData.getColumnCount()));
		}
		listValues.add(getMaxLong(listValues, resultSetMetaData.getColumnCount()));
	}

	public void init() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(stringStatement);
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
			listTile.add(resultSetMetaData.getColumnName(i));
			if(i == resultSetMetaData.getColumnCount()) {
				listValues.add(listTile);
			}
		}
		while(resultSet.next()) {
			listValues.add(getValues(resultSet, resultSetMetaData.getColumnCount()));
		}
		listValues.add(getMaxLong(listValues, resultSetMetaData.getColumnCount()));
	}

	public void setStringStatement(String stringStatement) throws ClassNotFoundException, SQLException {
		this.stringStatement = stringStatement;
		init();
	}

	public DisplayDataBase(String connectionPath, String user, String password) throws SQLException {
		this.connection = DriverManager.getConnection(connectionPath, user, password);
	}

	public DisplayDataBase(ResultSet resultSet) throws ClassNotFoundException, SQLException {
		initPreparedStatement(resultSet);
	}
}
