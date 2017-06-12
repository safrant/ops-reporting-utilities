package gov.nih.nci.evs.eStability;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stability_Data {

	public static List parse(InputStream src) throws IOException {

		// The document as published by the OMB is encoded in Latin-1
		InputStreamReader isr = new InputStreamReader(src, "8859_1");
		BufferedReader in = new BufferedReader(isr);
		List records = new ArrayList();
		String lineItem;
		while ((lineItem = in.readLine()) != null) {
			records.add(splitLine(lineItem));
		}
		return records;

	}

	// the field names in order
	public final static String[] keys = { "subset_code", "subset_name",
	        "nci_code", "nci_pt", "nci_definition", "stab_pt",
	        "stability_definition" };

	private static Map splitLine(String record) {

		record = record.trim();

		int index = 0;
		Map result = new HashMap();
		for (String key : keys) {
			// find the next comma
			StringBuffer sb = new StringBuffer();
			char c;
			boolean inString = false;
			while (true) {
				c = record.charAt(index);
				if (!inString && c == '"') {
					inString = true;
					sb.append("\"");
				} else if (inString && c == '"') {
					inString = false;
					sb.append("\"");
				} else if (!inString && c == '\t')
					break;
				else
					sb.append(c);
				index++;
				if (index == record.length())
					break;
			}
			String s = sb.toString().trim();
			result.put(key, s);
			index++;
			if (index >= record.length())
				break;
		}

		return result;

	}
}
