package gov.nih.nci.evs.subsetdiff;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class Diff {

    String oldFileName;
    String newFileName;
    String diffFileName;
    String subsetIdName;
    String conceptIdName;
    HashMap<String, SubsetElement> oldFile;
    HashMap<String, SubsetElement> newFile;
    HashMap<String, SubsetElement> oldFileNoMatch;
    HashMap<String, SubsetElement> newFileNoMatch;
    SubsetElement[][] compareArray;
    TreeSet<DiffElement> diffSet;
    String header;
    PrintWriter pw;

    /**
     * 0 - New report text file 1 - Old report text file 2 - Change report save
     * filename
     */
    public static void main(String[] args) {

        @SuppressWarnings("unused")
        final Diff myDiff = new Diff(args);

    }

    public Diff(String[] args) {
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                final String option = args[i];
                // if (option.equalsIgnoreCase("--help")) {
                // printHelp();
                // }
                    if (option.equalsIgnoreCase("-o")) {
                    this.oldFileName = args[++i];
                } else if (option.equalsIgnoreCase("-n")) {
                    this.newFileName = args[++i];
                } else if (option.equalsIgnoreCase("-d")) {
                    this.diffFileName = args[++i];
                } else if (option.equalsIgnoreCase("-s")) {
                    this.subsetIdName = args[++i];
                } else if (option.equalsIgnoreCase("-c")) {
                    this.conceptIdName = args[++i];
                } else {
                    System.out.println("Invalid argument" + args[i]);
                    printHelp();
                }
            }
        } else {
            System.out.println("No arguments entered");
            printHelp();
        }
        
        if (this.conceptIdName==null){
            System.out.println("ERROR: No concept ID provided");
            printHelp();
            System.exit(1);
        }
        
        if (this.subsetIdName==null){
            System.out.println("ERROR: No subset ID  provided");
            printHelp();
            System.exit(1);
        }

        try{
        config_pw(this.diffFileName);  
        this.oldFile = LoadFile(this.oldFileName);
        this.newFile = LoadFile(this.newFileName);

        getHeader(this.oldFile);

        compareFirstPass();
        compareSecondPass();
        compareThirdPass();
        // outputDiff();
        outputLongDiff();}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void config_pw(String fileLoc) {
        try {
            final File file = new File(fileLoc);
            this.pw = new PrintWriter(file);
        } catch (final Exception e) {
            System.out.println("Error in PrintWriter");
        }
    }

    private void getHeader(HashMap<String, SubsetElement> headerSource) {
        final Iterator<String> iter = headerSource.keySet().iterator();
        if (iter.hasNext()) {
            final String key = iter.next();
            this.header = headerSource.get(key).getHeadersAsString();
        }
    }

    private void outputDiff() {
        // Print the diff map to the specified location.
        // using diffMap and diffFileName
        this.pw.println("Change\t" + "Subset\t" + "Concept\t"
                + "Value Changed\t" + "Old Value\t" + "New Value");
        for (final DiffElement de : this.diffSet) {
            this.pw.println(de.toString());
        }
        this.pw.close();

    }

    private void outputLongDiff() {
        this.pw.println("Change\t" + "Subset\t" + "Concept\t"
                + "Value Changed\t" + "Old Value\t" + "New Value\t"
                + this.header);
        for (final DiffElement de : this.diffSet) {
            this.pw.println(de.toString() + "\t"
                    + de.getElement().getValuesAsString());
        }
        this.pw.close();
    }

    private HashMap<String, SubsetElement> LoadFile(String fileName) {
        // First line will be the header. All other lines will be data
        final HashMap<String, SubsetElement> tempMap = new HashMap<String, SubsetElement>();
        try {
            final Scanner input = new Scanner(new File(fileName));
            String line = input.nextLine();
            final String[] headers = line.split("\t");

            Integer i = 1;
            while (input.hasNext()) {
                line = input.nextLine();
                final String[] values = line.split("\t");
                // if(values.length!=headers.length){
                // System.out.println("Missing values in data row "+ i);
                // break;
                // }

                final SubsetElement element = new SubsetElement(headers,
                        values, this.subsetIdName, this.conceptIdName);
                tempMap.put(element.getHash(), element);
                i++;
            }
            input.close();

        } catch (final FileNotFoundException e) {
            System.err.println("Unable to open and parse file " + fileName);
            e.printStackTrace();
        }

        return tempMap;
    }

    private void compareFirstPass() {
        // compare oldFile and newFile
        // iterate oldFile and see if there is a match in newFile.
        // If no match, then add to oldFileNoMatch map
        // In this map the key is subsetCode+conceptCode
        this.oldFileNoMatch = new HashMap<String, SubsetElement>();
        Iterator<String> iter = this.oldFile.keySet().iterator();
        while (iter.hasNext()) {
            final String key = iter.next();
            if (!this.newFile.containsKey(key)) {
                final String newKey = this.oldFile.get(key).getSubsetCode()
                        + this.oldFile.get(key).getConceptCode();
                this.oldFileNoMatch.put(newKey, this.oldFile.get(key));
            }
        }

        // Now repeat going the other way, pulling all new file no matches into
        // newFileNoMatch
        this.newFileNoMatch = new HashMap<String, SubsetElement>();
        iter = this.newFile.keySet().iterator();
        while (iter.hasNext()) {
            final String key = iter.next();
            if (!this.oldFile.containsKey(key)) {
                final String newKey = this.newFile.get(key).getSubsetCode()
                        + this.newFile.get(key).getConceptCode();
                this.newFileNoMatch.put(newKey, this.newFile.get(key));
            }
        }

        // dump the initial large HashMaps to reclaim some memory
        this.oldFile = null;
        this.newFile = null;
    }


    private void compareSecondPass() {
        // Take the filtered map files and compare to each other using the new
        // key.
        // Place all matches into an array for re-examination
        final int arraySize = this.oldFileNoMatch.size()
                + this.newFileNoMatch.size();
        this.compareArray = new SubsetElement[arraySize][2];
        final Iterator<String> oldIter = this.oldFileNoMatch.keySet()
                .iterator();
        int i = 0;
        while (oldIter.hasNext()) {
            final String key = oldIter.next();
            final SubsetElement oldElement = this.oldFileNoMatch.get(key);
            SubsetElement newElement = null;
            if (this.newFileNoMatch.containsKey(key)) {
                newElement = this.newFileNoMatch.get(key);
            }
            this.compareArray[i][0] = oldElement;
            this.compareArray[i][1] = newElement;
            i++;
        }
        // now go through the new elements and check for any that have no match.
        // these will be new elements with no corresponding old element
        final SubsetElement oldElement = null;
        final Iterator<String> newIter = this.newFileNoMatch.keySet()
                .iterator();
        while (newIter.hasNext()) {
            final String key = newIter.next();
            if (!this.oldFileNoMatch.containsKey(key)) {
                final SubsetElement newElement = this.newFileNoMatch.get(key);
                this.compareArray[i][0] = oldElement;
                this.compareArray[i][1] = newElement;
                i++;
            }

        }

    }

    private void compareThirdPass() {
        this.diffSet = new TreeSet<DiffElement>();

        // take the compare array and iterate through.
        for (final SubsetElement[] element : this.compareArray) {
            // compare the two elements in each row to each other key by key
            final SubsetElement oldElement = element[0];
            final SubsetElement newElement = element[1];

            if (oldElement == null && newElement != null) {
                // This is a newly added element
                final DiffElement de = new DiffElement(newElement);
                de.setElementChanged("New Concept");
                de.setNewValue("");
                de.setOldValue("");
                de.setTypeOfChange("A");
                this.diffSet.add(de);
            } else if (oldElement != null && newElement == null) {
                // This is a removed element
                final DiffElement de = new DiffElement(oldElement);
                de.setElementChanged("Removed Concept");
                de.setNewValue("");
                de.setOldValue("");
                de.setTypeOfChange("D");
                this.diffSet.add(de);
            } else if (oldElement != null && newElement != null) {

                // compare the ValueMap of old to new.
                final TreeSet<DiffElement> valueDiff = oldElement
                        .diffSubsetElement(newElement);
                for (final DiffElement de : valueDiff) {
                    this.diffSet.add(de);
                }

            }

        }

    }

    public static void printHelp() {
        System.out.println("");
        System.out.println("Usage: Diff [OPTIONS]");
        System.out.println(" ");
        System.out.println("  -o, \t\tPath to the old file)");
        System.out.println("  -n, \t\tPath to the current file");
        System.out.println("  -d, \t\tPath to save diff file");
        System.out.println("  -s, \t\tName of subset ID");
        System.out.println("  -c, \t\tName of concept ID");
        System.out.println();
        System.out
                .println("Example: Diff -o \"D:\\data\\NCPDP_August.txt\" -n \"D:\\data\\NCPDP_September.txt\" -d \"D:\\data\\diffFile.txt\" -s \"NCIt Subset Code\" -c \"NCIt Code\" ");
        System.exit(1);
    }
}
