#!/bin/bash

if [ $# -gt 0 ]; then
    echo "Your command line contains $# arguments"
else
    echo "Your command line contains no arguments. Must provide username, password and server (ie:ncim, lexevsapi60)."
fi

ldate=$(date +%F)

username=$1
password=$2

if [ $# -gt 2 ]; then
      month=$3
      year=$4
   sh ./process_webstats.sh $username $password ncim $month $year
   sh ./process_webstats.sh $username $password ncit $month $year
   sh ./process_webstats.sh $username $password nciterms $month $year
else
   ./process_webstats.sh $username $password ncim
   ./process_webstats.sh $username $password ncit
   ./process_webstats.sh $username $password nciterms
fi