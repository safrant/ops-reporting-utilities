/* Rob Wynne, MSC
 * 
 * This program will display a collapsible hierarchy
 * based on the input flat file.  GIGO (no checking for cycles, etc).
 * 
 * Version 1.0: November 8, 2013 
 */

package gov.nih.nci.evs.hierarchy;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

public class DisplayHierarchy extends JFrame implements TreeSelectionListener {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new DisplayHierarchy(args);
	}
	
	private JTree tree;
	private JTextField currentSelectionField;
	private String rootIdentifier;
	private String rootLabel;
	private HashMap<String,ArrayList<String>> parents = new HashMap<String,ArrayList<String>>();
	private HashMap<String, String> pts = new HashMap<String, String>();
	
	public DisplayHierarchy(String[] args) {
		super("Hierarchy Viewer");		
		if( args.length == 3 ) {
			readFlatFile(args[0]);
			rootIdentifier = args[1];
			rootLabel = args[2];
		}
		else {
			System.out.println("Exiting, usage is: HierarchyViewer [FlatFile.txt] [RootID] [RootLabel]");
			System.exit(0);
		}
		WindowUtilities.setNativeLookAndFeel();
		addWindowListener(new ExitListener());
		Container content = getContentPane();
		MyNode root = new MyNode(rootLabel);
		setChildren(rootIdentifier, root);
		tree = new JTree(root);
		tree.addTreeSelectionListener(this);
		content.add(new JScrollPane(tree), BorderLayout.CENTER);
		currentSelectionField = new JTextField("Current Selection: NONE");
		content.add(currentSelectionField, BorderLayout.SOUTH);
		setSize(500, 400);
		setVisible(true);		
	}
	
	public void setChildren(String id, MyNode node) {
		if( parents.containsKey(id) ) {
			for(String child : parents.get(id)) {
				MyNode childNode = new MyNode(pts.get(child));
				node.add(childNode);
				setChildren(child, childNode);				
			}
		}
	}

	public void valueChanged(TreeSelectionEvent event) {
		currentSelectionField.setText("Current Selection: " + tree.getLastSelectedPathComponent().toString());
	}	
	
	public void readFlatFile(String filename) {
		FileReader configFile = null;
		BufferedReader buff = null;
		try {
			configFile = new FileReader(filename);
			buff = new BufferedReader(configFile);
			boolean eof = false;
			while (!eof) {
				String line = buff.readLine();
				if (line == null) {
					eof = true;
				} else {
					String[] tokens = line.split("\t");
					if( tokens.length == 3 ) {
						String parent = tokens[2];
						String pt = tokens[1];
						String concept = tokens[0];
						if( parents.containsKey(parent) ) {
							ArrayList<String> list = parents.get(parent);
							if(!list.contains(concept)) {
								list.add(concept);
								//Collections.sort(list);								
							}
							parents.put(parent, list);
						}
						else {
							ArrayList<String> list = new ArrayList<String>();
							list.add(concept);
							parents.put(parent, list);
						}
						pts.put(concept, pt);
					}
					else {
						System.out.println("Line doesn't have 3 elements: " + line + "\n");
						System.exit(0);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Closing the streams
			try {
				buff.close();
				configFile.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	
	
}
