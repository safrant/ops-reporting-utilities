package gov.nih.nci.evs.CDRH;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class FlatXML_CDRH {
	private static String version = "YY.MMd";
	private static String date = "YYYY-MM-DD";

	public static void convert(List data, OutputStream out) throws IOException {

		String versionString = "<EventCode version='" + version +"' date='" + date+ "'>\r\n";
		
		Writer wout = new OutputStreamWriter(out, "UTF8");
		wout.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		wout.write(versionString);
//		wout.write("<SubsetItem source='FDA' subset_code='C62596' subset_name='FDA Center for Devices and Radiological Health Terminology'>\r\n");	
		//<EventCode version='16.10e' date='2016-10-31'>
		//<SubsetItem source='FDA' subset_code='C62596' subset_name='FDA Center For Devices and Radiological Health Terminology'>
//		public final static String[] keys = { "source", "subset_code",
//		        "subset_name", "nci_code", "fda_pt", "fda_code", "imdrf_code","fda_synonym",
//		        "fda_definition", "nci_definition", "parent_ncicode",
//		         "parent_fdapt", "parent_fdacode" };
//		
		TreeMap<String,TreeSet<CDRH_Record>> SubsetItems = new TreeMap<String, TreeSet<CDRH_Record>>();
		HashMap<String,String> subsetNames = new HashMap<String,String>();
		Iterator records = data.iterator();
		while (records.hasNext()) {
//			wout.write("  <CodeItem>\r\n");
			Map record = (Map) records.next();
			CDRH_Record cdrhRecord = new CDRH_Record(record);
			String subset = cdrhRecord.getSubsetCode();
			if (SubsetItems.containsKey(subset)){
				TreeSet<CDRH_Record> setCdrh = SubsetItems.get(subset);
				setCdrh.add(cdrhRecord);
				SubsetItems.put(subset, setCdrh);
			} else {
				TreeSet<CDRH_Record> set = new TreeSet<CDRH_Record>();
				set.add(cdrhRecord);
				SubsetItems.put(cdrhRecord.getSubsetCode(), set);
				subsetNames.put(cdrhRecord.getSubsetCode(), cdrhRecord.getFdaPT());
			} 
		}
		
		Iterator<String> subsetIterator = SubsetItems.keySet().iterator();
		while(subsetIterator.hasNext()){
			String CdrhKey = subsetIterator.next();
			String subsetName = subsetNames.get(CdrhKey);
			//print out subset
			String subSetString = "<SubsetItem source='FDA' subset_code='"+CdrhKey+"' subset_name='"+subsetName+"'>\r\n";
			wout.write(subSetString);

			//print out CDRH records
			Set<CDRH_Record> set = SubsetItems.get(CdrhKey);
			for(CDRH_Record cdrhRecord:set){
				String codeItem = "  <CodeItem nci_code='"+cdrhRecord.getNciCode()+ "' fda_code='"+cdrhRecord.getFdaCode()+ "'>\r\n";
				wout.write(codeItem);
				wout.write(write("fda_pt", cdrhRecord.getFdaPT()));
				wout.write(write("imdrf_code", cdrhRecord.getImdrfCode()));
				wout.write(write("fda_synonym",cdrhRecord.getFdaSynonyms()));
				wout.write(write("fda_definition",cdrhRecord.getFdaDefinitions()));
				wout.write(write("nci_definition", cdrhRecord.getNciDefinition()));
				wout.write(write("parent_ncicode", cdrhRecord.getNciCode()));
				wout.write(write("parent_fdacode", cdrhRecord.getParentFdaCode()));
				wout.write(write("parent_fdapt", cdrhRecord.getParentFdaPT()));
				wout.write("  </CodeItem>\r\n");
			}
			//end subset
			wout.write("</SubsetItem>\r\n");
		}
			
		
			
//			Set fields = record.entrySet();
//			Iterator entries = fields.iterator();
//			while (entries.hasNext()) {
//				Map.Entry entry = (Map.Entry) entries.next();
//				String name = (String) entry.getKey();
//				String value = (String) entry.getValue();
//				// some of the values contain ampersands and less than
//				// signs that must be escaped
//				value = escapeText(value);
//				if(value.startsWith("\"")){
//					System.out.println(value);
//					value = value.substring(1,value.length());
//				}
//				if(value.endsWith("\"")){
//					System.out.println(value);
//					value = value.substring(0,value.length()-1);
//				}
//
//				if(value.contains("|")){
//					//Is synonym list?
////					System.out.println(value);
//					String[] valueList = value.split("\\|");
//					for(int i=0; i<valueList.length;i++){
//						wout.write("    <" + name + ">");
//						wout.write(valueList[i]);
//						wout.write("</" + name + ">\r\n");
//					}
//				} else {
//					wout.write("    <" + name + ">");
//					wout.write(value);
//					wout.write("</" + name + ">\r\n");
//				}
//				
//			}
//			wout.write("  </CodeItem>\r\n");
//		}
//		wout.write("  </SubsetItem>\r\n");
		wout.write("</EventCode>\r\n");
		wout.flush();}

	private static String write(String name, HashSet<String> values) {
		String output = "";
		for(String value:values){
			if(value.length()>0){
			output = output +"    <" + name + ">"+value+"</"+name+">\r\n";
			}else {
				output = "    <" + name + " />\r\n";
			}
		}
		return output;
	}
	private static String write(String name, String value) {
		String output = null;
		if(value.length()>0){
		output = "    <" + name + ">"+value+"</"+name+">\r\n";
		}else {
			output = "    <" + name + " />\r\n";
		}
		return output;

	}
	


	public static void main(String[] args) {

		try {

			if (args.length < 1) {
				System.out.println("Usage: FlatXMLBudget infile outfile version date");
				return;
			}

			InputStream in = new FileInputStream(args[0]);
			OutputStream out;
			if (args.length < 2) {
				out = System.out;
			} else {
				out = new FileOutputStream(args[1]);
			}
			if (args.length ==4){
				version = args[2];
				date = args[3];
			}

			List results = CDRH_Data.parse(in);
			convert(results, out);
		} catch (IOException e) {
			System.err.println(e);
		}

	}

}
