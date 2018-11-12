package business.entitie;

public class Equipe {
	
	private long id;
	private String name;
	private long budget;
	
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
	public long getBudget() {
		return budget;
	}
	public void setBudget(long budget) {
		this.budget = budget;
	}
	
	public Equipe(long id, String name, long budget) {
		super();
		this.id = id;
		this.name = name;
		this.budget = budget;
	}
	
	public Equipe() {}

}
