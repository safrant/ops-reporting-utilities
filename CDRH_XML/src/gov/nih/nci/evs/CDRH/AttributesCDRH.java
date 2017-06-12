package gov.nih.nci.evs.CDRH;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AttributesCDRH {

	public static void convert(List data, OutputStream out) throws IOException {

		Writer wout = new OutputStreamWriter(out, "UTF8");
		wout.write("<?xml version=\"1.0\"  encoding=\"UTF-8\"?>\r\n");
		wout.write("<EventCode version=\"11.06d\" date=\"2011-06-27\">\r\n");
		String previousSubsetCode = "";
		String subsetCode = "";
		Iterator records = data.iterator();
		while (records.hasNext()) {

			Map record = (Map) records.next();
			subsetCode = record.get("subset_code").toString();
			if (!subsetCode.equals(previousSubsetCode)) {
				if (!previousSubsetCode.equals("")) {
					wout.write("\t</SubsetItem>\r\n");
				}
				previousSubsetCode = subsetCode;

				wout.write("\t<SubsetItem");
				writeAttribute(wout, "source", record);
				writeAttribute(wout, "subset_code", record);
				writeAttribute(wout, "subset_name", record);
				wout.write(">\r\n");
			}

			wout.write("\t\t<CodeItem");
			writeAttribute(wout, "nci_code", record);
			writeAttribute(wout, "fda_code", record);
			writeAttribute(wout, "fda_pt", record);
			wout.write(">\r\n");

			// write the attributes
			writeDelimitedElement(wout, "fda_synonym", record);
			writeElement(wout, "fda_definition", record);
			writeElement(wout, "nci_definition", record);
			writeElement(wout, "parent_ncicode", record);
			writeElement(wout, "parent_fdacode", record);
			writeElement(wout, "parent_fdapt", record);

			wout.write("\t\t</CodeItem>\r\n");
			// if (!subsetCode.equals(previousSubsetCode)) {
			// wout.write("\t</SubsetItem>\r\n");
			// previousSubsetCode = subsetCode;
			// }
		}
		wout.write("\t</SubsetItem>\r\n");
		wout.write("</EventCode>\r\n");
		wout.flush();

	}

	private static void writeElement(Writer out, String name, Map record)
	        throws IOException {
		String element = (String) record.get(name);
		if (element == null || element.isEmpty()) {
			out.write("\t\t\t<" + name + " />\n");
		} else {
			out.write("\t\t\t<" + name + ">"
			        + escapeText((String) record.get(name)) + "</" + name
			        + ">\r\n");
		}
	}

	private static void writeDelimitedElement(Writer out, String name,
	        Map record) throws IOException {
		String element = (String) record.get(name);
		if (element == null || element.isEmpty()) {
			out.write("\t\t\t<" + name + " />\r\n");
		} else {
			String[] elements = element.split("\\|");
			for (String element2 : elements) {
				out.write("\t\t\t<" + name + ">" + escapeText(element2) + "</"
				        + name + ">\r\n");
			}
		}
	}

	// Just a couple of private methods to factor out repeated code
	private static void writeAttribute(Writer out, String name, Map record)
	        throws IOException {
		out.write(" " + name + "='"
		        + escapeAttribute((String) record.get(name)) + "'");
	}

	public static String escapeAttribute(String s) {

		if (s.indexOf('&') != -1 || s.indexOf('<') != -1
		        || s.indexOf('>') != -1 || s.indexOf('"') != -1
		        || s.indexOf('\'') != -1) {
			StringBuffer result = new StringBuffer(s.length() + 6);
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == '&')
					result.append("&amp;");
				else if (c == '<')
					result.append("&lt;");
				else if (c == '"')
					result.append("&quot;");
				else if (c == '\'')
					result.append("&apos;");
				else if (c == '>')
					result.append("&gt;");
				else
					result.append(c);
			}
			return result.toString();
		} else {
			return s;
		}

	}

	public static String escapeText(String s) {

		if (s.indexOf('&') != -1 || s.indexOf('<') != -1
		        || s.indexOf('>') != -1 || s.indexOf('"') != -1
		        || s.indexOf('\'') != -1) {
			StringBuffer result = new StringBuffer(s.length() + 6);
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == '&')
					result.append("&amp;");
				else if (c == '<')
					result.append("&lt;");
				// else if (c == '"')
				// result.append("&quot;");
				// else if (c == '\'')
				// result.append("&apos;");
				else if (c == '>')
					result.append("&gt;");
				else
					result.append(c);
			}
			return result.toString();
		} else {
			return s;
		}

	}

	public static void main(String[] args) {

		try {

			if (args.length < 1) {
				System.out.println("Usage: AttributesCDRH infile outfile");
				return;
			}

			InputStream in = new FileInputStream(args[0]);
			OutputStream out;
			if (args.length < 2) {
				out = System.out;
			} else {
				out = new FileOutputStream(args[1]);
			}

			List results = CDRH_Data.parse(in);
			convert(results, out);
		} catch (IOException ex) {
			System.err.println(ex);
		}

	}

}
