README - GenerateNICHD.exe (12/5/14)
====================================

This program will allow you to generate an OWL file based on the NICHD hierarchy
and attributes.  First, you will need to export a report from the Protege environment.
To do this, navigate to the Lucene Query Tab.  Search for all concepts with a property present NICHD_Hierarchy_Term.  
The Search Results will be the classes the report will be generated from.  Once you have the results, click the 
'Export Slot values to file' button in the top right corner.

You will then be prompted with the 'Export configuration' panel.  Choose a location to save the
'Exported file' (it is best to save in this directory as a .txt file).  Next, choose the 'Slots to export'.
The following order of slots is necessary.

1) code
2) NICHD_Hierarchy_Term
3) FULL_SYN
4) ALT_DEFINITION
5) DEFINITION
6) Has_NICHD_Parent

The 'Slot delimiter' must be a tab.  If this is not set to tab in your protege, type (without double quotes) "\t"
in the Slot delimiter box.  The 'Slot values delimiter' must be a pipe character, type (without double quotes) "|".

Uncheck options 'Export browser text (instead of name)', 'Export superclasses', and
'Export additional text as last line in the file'.

Click 'OK'.  The report may take a few minutes to generate (the red box will show during this time).
You will get a popup 'Export successful' with the location to the file when it is finished.

Now that the report is generated, it will be used as the input to GenerateNICHD.  Navigate to the directory where
your package was extracted and ensure the report generated is in this directory.  Also ensure the header.txt file
exists in this directory as it is used by the program.  Run the program as such:

C:\GenerateNICHD> GenerateNICHD.exe [report file] [name of saved file]

C:\GenerateNICHD> GenerateNICHD.exe nichd_report.txt NICHD.owl

The program will run quickly and save the output as the second argument you specified.

The output OWL may be opened in Protege for review.