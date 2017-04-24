# Preludio
Project Preludio 2017 is an interactive rhythm game that we hope will help many to learn the fundementals of music theory in a fun way. Create your own levels and share with friends. Anyone can join in the fun!!!
We hope you enjoy it as much as we did making it.

## Release Notes:

**New Software Feature:** Concert Mode  
Select a .midi file from your computer and have the computer play the file for you and show you the notes being played.

**New Software Feature:** Play Mode  
Choose a .midi file from your computer and play a procedurally generated level by following along in the song and pressing keys on your keyboard to play the notes. Afterwards you will receive a score and rating which you can choose to save.

**New Software Feature:** Free Play Mode  
Play a virtual piano keyboard using either your mouse or computer keyboard to practice what you have learned.

**New Software Feature:** Lessons Mode  
Choose a lesson provided by the game to learn basic music theory concepts such as intervals and chords. Each lesson comes with a written explanation, and a chance to either preview the lesson (the computer plays it) or play the lesson (you try playing along).

**New Software Feature:** Records  
View game records that were saved in either Play mode or Lessons mode

**New Software Feature:** Settings  
Change in game settings such as volume (music and sound effects) and controls for the computer keyboard

**New Software Feature:** Registration and Login  
Register with the companion website from the title screen of the game. Login from the title screen of the game to have scores updated from your computer to the website.

**Known Bug:** Issue with same note sprite appearing  
In Play mode and Lesson mode, when two notes that have the same name (example 2 C notes play one after the other), the second doesn’t appear. This is possibly due to the speed of the note sprite translation and that each note has only one sprite available at a time.

**Known Bug:** No security on save file  
The .csv file that saves scores and other user data (username, song name, rating, and time stamp) does not have any security to it. There is no encryption and decryption or any other method to prevent the user to modify the file before opening the game. This could potentially be used to cheat and put a better score than they actually received.

**Missing Functionality:** Gamepad and Midi Device Support  
In the original agreement document, the game was supposed to have support for external controllers to play the Play and Lessons modes. The Circle of Fifths team was not able to implement such features before the proposed date as stated in the agreement form.

**Missing Functionality:** Informational Loading Screens  
In the original agreement document, the game was supposed to have informational loading screens to teach extra topics on music history while the game loaded. The Circle of Fifths team was not able to include this feature, partly because it was not necessary as the game loads quickly (~ 1 to 2 seconds) going from one screen to another.

## Install Instructions:

Go to the Download page and download the .zip file. The .zip file contains the necessary game files, compressed into one file called Preludio.jar, and the game folder structure is already created so the game can save and do other necessary operations. The sample .midi and other files that come with the game are also located within the .zip file. After downloading, extract the .zip file to whatever location you want on your computer. Whenever you want to play, just run the Preludio.jar file by double clicking it and the game will start. You can also create a shortcut that references  Preludio.jar to start from the desktop if you don’t place the game files on the desktop.  
[Download](http://ec2-54-214-225-63.us-west-2.compute.amazonaws.com/download.html)

**Please Note**  
You must have the Java 8 JDK (Java Development Kit) installed on your computer so that your computer knows how to run the Preludio.jar file. This is because Preludio takes advantage of JavaFX libraries, which are not included in the base JRE runtime.
You can download the JDK from [Oracle’s website.](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
Please consult Oracle’s website on [specific installation instructions for your operating system.](http://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html)
Windows users will need follow extra steps in order to get their PATH variable set up for Java to be recognized by the system. See [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/windows_jdk_install.html#CHDEBCCJ) for more details.