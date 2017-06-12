package gov.nih.nci.evs.cdrh;

public class Concept {
	private String conceptCode;
	private String sourceCode;
	private String[] synonyms;
	private String nciDefinition;
	private String sourceDefinition;
	private String sourcePT;
	private String parentNCIConceptCode;
	private String parentSourceCode;
	private String parentSourcePT;
	
	public Concept(String cc, String scode, String spt, String syns, String sdef, String ncidef, String pcc, String psc, String ppt) {
		conceptCode = cc;
		sourceCode = scode;
		sourcePT = spt;
		synonyms = syns.split("\\|");
		for(int i=0; i < synonyms.length; i++) {
			synonyms[i] = synonyms[i].trim();
		}
		sourceDefinition = sdef;
		nciDefinition = ncidef;
		parentNCIConceptCode = pcc;
		parentSourceCode = psc;
		parentSourcePT = ppt;
	}
	
	public String getConceptCode() {
		return this.conceptCode;
	}
	
	public String getSourceCode() {
		return this.sourceCode;
	}
	
	public String[] getSynonyms() {
		return this.synonyms;
	}
	
	public String getNCIDefinition() {
		return this.nciDefinition;
	}
	
	public String getSourceDefinition() {
		return this.sourceDefinition;
	}
	
	public String getSourcePT() {
		return this.sourcePT;
	}
	
	public String getParentNCIConceptCode() {
		return this.parentNCIConceptCode;
	}
	
	public String getParentSourceCode() {
		return this.parentSourceCode;
	}
	
	public String getParentSourcePT() {
		return this.parentSourcePT;
	}
}
