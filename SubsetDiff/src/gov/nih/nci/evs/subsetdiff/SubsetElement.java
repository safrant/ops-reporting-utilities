package gov.nih.nci.evs.subsetdiff;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

public class SubsetElement implements Comparable<SubsetElement> {

    private String subsetCode;
    private String conceptCode;
    TreeMap<String, String> valueMap;
    private String hash;

    public String getSubsetCode() {
        return this.subsetCode;
    }

    public String getConceptCode() {
        return this.conceptCode;
    }

    public String getHash() {
        return this.hash;
    }

    public TreeMap<String, String> getTreeMap() {
        return this.valueMap;
    }

    public SubsetElement(String[] headers, String[] values,
            String subsetIdName, String conceptIdName) {

        if (headers.length != values.length) {
            System.out
                    .println("Error - header length does not match value length");
            // throw new java.lang.ArrayIndexOutOfBoundsException();
            // There are optional columns at the end of some of the value rows.
        }

        this.valueMap = new TreeMap<String, String>();
        for (int i = 0; i < headers.length; i++) {
            if (i < values.length) {
                this.valueMap.put(headers[i], values[i]);
            } else {
                this.valueMap.put(headers[i], "");
            }
        }

        if (this.valueMap.containsKey(subsetIdName)) {
            this.subsetCode = this.valueMap.get(subsetIdName);
        }

        if (this.valueMap.containsKey(conceptIdName)) {
            this.conceptCode = this.valueMap.get(conceptIdName);
        }

        if (this.subsetCode.length() == 0 && this.conceptCode != null) {
            this.subsetCode = this.conceptCode;
        }

        if (this.subsetCode == null || this.conceptCode == null) {
            System.out.println("Was not able to assign identifiers to element");
            throw new java.lang.NullPointerException();
        }

        // if(subsetCode.equals("C54450")){
        // String debug = "debug";
        // }
        // if (conceptCode.equals("C27298")){
        // String debug = "debug";
        // }

        this.valueMap.remove(subsetIdName);
        this.valueMap.remove(conceptIdName);
        HashValues(values);
    }

    private void HashValues(String[] hashMe) {
        String holder = "";
        for (final String element : hashMe) {
            holder = holder + element;
        }
        try {
            final MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            final byte[] buffer = holder.getBytes();
            md.update(buffer);
            final byte[] digest = md.digest();

            String hexStr = "";
            for (final byte element : digest) {
                hexStr += Integer.toString((element & 0xff) + 0x100, 16)
                        .substring(1);
            }
            this.hash = hexStr;
        } catch (final NoSuchAlgorithmException e) {

            e.printStackTrace();
        }

    }

    private Integer makeIntegerCode(String code) {
        // Remove the C from the subsetCode to allow numerical sorting
        code = code.replace("C", "");
        if (code.length() > 0) {
            return Integer.valueOf(code);
        } else {
            return null;
        }

    }

    @Override
    public int compareTo(SubsetElement o) {
        // compare the SubsetElement.subsetCode
        final String subsetCode = this.getSubsetCode();
        final Integer intSubsetCode = makeIntegerCode(subsetCode);

        final String diffSubsetCode = o.getSubsetCode();
        final Integer intDiffSubsetCode = makeIntegerCode(diffSubsetCode);

        if (intSubsetCode > intDiffSubsetCode) {
            return 1;
        } else if (intSubsetCode < intDiffSubsetCode) {
            return -1;
        }

        // then the SubsetElement.conceptCode
        final String conceptCode = this.getConceptCode();
        final Integer intConceptCode = makeIntegerCode(conceptCode);

        final String diffConceptCode = o.getConceptCode();
        final Integer intDiffConceptCode = makeIntegerCode(diffConceptCode);

        if (intConceptCode > intDiffConceptCode) {
            return 1;
        } else if (intConceptCode < intDiffConceptCode) {
            return -1;
        }
        return 0;
    }

    public String getValuesAsString() {
        String valueString = "";
        final Iterator<String> iter = this.valueMap.keySet().iterator();
        while (iter.hasNext()) {
            String tmpValue = "";
            final String key = iter.next();
            tmpValue = this.valueMap.get(key);
            if (iter.hasNext()) {
                valueString = valueString + tmpValue + "\t";
            } else {
                valueString = valueString + tmpValue;
            }
        }

        return valueString;
    }

    public String getHeadersAsString() {
        String headerString = "";
        final Iterator<String> iter = this.valueMap.keySet().iterator();
        while (iter.hasNext()) {
            final String key = iter.next();

            if (iter.hasNext()) {
                headerString = headerString + key + "\t";
            } else {
                headerString = headerString + key;
            }
        }

        return headerString;
    }

    // Assumption is that this is the old element
    public TreeSet<DiffElement> diffSubsetElement(SubsetElement newElement) {
        final TreeSet<DiffElement> diff = new TreeSet<DiffElement>();
        final Iterator<String> oldIter = this.valueMap.keySet().iterator();
        while (oldIter.hasNext()) {
            final String key = oldIter.next();
            if (newElement.valueMap.containsKey(key)) {
                final String newValue = newElement.valueMap.get(key);
                final String oldValue = this.valueMap.get(key);
                if (!oldValue.equals(newValue)) {
                    final DiffElement de = new DiffElement(this);
                    de.setOldValue(oldValue);
                    de.setNewValue(newValue);
                    de.setTypeOfChange("C");
                    de.setElementChanged(key);
                    diff.add(de);
                }
            } else {
                final DiffElement de = new DiffElement(this);
                de.setOldValue(this.valueMap.get(key));
                de.setNewValue("");
                de.setTypeOfChange("C");
                de.setElementChanged(key);
                diff.add(de);
            }
        }

        final Iterator<String> newIter = newElement.valueMap.keySet()
                .iterator();
        while (newIter.hasNext()) {
            final String key = newIter.next();
            if (!this.valueMap.containsKey(key)) {
                final DiffElement de = new DiffElement(this);
                de.setOldValue("");
                de.setNewValue(newElement.valueMap.get(key));
                de.setTypeOfChange("C");
                de.setElementChanged(key);
                diff.add(de);
            }
        }

        return diff;
    }

}
