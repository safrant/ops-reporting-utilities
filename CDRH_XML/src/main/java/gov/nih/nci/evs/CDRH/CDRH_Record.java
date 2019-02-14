package gov.nih.nci.evs.CDRH;

import java.util.HashSet;
import java.util.Map;

public class CDRH_Record implements Comparable{

	public static String escapeText(String s) {

		if (s.indexOf('&') != -1 || s.indexOf('<') != -1
		        || s.indexOf('>') != -1) {
			StringBuffer result = new StringBuffer(s.length() + 4);
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == '&')
					result.append("&amp;");
				else if (c == '<')
					result.append("&lt;");
				else if (c == '>')
					result.append("&gt;");
				else
					result.append(c);
			}
			return result.toString();
		}
		return s;

	}

	private static boolean hasPipe(String s){
		if(s.contains("|")) return true;
		return false;
	}

	private static HashSet<String> split(String s){
		String[] valueList = s.split("\\|");
		HashSet<String> set = new HashSet<String>();
		for (int i=0; i< valueList.length; i++){
			set.add(escapeText(valueList[i]));
		}
		return set;
	}

	private String fdaCode = "";

	private HashSet<String> fdaDefinition =new HashSet<String>();

	private String fdaPT = "";

	private HashSet<String> fdaSynonym = new HashSet<String>();

	private String imdrfCode = "";

	private String nciCode = "";

	private String nciDefinition = "";
	
	private String parentFdaCode = "";
	
	private String parentFdaPT = "";

	private String parentNciCode = "";

	private String subsetCode = "";

	private String subsetName = "";


	public CDRH_Record(Map record) {
		setNciCode((String) record.get("nci_code"));
		setFdaCode((String) record.get("fda_code"));
		setFdaPT ( (String) record.get("fda_pt"));
		setImdrfCode ( (String) record.get("imdrf_code"));
		setFdaSynonym ( (String) record.get("fda_synonym"));
		setFdaDefinition ( (String) record.get("fda_definition"));
		setNciDefinition ( (String) record.get("nci_definition"));
		setParentNciCode ( (String) record.get("parent_ncicode"));
		setParentFdaCode ( (String) record.get("parent_fdacode"));
		setParentFdaPT((String) record.get("parent_fdapt"));
		setSubsetCode((String) record.get("subset_code"));
		setSubsetName((String) record.get("subset_name"));
	}

	public String getFdaCode() {
		return fdaCode;
	}

	public HashSet<String> getFdaDefinitions() {
		return fdaDefinition;
	}

	public String getFdaPT() {
		return fdaPT;
	}

	public HashSet<String> getFdaSynonyms() {
		return fdaSynonym;
	}

	public String getImdrfCode() {
		return imdrfCode;
	}

	public String getNciCode() {
		return nciCode;
	}

	public String getNciDefinition() {
		return nciDefinition;
	}

	public String getParentFdaCode() {
		return parentFdaCode;
	}

	public String getParentFdaPT() {
		return parentFdaPT;
	}

	public String getParentNciCode() {
		return parentNciCode;
	}

	public String getSubsetCode() {
		return subsetCode;
	}

	public String getSubsetName() {
		return subsetName;
	}
	public void setFdaCode(String fdaCode) {

		this.fdaCode = fdaCode;
		if(hasPipe(fdaCode)){
			System.out.println(fdaCode);
		}
	}
	public void setFdaDefinition(String fdaDefinitionRaw) {
			this.fdaDefinition = split(fdaDefinitionRaw);
	}
	public void setFdaPT(String fdaPT) {
		this.fdaPT=escapeText(fdaPT);
		if(hasPipe(fdaPT)){
			System.out.println(fdaPT);
		}
	}
	
	public void setFdaSynonym(String fdaSynonymRaw) {
		this.fdaSynonym = split(fdaSynonymRaw);
	}
	public void setImdrfCode(String imdrfCode) {
		this.imdrfCode = escapeText(imdrfCode);
		if(hasPipe(imdrfCode)){
			System.out.println(imdrfCode);
		}
	}
	public void setNciCode(String nciCode) {
		this.nciCode = escapeText(nciCode);
		if(hasPipe(nciCode)){
			System.out.println(nciCode);
		}
	}
	public void setNciDefinition(String nciDefinition) {
		this.nciDefinition = escapeText(nciDefinition);
		if(hasPipe(nciDefinition)){
			System.out.println(nciDefinition);
		}
	}
	public void setParentFdaCode(String parentFdaCode) {
		this.parentFdaCode = escapeText(parentFdaCode);
		if(hasPipe(parentFdaCode)){
			System.out.println(parentFdaCode);
		}
	}

	public void setParentFdaPT(String parentFdaPT) {
		this.parentFdaPT = escapeText(parentFdaPT);
		if(hasPipe(parentFdaPT)){
			System.out.println(parentFdaPT);
		}
	}

	public void setParentNciCode(String parentNciCode) {
		this.parentNciCode = escapeText(parentNciCode);
		if(hasPipe(parentNciCode)){
			System.out.println(parentNciCode);
		}
	}
	
	public void setSubsetCode(String subsetCode) {
		this.subsetCode = escapeText(subsetCode);
		if(hasPipe(subsetCode)){
			System.out.println(subsetCode);
		}
	}

	
	public void setSubsetName(String subsetName) {
		this.subsetName = escapeText(subsetName);
		if(hasPipe(subsetName)){
			System.out.println(subsetName);
		}
	}

	public char[] write(String string) {
		
		return null;
	}

	
	public int compareTo(CDRH_Record o) {
		if(this.fdaCode.equals(o.getFdaCode())){
			return (this.nciCode.compareTo(o.getNciCode()));
		}
		return(this.fdaCode.compareTo(o.getFdaCode()));
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return compareTo((CDRH_Record)o);
	}
}
