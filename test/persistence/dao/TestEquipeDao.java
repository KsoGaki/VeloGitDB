package persistence.dao;

import java.util.List;

import business.entitie.Equipe;
import exception.SqlInserterException;
import persistence.exception.DaoException;
import persistence.father.TestUnitDao;

public class TestEquipeDao extends TestUnitDao {

	Equipe equipe = null;
	EquipeDao equipeDao = null;
	int nbRow = 0;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		equipeDao = new EquipeDao();
		nbRow = getInserter().select("SELECT COUNT(*) FROM equipe").getDataAsInt();
	}

	public void testFindList() throws DaoException {
		List<Equipe> listEquipe = equipeDao.findList();
		assertEquals(nbRow, listEquipe.size());
	}

	public void testFindById() throws DaoException, SqlInserterException {
		equipe = equipeDao.findById(1);
		assertNotNull(equipe);
		String marque = getInserter().select("SELECT name FROM equipe WHERE id=" + equipe.getId()).getDataAsString();
		assertEquals(marque, equipe.getName());
		equipe = equipeDao.findById(200);
		assertNull(equipe);
	}

	public void testCreate() throws DaoException {
		equipe = new Equipe(1, "Tobi", 1500000);
		equipe = equipeDao.create(equipe);
		assertEquals(nbRow + 1, equipeDao.findList().size());
		assertEquals(equipe.getName(), equipeDao.findById(equipe.getId()).getName());

		equipe = null; 
		Equipe equipeNull = equipeDao.create(equipe);
		assertNull(equipeNull);
	}

	public void testUpdate() throws DaoException {
		equipe = equipeDao.findById(1);
		equipe.setName("Bip");
		equipe = equipeDao.updateById(equipe);
		assertEquals(equipe.getName(), equipeDao.findById(1).getName());

		equipe = null; 
		Equipe equipeNull = equipeDao.updateById(equipe);
		assertNull(equipeNull);
	}

	public void testDelete() throws DaoException {
		equipe = new Equipe(1,"Bip", 150000);
		equipe = equipeDao.create(equipe);
		assertNotNull(equipe);
		equipeDao.deleteById(equipe.getId());
		equipe = equipeDao.findById(equipe.getId());
		assertNull(equipe);
		
		try {
			equipeDao.deleteById(500);
			assertNotNull(null);
		} catch (DaoException e) {
			assertNotNull(e);
		}
	}

}
