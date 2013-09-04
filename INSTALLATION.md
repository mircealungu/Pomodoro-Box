# Installation for OS X

1. Download and install [Pomodoro Desktop](http://mac.majorgeeks.com/files/details/pomodoro_desktop.html) by Ugo Landini
2. Go to the preferences of Pomodoro Desktop in the Script tab, set the following script for the End event:

    ```
    do shell script "echo  `date '+%m/%d/%Y %H:%M'`, $pomodoroName >> ~/Dropbox/Apps/PomodoroBox/box.txt" 
    ```

3. Execute the following two commands one after the other in Terminal:

    ```
    wget -N -P ~/Dropbox/Apps/PomodoroBox/ https://raw.github.com/mircealungu/PomodoroBox/master/osx/box.sh
    ```
    
    ```
    chmod +x ~/Dropbox/Apps/PomodoroBox/box.sh
    ```

4. Create an alias to the script by adding the following line in your .profile. I like to call it pb but you can call your command any way you like:

    alias pb='~/Dropbox/Apps/PomodoroBox/box.sh'


# Testing your installation

Now you should be able to run the following command

    box done "just installed pomodorobox, self-development"
    
which will log one pomodoro that you can visualize with

    box list
