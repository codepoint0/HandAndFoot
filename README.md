# HandAndFoot

## How to Download

### Download Java 12
You will need to make sure to download Java 12 or above in order to run the application. You can download this here:
https://www.oracle.com/java/technologies/javase/jdk12-archive-downloads.html
You will need to make an account before you can download the file.

### Download The Client
Go here and download the entire folder (click download all files in the top right hand corner of the page).
https://codepoint0.com/index.php/s/FYRxJG5Mfz3NsD4

### Run the JAR
Once you run the Jar, a single person in the party should "Create a game" which they will then get a Room Code and be able to share that with the rest of the group who should "Join the game"

## Hand and Foot Rules

[WIP]

## Work Log

### December 15 - 17, 2020
+ Created basic game logic code
+ Formatted code to work as a server
+ Created game.cpp, card.cpp and associated header files
+ c++

### December 18 - 20, 2020
+ Created basic GUI in Java
+ Created general game setup
+ Created all assets needed for the game

### December 21 - 28, 2020
+ General work on networking
+ Created Controller Server side
+ general fixes to both client and server
+ Opened up Server on AWS

### December 29, 2020
+ Product dropped (Allowed family to play on the AWS server) functionality worked
+ Minor bugs

### Januaray 2nd, 2021
+ Started work both client and server side for the idea of Room Codes
+ Added Code input to client
+ Multithreaded the server

### January 3rd, 2021
+ Finished up work on Room Codes
+ started work on Server protection (don't crash if running into a problem)

## Possible fixes
+ Player needs to be able to say where wilds are played (will need to know about team)
+ Meld score, dirty/clean bonus can be a setting that is changed
+ If > 0 wilds && valid  dirty == true
