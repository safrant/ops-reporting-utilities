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
	Vector<Field> fields = new Vector<Field>();
	HashMap<String, Integer> inputHeader = new HashMap<String, Integer>();
	HashMap<Integer, String> inputHeader_rev = new HashMap<Integer, String>();
	
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
		System.out.println("  -C, --config\t\tpath to config file");
		System.out.println("  --help\t\tprint this help");
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
				if (option.equalsIgnoreCase("-C")
				        || option.equalsIgnoreCase("--config")) {
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
		report = new ExcelReport(excelFile);
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
				if( line == null ) {
					eof = true;
					report.close();
				}
				else {
					if( index == 0 ) {
						//this is the header of the report
						//map the slot to desired columns
						line = line.replace("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#", "");
						String[] headerValues = line.split("\t");
						for(int i=0; i < headerValues.length; i++) {
							inputHeader.put(headerValues[i], i);
							inputHeader_rev.put(i, headerValues[i]);
						}
						Vector<String> row = new Vector<String>();
						for(int i=0; i < columns.size(); i++) {
							String rowString;							
							String propString = columns.elementAt(i).getProperty().getPropertyId();							
							if( inputHeader.containsKey(columns.elementAt(i).getProperty().getPropertyId()) ) {
								Vector<Qualifier> quals = columns.elementAt(i).getProperty().getQualifiers();
								if( quals != null && quals.size() > 0 ) {
									String qualString = "{";
									for( int j=0; j < quals.size(); j++ ) {
										qualString = qualString.concat("\"" + quals.elementAt(j).getQualifierName() + "\" : ");
										qualString = qualString.concat("\"" + quals.elementAt(j).getQualifierValue() + "\"");
										if( j+1 < quals.size() ) {
											qualString = qualString.concat(",");
										}
									}
									qualString = qualString.concat("}");
									rowString = propString + " " + qualString;
								}
								else {
									rowString = propString;
								}
							}
							else {
								rowString = propString;
							}
							row.add(rowString);							
						}
						report.printHeader(row);
						index++;
					}
					else {
//						System.out.println("Clearing fields...\n");
						fields.clear();
						String[] fieldStrings = line.split("\t");
						String rowString;
						Vector<String> row = new Vector<String>();
						for( int i=0; i < fieldStrings.length; i++) {
							//TODO: pick up here
							Field f = new Field(fieldStrings[i], inputHeader_rev.get(i));
//							f.print();
							fields.add(f);
						}
						for( int i=0; i < columns.size(); i++ ) {
							Property colProp = columns.elementAt(i).getProperty();
							int colIndex = columns.elementAt(i).getColNumber();  //TODO
							Vector<Qualifier> colQuals = colProp.getQualifiers();								
							
							rowString = "";
							if( fields.size() > 0 ) {									
								for( int j=0; j < fields.size(); j++ ) {
									Field f = fields.elementAt(j);								
									
									
									//check matching property
									if( colProp.getPropertyId().equals(f.getPropertyName()) ) {
										//check matching qualifiers
										Vector<Property> fieldProps = f.getProperties();
										if( fieldProps.size() > 0 ) {										
											for(int k=0; k < fieldProps.size(); k++) {
												boolean add = false;													
												Property p = fieldProps.elementAt(k);
												Vector<Qualifier> qs = p.getQualifiers();
												if( qs != null && qs.size() > 0 ) {
													//if the colQuals contains any of the field property qualifiers
													Vector<Boolean> qualifierTesting = new Vector<Boolean>();
													for( Qualifier colQual : colQuals ) {
														Boolean contained = false;
														for( Qualifier q : qs ) {
															if( q.getQualifierName().equals(colQual.getQualifierName()) &&
																q.getQualifierValue().equals(colQual.getQualifierValue()) ) 
																contained = true;
														}
														qualifierTesting.add(contained);
													}
													if( !qualifierTesting.contains(Boolean.FALSE) ) {
														add = true;
													}
												}
												else {
													add = true;
												}
												if( add ) {
													if( rowString.equals("") ) {
														rowString = p.getPropertyId();
													}
													else {
														rowString = rowString.concat("|" + p.getPropertyId());
													}
												}												
											}
										}
									}
								
								}
							}
							else {
								//do nothing
							}
							row.add(rowString);								
						}
						report.printValues(row);
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
//						System.out.println("Adding " + columnNumber);
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
//				columns.elementAt(i).print();
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
