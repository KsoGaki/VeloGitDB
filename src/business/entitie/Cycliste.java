package business.entitie;

public class Cycliste {
	
	private long id;
	private String name;
	private int nombreVelos;
	private String velo;
	private Equipe equipe;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNombreVelos() {
		return nombreVelos;
	}
	public void setNombreVelos(int nombreVelos) {
		this.nombreVelos = nombreVelos;
	}
	public Equipe getEquipe() {
		return equipe;
	}
	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}
	public String getVelo() {
		return velo;
	}
	public void setVelo(String velo) {
		this.velo = velo;
	}
	
	public Cycliste(long id, String name, int nombreVelos, Equipe equipe) {
		super();
		this.id = id;
		this.name = name;
		this.nombreVelos = nombreVelos;
		this.equipe = equipe;
	}
	
	public Cycliste(long id, String name, int nombreVelos, Equipe equipe, String velo) {
		super();
		this.id = id;
		this.name = name;
		this.nombreVelos = nombreVelos;
		this.equipe = equipe;
		this.velo = velo;
	}
	
	public Cycliste() {}
	
}
