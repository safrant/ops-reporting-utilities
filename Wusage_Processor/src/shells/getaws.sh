#!/bin/bash

# password $ and @ characters need to be escaped, probably others too.

myusr=$2
mypwd=$3
myserver=$4
ldate=$(date +%F)

search1='https://sitestats.nci.nih.gov/awstats/awstats.pl?month=all&year=2012&config='
search2='.nci.nih.gov&framename=mainright&output=allhosts'

searchString=$search1$myserver$search2

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



