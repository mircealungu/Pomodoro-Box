#/bin/sh

LOGFILE="/Users/mircea/Dropbox/logs/pomodoros.txt"
USAGE="Usage: [pom \"<task, tags>\"] | -d <date> | [li[st] | ed[it] | st[at] | to[day] ]>"

if [ "$1" = "-d" ]; then
	if [[ $# -eq 2 ]]; then
		cat $LOGFILE | grep $2 | cut -f3 -d "," | sort  | uniq -c | sort -r
		exit
	else
		echo $USAGE
		exit
	fi
	echo $USAGE
	exit
fi

if [[ $# -ne 1 ]]; then
	echo $USAGE
	exit 1
fi

if [ "$1" = "li" -o "$1" = "list" ]; then
	#statements
	cat $LOGFILE
	exit
fi

if [ "$1" = "today" -o "$1" = "to" ]; then
        cat $LOGFILE | grep "`date '+%m/%d/%Y'`" | cut -f3 -d "," | sort  | uniq -c | sort -r
        exit
fi
	
if [ "$1" = "st" -o "$1" = "stat" -o "$1" = "stats" ]; then
	cat $LOGFILE | cut -f3 -d "," | sort  | uniq -c | sort -r
	exit
fi

if [ "$1" = "ed" -o "$1" = "edit" ]; then
	#statements
	$EDITOR $LOGFILE
	exit
fi

echo  `date '+%m/%d/%Y %H:%M' `, $1 >> $LOGFILE



