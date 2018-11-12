package persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import business.entitie.Cycliste;
import business.entitie.Equipe;
import persistence.exception.DaoException;
import persistence.manager.JDBCManager;

public class CyclisteDao implements IDAO<Cycliste> {

	public final static String _INSERT = "INSERT INTO cycliste (`name`, `nombre_velos`, `equipe_id`) VALUES(?, ?, ?);";
	public final static String _SELECTID = "SELECT * FROM cycliste INNER JOIN equipe on cycliste.equipe_id = equipe.id WHERE cycliste.id=?;";
	public final static String _SELECT = "SELECT * FROM cycliste INNER JOIN equipe on cycliste.equipe_id = equipe.id;";
	public final static String _UPDATE = "UPDATE cycliste SET name=?, nombre_velos=?, equipe_id=? WHERE id=?;";
	public final static String _DELETE = "DELETE FROM cycliste WHERE id=?;";

	@Override
	public Cycliste create(Cycliste pT) throws DaoException {
		if(pT == null) {
			return null;
		}
		Connection connection = null;
		try {
			connection = JDBCManager.getInstance().openConection();
			PreparedStatement preparedStatement = connection.prepareStatement(_INSERT, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, pT.getName());
			preparedStatement.setInt(2, pT.getNombreVelos());
			preparedStatement.setLong(4, pT.getEquipe().getId());
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
	public Cycliste findById(long pId) throws DaoException {
		Connection connection = null;
		Cycliste cycliste = null;
		try {
			connection = JDBCManager.getInstance().openConection();
			PreparedStatement preparedStatement = connection.prepareStatement(_SELECTID);
			preparedStatement.setLong(1, pId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				int nombre_velos = resultSet.getInt("nombre_velos");
				long idEquipe = resultSet.getLong("equipe_id");
				EquipeDao equipeDao = new EquipeDao();
				Equipe equipe = equipeDao.findById(idEquipe);
				cycliste = new Cycliste(id,name,nombre_velos,equipe);
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
		return cycliste;
	}

	@Override
	public List<Cycliste> findList() throws DaoException {
		Connection connection = null;
		List<Cycliste> listCycliste = new ArrayList<>();
		try {
			connection = JDBCManager.getInstance().openConection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(_SELECT);
			while(resultSet.next()) {
				long id = resultSet.getLong("id");
				String name = resultSet.getString("name");
				int nombre_velos = resultSet.getInt("nombre_velos");
				long idEquipe = resultSet.getLong("equipe_id");
				EquipeDao equipeDao = new EquipeDao();
				Equipe equipe = equipeDao.findById(idEquipe);
				listCycliste.add(new Cycliste(id,name,nombre_velos,equipe));
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
		return listCycliste;
	}

	@Override
	public Cycliste updateById(Cycliste pT) throws DaoException {
		if(pT == null) {
			return null;
		}
		Connection connection = null;
		try {
			connection = JDBCManager.getInstance().openConection();
			PreparedStatement preparedStatement = connection.prepareStatement(_UPDATE);
			preparedStatement.setString(1, pT.getName());
			preparedStatement.setInt(2, pT.getNombreVelos());
			preparedStatement.setLong(3, pT.getEquipe().getId());
			preparedStatement.setLong(4, pT.getId());
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
