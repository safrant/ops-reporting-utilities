#This program will take two NCPDP subset reports, in text format, and compare them to each other.
#
#It requires two inputs:
#-o   The path to the "old" subset report
#-n   The path to the "new" subset report
#
#  
#This script is specific for the NCPDP.  The only parameters passed in are the location of the old and new files.  The other parameters are already set in the script.
# 
#   sh SubsetDiff.sh -n "./NCPDP 2014-06-27.txt" -o "./NCPDP 2014-03-28.txt"


java -Xmx1000m -cp "./FDASubsetDiff.jar" gov.nih.nci.evs.subsetdiff.Diff -d "./NCPDP_DiffReport.txt" -s "NCIt Subset Code" -c "NCIt Code" "$@"