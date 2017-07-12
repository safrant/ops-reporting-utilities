package gov.nih.nci.evs.protege;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Vector;

//driver
public class FormatProtegeReport {
	
	String inputReport = null;
	String excelFile = null;
	String configFile = new String("./config/config.txt");
	ExcelReport report = null;
	Vector<Column> columns = new Vector<Column>();
	Field[][] fields; 
	HashMap<Integer, String> inputHeader = new HashMap<Integer, String>();
	
	public static void main(String[] args) {
		FormatProtegeReport format = new FormatProtegeReport();
		long start = System.currentTimeMillis();
		format.configure(args);
		format.run();
		System.out.println("Finished report in "
		        + (System.currentTimeMillis() - start) / 1000 + " seconds.");
	}
	
	public void printHelp() {
		System.out.println("");
		System.out
		        .println("Usage: FormatProtegeReport [OPTIONS] ... [RAW REPORT INPUT FILE] [EXCEL OUTPUT FILE]");
		System.out.println(" ");
		System.out.println("  -I, --input\t\tpath to match input text file");
		//more to come maybe
		System.exit(1);
	}
	
	public void configure(String[] args) {
		if (args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				String option = args[i];
				if (option.equalsIgnoreCase("--help")) {
					printHelp();
				}
				if (option.equalsIgnoreCase("-I")
				        || option.equalsIgnoreCase("--input")) {
					configFile = args[++i];
				}
				else {
					if (i == args.length - 2) {
						inputReport = option;
						File input = new File(inputReport);
						if (input.isFile()) {
							//assume the input is valid, might want to check if it's txt or csv though
						}
						else {
							System.err.println("!! Invalid Protege report input file (" + option
							        + ").");
							printHelp();
						}
					}
					if (i == args.length - 1) {
						excelFile = option;
						report = new ExcelReport(excelFile);
					}
				}
			}
		}
	}
	
	public void run() {
		readAndSetColumns(configFile);
		processReport(inputReport);
		
	}
	
	
	public void processReport(String filename) {
		FileReader file = null;
		BufferedReader buff = null;
		try {
			file = new FileReader(filename);
			buff = new BufferedReader(file);
			boolean eof = false;
			int index = 0;
			while(!eof) {
				String line = buff.readLine();
				if( line == null )
					eof = true;
				else {
					if( index == 0 ) {
						//this is the header of the report
						//map the slot to desired columns
						line.replace("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#", "");
						String[] headerValues = line.split("\t");
						for(int i=0; i < headerValues.length; i++) {
							inputHeader.put(i, headerValues[i]);
						}							
					}
					else {
						String[] fieldStrings = line.split("\t");
						for( int i=0; i < fieldStrings.length; i++) {
							//TODO: pick up here
						}
						
					}
					
				}
			}
		}
		 catch (Exception e) {
				e.printStackTrace();
			} finally {
				// Closing the streams
				try {
					buff.close();
					file.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}		
	}
	
	public void readAndSetColumns(String filename) {
		FileReader file = null;
		BufferedReader buff = null;
		try {
			file = new FileReader(filename);
			buff = new BufferedReader(file);
			boolean eof = false;
			int colIndex = -1;
			while (!eof) {
				String line = buff.readLine();
				if (line == null)
					eof = true;
				else {
					boolean endOfCol = false;
					colIndex++;
					int columnNumber = -1;
					String propertyId = null;
					String qualifierName = null;
					Vector<Qualifier> qualifiers = new Vector<Qualifier>();
					while( !endOfCol ) {							
						if( line != null && line.contains("=") ) {
							String[] values = line.split("=");							
							String name = values[0].trim();
							String value = values[1].trim();
//							System.out.println(name + ": " + value);
							switch (name) {
								case "ColumnNumber":
									columnNumber = Integer.parseInt(value);
									break;
								case "Property":
									propertyId = value;
									break;
								case "QualifierName":
									qualifierName = value;
									break;
								case "QualifierValue":
									if( qualifierName != null ) {
										qualifiers.add(new Qualifier(qualifierName, value));
									}
									else {
										System.err.println("Invalid configuration entry: " + line);
										System.err.println("Entry not preceded by a QualifierName.");
										System.err.println("Exiting");
										System.exit(-1);
									}
									break;
								default:
									System.err.println("Invalid configuration entry: " + line);
									System.err.println("Exiting");
									System.exit(-1);
									break;		
							}
							line = buff.readLine();							
						}
						else {
							endOfCol = true;
						}

					}
					if( propertyId != null ) {
						System.out.println("Adding " + columnNumber);
						Column column = new Column(columnNumber, propertyId, qualifiers);
						columns.add(column);
					}
					else {
						System.err.println("No Property configured for configuration index " + colIndex);
						System.err.println("Exiting");
						System.exit(-1);
					}						
				}
			}
//			for( int i=0; i < columns.size(); i++ ) {
//				System.out.println(columns.elementAt(i).getColNumber());
//				System.out.println(columns.elementAt(i).getProperty().getPropertyId());
//				if( columns.elementAt(i).getProperty().getQualifiers() != null ) {
//					for( String key : columns.elementAt(i).getProperty().getQualifiers().keySet() ) {
//						System.out.println(key + "-> " + columns.elementAt(i).getProperty().getQualifiers().get(key));
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Closing the streams
			try {
				buff.close();
				file.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	

}
