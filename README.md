PomodoroBox
===========

    Motto #1: "All your pomodoros are belong to box"
    Motto #2: "I know what I did last summer"

The five killer features of PomodoroBox are four:
- Time and log pomodoros from all your *Mac*, and *Android* devices
- *Query and visualize* your past pomodoros
- Data is stored in a *plain text file in your Dropbox*. No proprietary format. All your pomodoros are belong to you.
- It integrates nicely with the excellent Pomodoro Desktop for Mac app by Ugo Landini

## Installation for OS X

You have to follow these steps for each of your Macs on which you want to install Pomodoro Box.

1. Download and install [Pomodoro Desktop](http://mac.majorgeeks.com/files/details/pomodoro_desktop.html) by Ugo Landini

2. Open the Preferences of Pomodoro Desktop. Go to the Script tab and paste the following script in the box for the End event (you need to paste from the mouse as the keyboard shortcut does not work!!!):

    ```
    do shell script "echo  `date '+%Y/%m/%d %H:%M'`, $pomodoroName >> ~/Dropbox/Apps/PomodoroBox/box.txt" 
    ```

3. Execute the following two commands one after the other in Terminal:

    ```
    wget -N -P ~/Dropbox/Apps/PomodoroBox/ https://raw.github.com/mircealungu/PomodoroBox/master/script/box.sh
    ```
    
    ```
    chmod +x ~/Dropbox/Apps/PomodoroBox/box.sh
    ```

4. Create an alias to the script by adding the following line in your .profile. I like to call it `box` but you can call your command any way you like:

    ```
    alias box='~/Dropbox/Apps/PomodoroBox/box.sh'
    ```

## Installation for Android
Coming soon...

## Testing your installation

Now you should be able to start using Pomodoro Desktop and every pomodoro that you finish will be logged into your Dropbox. 
Or if you already spent one full pomodoro during the instalation and you want to log it, you can do that with:

    box done "Installing PomodoroBox, self-improvement"

Once you've done a few you can see them by running:

    box list
    
For more commands, see the following section.


## Using Pomodoro Box

### Statistics
To see all the pomodoros you've ever done
   
    box list

To see how did you spend your time on the different projects
   
    box stats
   
To see how did you spend your time on the different projects today
   
    box today
    
### Logging
If you want to edit some of the pomodoros you've already logged, use
   
    box ed
    
If you want to add a pomodoro that you just finished and you forgot to log with Pomodoro Desktop, use
    
    box done "name of task, project"

    

