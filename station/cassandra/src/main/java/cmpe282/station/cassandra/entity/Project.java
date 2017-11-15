package cmpe282.station.cassandra.entity;

public class Project {
	
	private long id; 
	
	private String name; 
	
	private float budget;
	
	public Project(){}
	
	public Project(Project that){
		this.id = that.getId();
		this.name = that.getName();
		this.budget = that.getBudget();
	}
	
	public Project(long id, String name, float budget){
		this.id = id;
		this.name = name;
		this.budget = budget;
	}
	
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
	public float getBudget() {
		return budget;
	}
	public void setBudget(float budget) {
		this.budget = budget;
	}
}
