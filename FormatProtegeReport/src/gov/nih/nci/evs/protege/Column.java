package gov.nih.nci.evs.protege;

import java.util.Vector;

public class Column {
	private int number;
	private Property property = null;
	
	//might not need a HashMap and just feed in the JSON
	public Column(int colNum, String propName, Vector<Qualifier> qualifiers) {
		this.number = colNum;
		this.property = new Property(propName, qualifiers);
	}
	
	public Column() {
		
	}
	
	public void setColNumber(int num) {
		this.number = num;
	}
	
	public void setProperty(String propName) {
		this.property = new Property(propName, null);
	}	
	
	public void setProperty(String propName, Vector<Qualifier> qualifiers) {
		this.property = new Property(propName, qualifiers);
	}
	 
	public int getColNumber() {
		return this.number;
	}
	
	public Property getProperty() {
		return this.property;
	}
	
	public void print() {
		System.out.println("Column Number: " + number);
		property.print();
	}
	
}
