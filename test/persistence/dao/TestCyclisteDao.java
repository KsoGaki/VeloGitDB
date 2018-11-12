package persistence.dao;

import java.util.List;

import business.entitie.Cycliste;
import business.entitie.Equipe;
import exception.SqlInserterException;
import persistence.exception.DaoException;
import persistence.father.TestUnitDao;

public class TestCyclisteDao extends TestUnitDao {

	Cycliste cycliste = null;
	CyclisteDao cyclisteDao = null;
	int nbRow = 0;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		cyclisteDao = new CyclisteDao();
		nbRow = getInserter().select("SELECT COUNT(*) FROM cycliste").getDataAsInt();
	}

	public void testFindList() throws DaoException {
		List<Cycliste> listCycliste = cyclisteDao.findList();
		assertEquals(nbRow, listCycliste.size());
	}

	public void testFindById() throws DaoException, SqlInserterException {
		cycliste = cyclisteDao.findById(1);
		assertNotNull(cycliste);
		String marque = getInserter().select("SELECT name FROM cycliste WHERE id=" + cycliste.getId()).getDataAsString();
		assertEquals(marque, cycliste.getName());
		cycliste = cyclisteDao.findById(200);
		assertNull(cycliste);
	}

	public void testCreate() throws DaoException {
		Equipe equipe = new EquipeDao().findById(2);
		cycliste = new Cycliste(1, "Tobi", 15, equipe);
		cycliste = cyclisteDao.create(cycliste);
		assertEquals(nbRow + 1, cyclisteDao.findList().size());
		assertEquals(cycliste.getName(), cyclisteDao.findById(cycliste.getId()).getName());

		cycliste = null; 
		Cycliste cyclisteNull = cyclisteDao.create(cycliste);
		assertNull(cyclisteNull);
	}

	public void testUpdate() throws DaoException {
		cycliste = cyclisteDao.findById(1);
		cycliste.setName("Bip");
		cycliste = cyclisteDao.updateById(cycliste);
		assertEquals(cycliste.getName(), cyclisteDao.findById(1).getName());

		cycliste = null; 
		Cycliste cyclisteNull = cyclisteDao.updateById(cycliste);
		assertNull(cyclisteNull);
	}

	public void testDelete() throws DaoException {
		Equipe equipe = new EquipeDao().findById(2);
		cycliste = new Cycliste(1, "Tobi", 15, equipe);
		cycliste = cyclisteDao.create(cycliste);
		assertNotNull(cycliste);
		cyclisteDao.deleteById(cycliste.getId());
		cycliste = cyclisteDao.findById(cycliste.getId());
		assertNull(cycliste);
		
		try {
			cyclisteDao.deleteById(500);
			assertNotNull(null);
		} catch (DaoException e) {
			assertNotNull(e);
		}
	}

}
