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
import java.util.Set;

public class FlatXML_CDRH {

	public static void convert(List data, OutputStream out) throws IOException {

		Writer wout = new OutputStreamWriter(out, "UTF8");
		wout.write("<?xml version=\"1.0\">\r\n");
		wout.write("<Budget>\r\n");

		Iterator records = data.iterator();
		while (records.hasNext()) {
			wout.write("  <LineItem>\r\n");
			Map record = (Map) records.next();
			Set fields = record.entrySet();
			Iterator entries = fields.iterator();
			while (entries.hasNext()) {
				Map.Entry entry = (Map.Entry) entries.next();
				String name = (String) entry.getKey();
				String value = (String) entry.getValue();
				// some of the values contain ampersands and less than
				// signs that must be escaped
				value = escapeText(value);

				wout.write("    <" + name + ">");
				wout.write(value);
				wout.write("</" + name + ">\r\n");
			}
			wout.write("  </LineItem>\r\n");
		}
		wout.write("</Budget>\r\n");
		wout.flush();

	}

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
		} else {
			return s;
		}

	}

	public static void main(String[] args) {

		try {

			if (args.length < 1) {
				System.out.println("Usage: FlatXMLBudget infile outfile");
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
		} catch (IOException e) {
			System.err.println(e);
		}

	}

}
