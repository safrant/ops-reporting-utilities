package gov.nih.nci.evs.protege;

import java.util.Vector;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class Field {
	private Vector<Property> properties;
	Gson gson = new Gson();
	
	
	public Field(String fieldString) {
		String field = fieldString;
		
		//multiple values
		if( field.startsWith("\"") && field.endsWith("\"") ) {
			field = field.substring(1, field.length()-1);
		}
		
		//break this up by the pipe delimiter
		String[] values = field.split("|");
		for( int i=0; i < values.length; i++ ) {
			String value = values[i];
			if( value.contains(" {\"\"") ) {
				//qualifiers exist
				Vector<Qualifier> qualifiers = new Vector<Qualifier>();
				int index = value.indexOf(" {\"\"");
				String qualifierString = value.substring(index+1);
				String propertyString = value.substring(0, index-1);
				qualifierString = qualifierString.replace("\"\"", "\"");
				
				JsonParser parser = new JsonParser();
				JsonArray array = parser.parse(qualifierString).getAsJsonArray();
				for(int j=0; j < array.size(); j++) {
					Qualifier qual = gson.fromJson(array.get(j), Qualifier.class);
					qualifiers.add(qual);
				}
				properties.add(new Property(propertyString, qualifiers));				
			}
			else {
				properties.add(new Property(value, null));
			}
		}
	}
	
	public void print() {
		for(Property prop : properties) {
			prop.print();
		}
	}

}
