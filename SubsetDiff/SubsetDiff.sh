#This program will take two FDA subset reports, in text format, and compare them to each other.
#
#It requires five inputs:
#-o   The path to the "old" subset report
#-n   The path to the "new" subset report
#-d   The path to store the diff report
#-s   The column name where the subset code is stored
#-c   The column name where the NCIt code is stored
   
#For example, if we wanted to compare the current NCPDP report to one a few months ago I would first need to look at 
#the report and determine the column names for the subset and NCIt code. For NCPDP these are "NCIt Subset Code" and 
#"NCIt Code."  Note that the names are case sensitive.  I could then build the request like this
# 
#   sh SubsetDiff.sh -n "./NCPDP 2014-06-27.txt" -o "./NCPDP 2014-03-28.txt" -d "./NCPDP_DiffReport.txt" -s "NCIt Subset Code" -c "NCIt Code"


java -Xmx1000m -cp "./FDASubsetDiff.jar" gov.nih.nci.evs.subsetdiff.Diff "$@"