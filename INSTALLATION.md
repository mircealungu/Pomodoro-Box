# Installation for OS X:

1. Download and install [Pomodoro Desktop](http://mac.majorgeeks.com/files/details/pomodoro_desktop.html) by Ugo Landini
2. Go to the preferences of Pomodoro Desktop in the Script tab, set the following script for the End event:

    ```
    do shell script "echo  `date '+%m/%d/%Y %H:%M'`, $pomodoroName >> ~/Dropbox/Apps/PomodoroBox/box.txt" 
    ```

3. Get the script by executing the following command in your command line:

    ```
    wget -N -P ~/Dropbox/Apps/PomodoroBox/ https://raw.github.com/mircealungu/PomodoroBox/master/osx/box.sh
    ```
    
4. Make the script executable by running
    ```
    chmod +x ~/Dropbox/Apps/PomodoroBox/box.sh
    ```

5. Create an alias to the script by adding the following line in your .profile 

    alias box='~/Dropbox/Apps/PomodoroBox/box.sh'


# Testing your installation

Now you should be able to run the following command

    box done "just installed pomodorobox, self-development"
    
which will log one pomodoro that you can visualize with

    box list
