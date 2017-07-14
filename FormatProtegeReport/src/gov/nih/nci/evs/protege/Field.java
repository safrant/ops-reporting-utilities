package gov.nih.nci.evs.protege;

import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Field {
	private Vector<Property> properties;
	private String propertyName;
	Gson gson = new Gson();
	
	
	public Field(String fieldString, String propName) {
		String field = fieldString;
		propertyName = propName;
		properties = new Vector<Property>();
		
		//multiple values
		if( fieldString.equals("")) {
			//do nothing
		}
		else {
			if( field.startsWith("\"") && field.endsWith("\"") ) {
				field = field.substring(1, field.length()-1);
			}
			
			//break this up by the pipe delimiter
			String[] values = field.split("\\|");
			for( int i=0; i < values.length; i++ ) {
				String value = values[i];
				if( value.contains(" {\"\"") ) {
					//qualifiers exist
					Vector<Qualifier> qualifiers = new Vector<Qualifier>();
					int index = value.indexOf(" {\"\"");
					String qualifierString = value.substring(index+1);
					String propertyString = value.substring(0, index);
					qualifierString = qualifierString.replace("\"\"", "\"");
					
					JsonParser parser = new JsonParser();
					JsonObject obj = parser.parse(qualifierString).getAsJsonObject();
					for( String memberKey : obj.keySet() ) {
//						System.out.println(memberKey);
//						System.out.println(obj.get(memberKey).getAsString());
						String qualName = memberKey;
						String qualVal = obj.get(memberKey).getAsString();
						Qualifier qual = new Qualifier(qualName, qualVal);
					//	qual.print();
						qualifiers.add(qual);
					}
//					for(int j=0; j < array.size(); j++) {
//						Qualifier qual = gson.fromJson(array.get(j), Qualifier.class);
//						qualifiers.add(qual);
//					}
					properties.add(new Property(propertyString, qualifiers));				
				}
				else {
					properties.add(new Property(value, null));
				}
			}
		}
	}
	
	public Vector<Property> getProperties() {
		return properties;
	}
	
	public String getPropertyName() {
		return propertyName;
	}
	
	public void print() {
		System.out.println("Field property: " + propertyName);
		for(Property prop : properties) {
			prop.print();
		}
	}

}
