#/bin/sh

LOGFILE="/Users/mircea/Dropbox/logs/pomodoros.txt"
if [[ $# -ne 1 ]]; then
	echo "Usage: [pom \"<task, tags>\"] | [li[st] | ed[it] | st[at] | to[day] ]>"
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



