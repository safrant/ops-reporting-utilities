package gov.nih.nci.evs.cdrh;

public class Subset {
	private String code;
	private String name;
	private String source;
	
	public Subset(String c, String n, String src) {
		code = c;
		name = n;
		source = src;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getSource() {
		return this.source;
	}

}
