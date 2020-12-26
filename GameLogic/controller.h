#ifndef CONTROLLER
#define CONTROLLER

/*
    HAND AND FOOT
    This file is the messanger between the outside world and the game.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: controller.h
*/

#include <sstream>
#include <unistd.h> 
#include <stdio.h> 
#include <sys/socket.h> 
#include <stdlib.h> 
#include <netinet/in.h> 
#include <string.h> 
#include <string>
#include <cstdlib>
#include <thread>
#include <mutex> 
#include <iostream>
#include <exception>
#define PORT 8080 

#include "game.h"

struct Client{
    int socket;
};

class Controller{
    private:
        BoardState* b;

    public:
        Controller(int teams);
        ~Controller();

        // Determines the entire game.
        void play();
        std::string Read(int i);
        int SetUpServer();
        void CountDown(int threadid);
        void ListenForNewClients();
        void SendPlayerInfo(int PlayerId, std::string message);
        void SendAllInfo(std::string message);
        std::string StringHand(int playerID, vector<Player*> turn);
        std::string StringGroup(int PlayerID, vector<Player*> turn);
};

#endif