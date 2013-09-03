#!/bin/bash

POMODOROBOXDIR=~/Dropbox/Apps/PomodoroBox/

#  try to use wget if possible
if [[ `which wget 2> /dev/null` ]]; then
	DOWNLOAD_TO="wget --quiet -N -P ";
	DOWNLOAD="$DOWNLOAD_TO-";
else
	echo "Please install curl or wget on your machine";
	exit 1
fi

mkdir -p $POMODOROBOXDIR
$DOWNLOAD_TO $POMODOROBOXDIR https://raw.github.com/mircealungu/PomodoroBox/master/osx/pom.sh 
$DOWNLOAD_TO $POMODOROBOXDIR https://raw.github.com/mircealungu/PomodoroBox/master/osx/log-pomodoro.scpt

