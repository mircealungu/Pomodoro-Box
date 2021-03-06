#!/bin/bash

# all possible commands
# pom do "testing" (will sleep for a pomodoro, then alert you)
# pom done "testing"

LOGFILE=~/Dropbox/Apps/PomodoroBox/box.txt
LOGFILEDIR=~/Dropbox/Apps/PomodoroBox/
USAGE="usage: pom [do \"<task, tags>\"] [done \"<task, tags>\"] [li[st]|stat|ed[it]|t[oday]] [grep \"<regex>\"]"
DATEANDTIME=`date '+%Y/%m/%d %H:%M'`
DATE=`date '+%Y/%m/%d'`


clean_up() {

	if [ `ls -l $LOGFILEDIR/box*.txt | wc -l` -gt 1 ]; then
		cat $LOGFILEDIR/box*.txt | sort | uniq > $LOGFILE
		rm -rf $LOGFILEDIR/box*conflicted*.txt
		#echo "Consolidated" `cat $LOGFILE | wc -l` "pomodoros..."
	fi

}
progress() {
	i=0

	echo -ne "....,....,....,....,....,\r"
	while [ $i -lt 25 ]
	do
		let "i+=1"
		sleep 60
		echo -ne "+"
	done

	say "Well done"
}

# always check if cleanup is needed, and if so, do it
clean_up

if [[ $# -eq 0 ]]; then
	echo $USAGE
	exit
fi

# One argument commands
if [[ $# -eq 1 ]]; then
	case "$1" in
		# pom list
		"list" | "li" )
		cat $LOGFILE
		;;

		# pom stat
		"stat" ) 
		cat $LOGFILE | cut -f3 -d "," | sort  | uniq -c | sort -r
		;;

		# pom ed
		"edit" | "ed" ) 
		$EDITOR $LOGFILE
		;;

		# pom t
		"today" | "t" )
        cat $LOGFILE | grep $DATE | cut -f3 -d "," | sort  | uniq -c | sort -r
        ;;

    	* )
		echo $USAGE
		exit 1
		;;
    esac
fi

# Two argument commands
if [[ $# -eq 2 ]]; then
	case "$1" in
		# pom st "task, project"
		"do" )
			echo "Started: " $2
			progress
			echo  $DATEANDTIME, $2 >> $LOGFILE
			;;

		# pom done "task, project"
		"done" )
			echo  $DATEANDTIME, $2 >> $LOGFILE
			;;
			
		# pom grep "Red" -- stats for all the tasks which contain Red
		# pom grep "^05/" -- will return stats for the pomodoros in June 
		"grep" )
			cat $LOGFILE | grep $2 | cut -f3 -d "," | sort  | uniq -c | sort -r
			;;
		* )
			echo $USAGE
			exit 1
			;;
	esac
fi

if [[ $# -gt 2 ]]; then
	echo $USAGE
	exit
fi





