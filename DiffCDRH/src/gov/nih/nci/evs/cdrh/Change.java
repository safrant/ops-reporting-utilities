package gov.nih.nci.evs.cdrh;

public class Change {
	private String changeType;
	private String code;
	private String sourcePT;
	private String subsetName;
	private String element;
	private String changeSummary;
	private String oldValue;
	private String newValue;
	private int noCCode;
	
	public Change(String type, String c, String spt, String subname, String el, String cs, String oldV, String newV) {
		changeType = type;
		code = c;
		noCCode = Integer.parseInt(c.replace("C", ""));		
		sourcePT = spt;
		subsetName = subname;
		element = el;
		changeSummary = cs;
		oldValue = oldV;
		newValue = newV;
	}
	
	public String getChangeType() {
		return changeType;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getSourcePT() {
		return sourcePT;
	}
	
	public String getSubsetName() {
		return subsetName;
	}
	
	public String getElement() {
		return element;
	}
	
	public String getChangeSummary() {
		return changeSummary;
	}
	
	public String getOldValue() {
		return oldValue;
	}
	
	public String getNewValue() {
		return newValue;
	}
	
	public int getNoCCode() {
		return noCCode;
	}
	
	public String toString() {
		return changeType + "\t" + code + "\t" + sourcePT + "\t" + subsetName + "\t" + element +
				"\t" + changeSummary + "\t" + oldValue + "\t" + newValue + "\n";
	}

}
