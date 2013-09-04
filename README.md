PomodoroBox
===========

    Motto #1: "All your pomodoros are belong to box"
    Motto #2: "I know what I did last summer"


This repo contains a script that integrates with Pomodoro Desktop and logs all your pomodoros in Dropbox. 
It also contains an Android application that does the same thing, but that is still work in progress. 

# Installation for OS X

0. Download and install [Pomodoro Desktop](http://mac.majorgeeks.com/files/details/pomodoro_desktop.html) by Ugo Landini

1. Open the Preferences of Pomodoro Desktop. Go to the Script tab and set the following script for the End event:

    ```
    do shell script "echo  `date '+%m/%d/%Y %H:%M'`, $pomodoroName >> ~/Dropbox/Apps/PomodoroBox/box.txt" 
    ```

2. Execute the following two commands one after the other in Terminal:

    ```
    wget -N -P ~/Dropbox/Apps/PomodoroBox/ https://raw.github.com/mircealungu/PomodoroBox/master/osx/box.sh
    ```
    
    ```
    chmod +x ~/Dropbox/Apps/PomodoroBox/box.sh
    ```

3. Create an alias to the script by adding the following line in your .profile. I like to call it `box` but you can call your command any way you like:

    alias box='~/Dropbox/Apps/PomodoroBox/box.sh'
    
*. Repeat the steps above for all the machines you have.


# Testing your installation

Now you should be able to start using Pomodoro Desktop and every pomodoro that you finish will be logged into your Dropbox. 
Once you've done a few you can see them by running:

    box list
    
Or if you already spent one full pomodoro during the instalation and you want to log it, you can do that with:

    box done "Installing PomodoroBox, self-improvement"
    

