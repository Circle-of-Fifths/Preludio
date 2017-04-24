# Preludio
Project Preludio 2017 is an interactive rhythm game that we hope will help many to learn the fundamentals of music theory in a fun way. Create your own levels and share with friends. Anyone can join in the fun!!!
We hope you enjoy it as much as we did making it.

# Version 1.0 Release Notes:
## New Features
### Concert Mode
Select a .midi file from your computer, and the computer will play the file for you and show you the notes being played using a virtual piano keyboard.

### Play Mode
Choose a .midi file from your computer and play a procedurally generated level by following along in the song and pressing keys on your computer keyboard to play the notes. Afterwards you will receive a score and rating which you can choose to save.

### Free Play Mode
Play a virtual piano keyboard using either your mouse or computer keyboard to practice what you have learned.

### Lessons Mode
Choose a lesson provided by the game to learn basic music theory concepts such as intervals and chords. Each lesson comes with a written explanation and a chance to either preview the lesson (the computer plays it) or play the lesson (you try playing along).

### Records
View game records that were saved in either Play mode or Lessons mode.

### Settings
Change in-game settings such as volume (music and sound effects) and controls for the computer keyboard.

### Registration and Login
Register with the companion website after being redirected from the title screen of the game. Login from the title screen of the game to have scores sent from your computer to the website.

### Companion Website
View information about the game and download it. Also view the global high scores list or, after logging in, your own personal scores.

See the [corresponding github repository](https://github.com/Circle-of-Fifths/Preludio-website) for the source code.

## Known Bugs
### Issue with missing note sprites
In Play mode and Lesson mode, when two notes that have the same name (example 2 C notes play one after the other), the second will not appear if the first is still playing. This is possibly due to the speed of the note sprite translation and the fact that each note has only one sprite available at a time.

### No security for save file
The .csv file that saves scores and other user data (username, song name, rating, and time stamp) is not encrypted or stored in a way that prevents the user from manually modifying it. This could potentially be used to cheat and record a better score than the one actually received.

## Missing Functionality
The Circle of Fifths team was not able to implement these features due to time constraints.

### Password Recovery
Implementing a method of password recovery was a functionality listed in the customer agreement document.

### In-Game News
The customer agreement document also detailed that the game would support displaying news updates taken from the website.

# Install Instructions:
## Pre-requisites
You must have the Java 8 Java Development Kit (JDK) installed on your computer in order to be able to run .jar files. This is because Preludio takes advantage of JavaFX libraries, which are not included in the base Java Runtime Environment (JRE).

You can download the JDK from [Oracle’s website.](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Please consult Oracle’s website on [specific installation instructions for your operating system.](http://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)

Windows users will need follow extra steps in order to get their PATH variable set up for Java to be recognized by the system. See [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/windows_jdk_install.html#CHDEBCCJ) for more details.

## Downloading and Running Preludio
Go to the Download page and download the .zip file. The .zip file contains the necessary game files, compressed into one file called Preludio.jar, and the game folder structure is already created so that the game can save and do other necessary operations. The sample .midi and lessons files that come with the game are also located within the .zip file. After downloading, extract the .zip file to whatever location you want on your computer. Whenever you want to play, just run the Preludio.jar file by double clicking it, and the game will start. No compilation is required, and the location of the .midi files does not matter, as they can be accessed in-game regardless of their location. You can also create a shortcut that references Preludio.jar to start it from the desktop if you don’t place the .jar file on the desktop.

[Download](http://ec2-54-214-225-63.us-west-2.compute.amazonaws.com/download.html)

## Troubleshooting
Were the companion website to be down, the .jar could also be downloaded [from this repository](https://github.com/Circle-of-Fifths/Preludio/blob/master/out/artifacts/Preludio_jar/Preludio.jar) directly.

Since the game itself does not require an installer, any problems with installation are likely to be with the JDK, in which case the previously linked Oracle documentation should be consulted.
