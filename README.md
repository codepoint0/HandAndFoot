# HandAndFoot

## Work Log
### December 15, 2020
+ Created Game heard and Card header to handle game information
+ Created card class to create the logic behind the card hearder
+ Two exceptions created to throw when pile and wreath are empty
+ Started on Server prototyping*
+ Created a server class that allows for multiple connections and keeps track of those connections
+ Multithreading on server to allow for server to keep listening while still doing calculations
+ Client can connect and send messages to server
+ *This server/client a prototype and proof of concept, more work needs to be done

### December 17, 2020
+ Created game.cpp
+ changed functions in card.h from private to public

## Possible fixes
+ Player needs to be able to say where wilds are played (will need to know about team)
+ Meld score, dirty/clean bonus can be a setting that is changed

+ If > 0 wilds && valid  dirty == true
