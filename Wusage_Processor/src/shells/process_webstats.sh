#!/bin/bash

if [ $# -gt 0 ]; then
    echo "Your command line contains $# arguments"
else
    echo "Your command line contains no arguments. Must provide username, password and server (ie:ncim, lexevsapi60)."
fi

ldate=$(date +%F)

username=$1
password=$2
server=$3


echo retrieving page...
if [ $# -gt 3 ]; then
      month=$4
      year=$5
      filename=stripped-stats-$server-$month-$year.txt
   sh ./getaws_month.sh $filename $username $password $server $month $year
else
   filename=stripped-stats-$server-$ldate.txt
   sh ./getaws.sh $filename $username $password $server
fi


echo grepping...
grep -v -f no-bots.txt $filename > no-bot-$filename
grep .edu no-bot-$filename > edu-stats-$filename 
grep .net no-bot-$filename > net-stats-$filename
grep .com no-bot-$filename | grep -v ".comcast" > com-stats-$filename
grep .org no-bot-$filename > org-stats-$filename
grep .gov no-bot-$filename > gov-stats-$filename
grep -v -f no-domain.txt no-bot-$filename > no-dom-stats-$filename

echo processing edu domains
sh ./process-edu-domains.sh edu-stats-$filename
echo processing org domains
sh ./process-org-domains.sh org-stats-$filename
echo processing gov domains
sh ./process-gov-domains.sh gov-stats-$filename
echo processing com domains
sh ./process-com-domains.sh com-stats-$filename
echo processing net domains
sh ./process-net-domains.sh net-stats-$filename