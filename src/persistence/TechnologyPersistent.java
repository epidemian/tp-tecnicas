
package persistence;

import java.util.List;

import model.lab.NewProductionSequenceTechnologyTest;
import model.lab.technologies.NewProductionSequenceTechnology;


public class TechnologyPersistent {

	private NewProductionSequenceTechnology technology;
	private int id;
	private List<Integer> dependencies;
	
	public TechnologyPersistent(NewProductionSequenceTechnology technology,
			int id, List<Integer> dependencies) {
		super();
		this.technology = technology;
		this.id = id;
		this.dependencies = dependencies;
	}
	public NewProductionSequenceTechnology getTechnology() {
		return technology;
	}
	public void setTechnology(NewProductionSequenceTechnology technology) {
		this.technology = technology;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Integer> getDependencies() {
		return dependencies;
	}
	public void setDependencies(List<Integer> dependencies) {
		this.dependencies = dependencies;
	}
	
	public String toString(){
		return "ID:"+this.id+".Dependencies:"+this.dependencies;
	}
	
}
