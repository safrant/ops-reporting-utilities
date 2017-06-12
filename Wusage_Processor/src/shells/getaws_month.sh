#!/bin/bash
# https://sitestats.nci.nih.gov/awstats/awstats.pl?month=05&year=2012&output=allhosts&config=lexevsapi51.nci.nih.gov&framename=mainright
# password $ and @ characters need to be escaped, probably others too.

myusr=$2
mypwd=$3
myserver=$4
mymonth=$5
myyear=$6
ldate=$mymonth-$myyear

echo mymonth $mymonth
echo myyear $myyear


search1='https://sitestats.nci.nih.gov/awstats/awstats.pl?month='
search2='&year='
search3='&output=allhosts&config='
search4='.nci.nih.gov&framename=mainright'

searchString=$search1$mymonth$search2$myyear$search3$myserver$search4

echo searchString $searchString


wget --secure-protocol=auto \
--no-check-certificate \
--random-file=seed.txt \
--http-user=$myusr \
--http-password=$mypwd \
-O temp-$myserver-$ldate.htm \
 $searchString

grep "<tr><td class=\"aws\">" temp-$myserver-$ldate.htm > temp2-$myserver-$ldate.htm
sed \
	-e 's/<tr><td class=\"aws\">/	/' \
	-e 's/<\/td><td>/	/g' \
	-e 's/<span style=\"color: #666688\">/	/g' \
	-e 's/<\/span>/	/g' \
	-e 's/<\/td><\/tr>/	/' < temp2-$myserver-$ldate.htm > $1



