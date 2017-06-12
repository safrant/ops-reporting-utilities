REM This program will take two NCPDP subset reports, in text format, and compare them to each other.
REM
REM It requires two inputs:
REM -o   The path to the "old" subset report
REM -n   The path to the "new" subset report
REM    
REM This script is specific for the NCPDP.  The only parameters passed in are the location of the old and new files.  The other parameters are already set in the script.
REM 
REM    SubsetDiff.bat -n "D:\data\NCPDP\NCPDP 2014-06-27.txt" -o "D:\data\NCPDP\NCPDP 2014-03-28.txt"
 
java -Xmx1000m -cp ".\FDASubsetDiff.jar" gov.nih.nci.evs.subsetdiff.Diff -d "./NCPDP_DiffReport.txt" -s "NCIt Subset Code" -c "NCIt Code" %*