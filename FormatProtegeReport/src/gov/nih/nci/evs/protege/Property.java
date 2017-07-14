package gov.nih.nci.evs.protege;

import java.util.Vector;

public class Property {
	private String propertyId;
	private Vector<Qualifier> qualifiers;
	
	public Property(String id, Vector<Qualifier> qualifiers) {
		this.propertyId = id;
		this.qualifiers = qualifiers;
	}
	
	public String getPropertyId() {
		return this.propertyId;
	}
	
	public Vector<Qualifier> getQualifiers() {
		return this.qualifiers;
	}
	
	public String getQualifierValue(String qualName) {
		String value = null;
		if( qualifiers != null ) {
			for( Qualifier qual : qualifiers ) {
				if(qual.getQualifierName().equals(qualName)) {
					value = qual.getQualifierValue();
				}
			}
		}
		return value;
	}
	
	public void print() {
		System.out.println("Property: " + propertyId);
		if( qualifiers != null && qualifiers.size() > 0) {
			for( Qualifier qual : qualifiers ) {
				qual.print();
			}
		}
	}

}
