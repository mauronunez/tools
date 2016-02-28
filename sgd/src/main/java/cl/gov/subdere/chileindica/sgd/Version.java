package cl.gov.subdere.chileindica.sgd;

public class Version {
	
	private int id;
	private Version parent;
	
	public Version(Version parent){
		this.parent=parent;
	}
	
	public Version getParent(){
		return parent;
	}
	
	public int getId(){
		return id;
	}

}
