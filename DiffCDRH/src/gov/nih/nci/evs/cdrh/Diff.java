/* Rob Wynne, MSC
 * 
 * Create a changes report from an
 * old and new CDRH subset report.
 */


package gov.nih.nci.evs.cdrh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

public class Diff {
	
	public HashMap<Subset, ArrayList<Concept>> newMap = new HashMap<Subset, ArrayList<Concept>>();
	public HashMap<Subset, ArrayList<Concept>> oldMap = new HashMap<Subset, ArrayList<Concept>>();
	public Vector<Change> changes = new Vector<Change>();
	PrintWriter pw;
	
	/*
	 * 0 - New report text file
	 * 1 - Old report text file
	 * 2 - Change report save filename	
	 */
	public static void main(String args[]) {
		Diff report = new Diff();
		if( args.length == 3 ) {
			System.out.println("Initializing diff report...");			
			report.init(args[0], args[1], args[2]);
			System.out.println("Getting changes...");
			report.getChanges();
			System.out.println("Printing changes report...");
			report.print();
		}
		else {
			printHelp();
		}
	}
	
	public static void printHelp() {
		//TODO: Create batch scripts
		System.out.println("Unable to start program due to improper input parameters. Program will exit.");
		System.out.println();
		System.out.println("Run as: DiffCDRH [new report text file] [previous report text file] [output filename]");
		System.out.println("Example: DiffCDRH \"FDA-CDRH_NCIt_Subsets.txt\" \"FDA-CDRH_NCIt_Subsets 2014-07-08.txt\" \"D:\\data\\Changes.txt\"");
		System.out.println();
		System.exit(0);
	}
	
	private void config_pw(String fileLoc) {
		try {
			File file = new File(fileLoc);
			pw = new PrintWriter(file);
		} catch (Exception e) {
			System.out.println("Error in PrintWriter");
		}
	}	
	
	public void init(String newReport, String oldReport, String saveDestination) {
		newMap = parseCDRH(newReport);
		oldMap = parseCDRH(oldReport);
//		for( Subset s : newMap.keySet() ) {
//			System.out.println(s.getName());
//		}
//		
//		for( Subset s : oldMap.keySet() ) {
//			System.out.println(s.getName());
//		}
		
		config_pw(saveDestination);
	}
	
	public void getChanges() {
		for(Subset newSub : newMap.keySet()) {
			boolean found = false;				
			for(Subset oldSub : oldMap.keySet() ) {
				if( newSub.getCode().equals(oldSub.getCode()) ) {
					found = true;
					compareSubsets(newSub, oldSub);
					if( newMap.get(newSub) != null && oldMap.get(oldSub) != null )
						compareConcepts(newMap.get(newSub), oldMap.get(oldSub), newSub.getName());
					break;
				}
			}
			if( !found ) {
				getChangesForNewSubset(newSub, newMap.get(newSub));
			}
		}
		
		for(Subset oldSub : oldMap.keySet()) {
			boolean found = false;
			for(Subset newSub : newMap.keySet()) {
				if(oldSub.getCode().equals(newSub.getCode()) ) {
					found = true;
				}
			}
			if( !found ) {
				getChangesForRemovedSubset(oldSub, oldMap.get(oldSub));
			}
		}		
		
	}
	
	public void getChangesForNewSubset(Subset newSub, ArrayList<Concept> newConcepts) {
		changes.add(new Change("Add", newSub.getCode(), "", newSub.getName(), "FDA Subset", "Add Subset", "- - -", newSub.getName()));
		for( Concept c : newConcepts ) {
			changes.add(new Change("Add", c.getConceptCode(), c.getSourcePT(), newSub.getName(), "Concept", "Add Concept to Subset", "- - -", c.getSourcePT()));
		}
	}
	
	public void getChangesForRemovedSubset(Subset oldSub, ArrayList<Concept> oldConcepts) {
		changes.add(new Change("Remove", oldSub.getCode(), "", oldSub.getName(), "FDA Subset", "Remove Subset", oldSub.getName(), "- - -"));
		for( Concept c : oldConcepts ) {
			changes.add(new Change("Remove", c.getConceptCode(), c.getSourcePT(), oldSub.getName(), "Concept", "Remove Concept from Subset", c.getSourcePT(), "- - -"));
		}
	}
	
	public void compareSubsets(Subset newSub, Subset oldSub) {
		if( !newSub.getName().equals(oldSub.getName()))
			changes.add(new Change("Update", newSub.getCode(), newSub.getName(), newSub.getName(), "FDA Subset Name", "Update Subset Name", oldSub.getName(), newSub.getName()));
		if( !newSub.getSource().equals(oldSub.getSource()))
			changes.add(new Change("Update", newSub.getCode(), newSub.getName(), newSub.getName(), "Source", "Update Source", oldSub.getSource(), newSub.getSource()));
	}
	
	public void compareConcepts(ArrayList<Concept> newConcepts, ArrayList<Concept> oldConcepts, String subsetName) {
		for(Concept newConcept : newConcepts ) {
			boolean found = false;
			for( Concept oldConcept : oldConcepts ) {
				if( newConcept.getConceptCode().equals(oldConcept.getConceptCode()) ) {
					found = true;
					diffConcepts(newConcept, oldConcept, subsetName);
				}
			}
			if(!found)
				changes.add(new Change("Add", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "Concept", "Add Concept to Subset", "- - -", newConcept.getSourcePT()));
		}
		
		for( Concept oldConcept : oldConcepts ) {
			boolean found = false;
			for( Concept newConcept : newConcepts ) {
				if( oldConcept.getConceptCode().equals(newConcept.getConceptCode()) ) {
					found = true;
				}
			}
			if(!found)
				changes.add(new Change("Remove", oldConcept.getConceptCode(), oldConcept.getSourcePT(), subsetName, "Concept", "Remove Concept from Subset", oldConcept.getSourcePT(), "- - -"));
		}
	}
	
	public void diffConcepts(Concept newConcept, Concept oldConcept, String subsetName) {
		if( !newConcept.getNCIDefinition().equals(oldConcept.getNCIDefinition()))
			changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "NCIt Definition", "Update NCIt Definition", oldConcept.getNCIDefinition(), newConcept.getNCIDefinition()));
		if( !newConcept.getParentNCIConceptCode().equals(oldConcept.getParentNCIConceptCode()))
			changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "Parent Concept's NCIt Concept Code", "Update Parent Concept's NCIt Concept Code", oldConcept.getParentNCIConceptCode(), newConcept.getParentNCIConceptCode()));
		if( !newConcept.getParentSourceCode().equals(oldConcept.getParentSourceCode()))
			changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "Parent Concept's FDA Source Code", "Update Parent Concept's FDA Source Code", oldConcept.getParentSourceCode(), newConcept.getParentSourceCode()));
		if( !newConcept.getParentSourcePT().equals(oldConcept.getParentSourcePT()))
			changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "Parent Concept's Source PT", "Update Parent Concept's Source PT", oldConcept.getParentSourcePT(), newConcept.getParentSourcePT()));
		if( !newConcept.getSourceCode().equals(oldConcept.getSourceCode()))
			changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "FDA Source Code", "Update FDA Source Code", oldConcept.getSourceCode(), newConcept.getSourceCode()));
		if( !newConcept.getSourceDefinition().equals(oldConcept.getSourceDefinition()))
			changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "FDA Source Definition", "Update FDA Source Definition", oldConcept.getSourceDefinition(), newConcept.getSourceDefinition()));
		if( !newConcept.getSourcePT().equals(oldConcept.getSourcePT()))
			changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "FDA Source PT", "Update FDA Source Definition", oldConcept.getSourcePT(), newConcept.getSourcePT()));
		String[] old_syns = oldConcept.getSynonyms();
		String[] new_syns = newConcept.getSynonyms();
		Arrays.sort(old_syns);
		Arrays.sort(new_syns);		

		//forwards
		for(int i=0; i < new_syns.length; i++) {
			boolean found = false;
			for(int j=0; j < old_syns.length; j++) {
				if( new_syns[i].equals(old_syns[j])) {
					found = true;
				}
			}
			if( !found ) {
				if( !new_syns[i].equals("") ) {
					changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "FDA Source Synonym", "Add FDA Synonym", "- - -", new_syns[i])); 
				}
			}
		}	
		
		//and backwards
		for(int i=0; i < old_syns.length; i++) {
			boolean found = false;
			for(int j=0; j < new_syns.length; j++) {
				if( old_syns[i].equals(new_syns[j])) {
					found = true;
				}
			}
			if( !found ) {
				if( !old_syns[i].equals("") ) {
					changes.add(new Change("Update", newConcept.getConceptCode(), newConcept.getSourcePT(), subsetName, "FDA Source Synonym", "Remove FDA Synonym", old_syns[i], "- - -"));				
				}
			}
		}		
		
	}
	
	public HashMap<Subset, ArrayList<Concept>> parseCDRH(String filename) {
		HashMap<Subset, ArrayList<Concept>> map = new HashMap<Subset, ArrayList<Concept>>();
		
		try {
			Scanner input = new Scanner(new File(filename));
			int lineNum = 0;
			while(input.hasNext()) {
				lineNum++;
				String line = input.nextLine();
				String[] tmpTokens = line.split("\t");
				String[] tokens = new String[12];
				
				for(int i = 0; i < tmpTokens.length; i++ ) {
					tokens[i] = tmpTokens[i];
				}
				
				//not all rows have a Parent Concept FDA Source Code and/or FDA PT
				if( tokens.length - tmpTokens.length != 0 ) {
					tokens[10] = "";
					tokens[11] = "";
				}

				
				if( tokens.length == 12 ) {
					/* 0 - Source
					 * 1 - Ncit Subset Code
					 * 2 - Subset name
					 * 3 - NCIt Concept Code
					 * 4 - FDA Source Code
					 * 5 - FDA Source PT
					 * 6 - FDA Source Synonyms
					 * 7 - FDA Source Definition
					 * 8 - NCIt Definition
					 * 9 - Parent Concept's NCIt Concept Code
					 * 10 - Parent Concept's FDA Source Code
					 * 11 - Parent Concept's FDA Source PT
					 */					
					String source = tokens[0];
					String subsetCode = tokens[1];
					String subsetName = tokens[2];
					String conceptCode = tokens[3];
					String fdaSourceCode = tokens[4];
					String fdaSourcePT = tokens[5];
					String fdaSynonyms = tokens[6];
					String fdaDefinition = tokens[7];
					String nciDefinition = tokens[8];
					String parentConceptCode = tokens[9];
					String parentFDACode = tokens[10];
					String parentFDAPT = tokens[11];
						
					Subset subset = null;
					
					boolean found = false;
					for(Subset s : map.keySet()) {
						if( s.getCode().equals(subsetCode) ) {
							found = true;
							subset = s;
							break;
						}
					}
					if( !found ) {
						subset = new Subset(subsetCode, subsetName, source);						
						map.put(subset, null);
					}
					
					Concept c = new Concept(conceptCode, fdaSourceCode, fdaSourcePT, fdaSynonyms, fdaDefinition, nciDefinition, parentConceptCode, parentFDACode, parentFDAPT);

					ArrayList<Concept> tmp = map.get(subset);
					if( tmp == null ) {
						tmp = new ArrayList<Concept>();
						tmp.add(c);
						map.put(subset, tmp);
					}
					else {
						tmp.add(c);
						map.put(subset, tmp);
					}

				}
				else {
					System.err.println("Unable to read " + filename + ": line " + lineNum);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Unable to open and parse file " + filename);
			e.printStackTrace();
		}			
		return map;
	}
	
	public void print() {
		Collections.sort(changes, new Comparator<Change>() {
			public int compare(final Change change1, final Change change2) {
			    return change1.getNoCCode() - change2.getNoCCode();
			}	
		});
		
		Collections.sort(changes, new Comparator<Change>() {
			public int compare(final Change change1, final Change change2) {
			    return change1.getSubsetName().compareTo(change2.getSubsetName());
			}	
		});	
		
		pw.println("Change Type\tNCI Code\tFDA Source PT\tFDA Subset Name\tElement\tChange Summary\tOld Value\tNew Value");
		for(Change c : changes ) {
			pw.print(c.toString());
		}
		pw.close();		
	}

}
