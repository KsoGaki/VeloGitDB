package persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.entitie.Equipe;
import persistence.exception.DaoException;
import persistence.manager.JDBCManager;

public class EquipeDao implements IDAO<Equipe> {

	public final static String _INSERT = "INSERT INTO equipe (`name`, `budget`) VALUES(?, ?);";
	public final static String _SELECT = "SELECT * FROM equipe;";
	public final static String _SELECTID = "SELECT * FROM equipe WHERE id=?;";
	public final static String _UPDATE = "UPDATE equipe SET name=?, budget=? WHERE id=?;";
	public final static String _DELETE = "DELETE FROM equipe WHERE id=?;";

	@Override
	public Equipe create(Equipe pT) throws DaoException {
		if(pT == null) {
			return null;
		}
		Connection connection = null;
		try {
			connection = JDBCManager.getInstance().openConection();
			PreparedStatement preparedStatement = connection.prepareStatement(_INSERT, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, pT.getName());
			preparedStatement.setLong(2, pT.getBudget());
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getGeneratedKeys();
			while(resultSet.next()) {
				pT.setId(resultSet.getLong("GENERATED_KEY"));
			}
		}
		catch (Exception e) {
			throw new DaoException(e);
		} 
		finally {
			try {
				connection.close();
				JDBCManager.getInstance().closeConnection();
			} 
			catch (SQLException e) {
				throw new DaoException(e);
			}
		}
		return pT;
	}

	@Override
	public Equipe findById(long pId) throws DaoException {
		Connection connection = null;
		Equipe equipe = null;
		try {
			connection = JDBCManager.getInstance().openConection();
			PreparedStatement preparedStatement = connection.prepareStatement(_SELECTID);
			preparedStatement.setLong(1, pId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				long budget = resultSet.getLong("budget");
				equipe = new Equipe(id,name,budget);
			}
		}
		catch (Exception e) {
			throw new DaoException(e);
		} 
		finally {
			try {
				connection.close();
				JDBCManager.getInstance().closeConnection();
			} 
			catch (SQLException e) {
				throw new DaoException(e);
			}
		}
		return equipe;
	}

	@Override
	public List<Equipe> findList() throws DaoException {
		Connection connection = null;
		List<Equipe> listEquipe = new ArrayList<>();
		try {
			connection = JDBCManager.getInstance().openConection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(_SELECT);
			while(resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				long budget = resultSet.getLong("budget");
				listEquipe.add(new Equipe(id,name,budget));
			}
		}
		catch (Exception e) {
			throw new DaoException(e);
		} 
		finally {
			try {
				connection.close();
				JDBCManager.getInstance().closeConnection();
			} 
			catch (SQLException e) {
				throw new DaoException(e);
			}
		}
		if(listEquipe.size() == 0) {
			return null;
		}
		return listEquipe;
	}

	@Override
	public Equipe updateById(Equipe pT) throws DaoException {
		if(pT == null) {
			return null;
		}
		Connection connection = null;
		try {
			connection = JDBCManager.getInstance().openConection();
			PreparedStatement preparedStatement = connection.prepareStatement(_UPDATE);
			preparedStatement.setString(1, pT.getName());
			preparedStatement.setLong(2, pT.getBudget());
			preparedStatement.setLong(3, pT.getId());
			preparedStatement.execute();
		}
		catch (Exception e) {
			throw new DaoException(e);
		} 
		finally {
			try {
				connection.close();
				JDBCManager.getInstance().closeConnection();
			} 
			catch (SQLException e) {
				throw new DaoException(e);
			}
		}
		return pT;
	}

	@Override
	public void deleteById(long pId) throws DaoException {
		Connection connection = null;
		try {
			connection = JDBCManager.getInstance().openConection();
			PreparedStatement preparedStatement = connection.prepareStatement(_DELETE);
			preparedStatement.setLong(1, pId);
			preparedStatement.execute();
			
			if(preparedStatement.getUpdateCount() == 0) {
				throw new DaoException("NO QUERY AFFECTED");
			}
		}
		catch (Exception e) {
			throw new DaoException(e);
		} 
		finally {
			try {
				connection.close();
				JDBCManager.getInstance().closeConnection();
			} 
			catch (SQLException e) {
				throw new DaoException(e);
			}
		}
	}
}
