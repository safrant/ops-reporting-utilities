This program will take two FDA subset reports, in text format, and compare them to each other.

It requires five inputs:
-o   The path to the "old" subset report
-n   The path to the "new" subset report
-d   The path to store the diff report
-s   The column name where the subset code is stored
-c   The column name where the NCIt code is stored
   
For example, if we wanted to compare the current NCPDP report to one a few months ago I would first need to look at the report and determine the column names for the subset and NCIt code. For NCPDP these are "NCIt Subset Code" and "NCIt Code."  Note that the names are case sensitive.  I could then build the request like this
 
 On Windows  
   SubsetDiff.bat -n "D:\data\NCPDP\NCPDP 2014-06-27.txt" -o "D:\data\NCPDP\NCPDP 2014-03-28.txt" -d "D:\data\NCPDP\NCPDP_DiffReport.txt" -s "NCIt Subset Code" -c "NCIt Code"
   
 On Linux
   sh SubsetDiff.sh -n "./NCPDP 2014-06-27.txt" -o "./NCPDP 2014-03-28.txt" -d "./NCPDP_DiffReport.txt" -s "NCIt Subset Code" -c "NCIt Code"
     
Double quotes are required for any input with spaces.  It's generally a good idea to use double quotes for all inputs.


To make things easier you can create custom scripts for each subset that has all the "constant" parameters already entered.  See SubsetDiffNCPDP.sh and SubsetDiffNCPDP.bat for examples.