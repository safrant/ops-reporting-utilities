package gov.nih.nci.evs.protege;

public class Qualifier {
	private String name;
	private String value;
	
	public Qualifier(String n, String v) {
		this.name = n;
		this.value = v;
	}
	
	public String getQualifierName() {
		return this.name;
	}
	
	public String getQualifierValue() {
		return this.value;
	}
	
	public void print() {
		System.out.println("Qualifier name: " + name);
		System.out.println("Qualifier value: " + value);
	}

}
