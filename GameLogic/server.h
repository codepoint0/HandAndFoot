#ifndef SERVER
#define SERVER

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

#include "controller.h"

struct CtrCodePair{
    int index;
    int code;
}

class Server{
    private:
    public:
    vector<Client> clients;
    vector<Controller> games;
    vector<int> codes;
    int runningGames;

    void SetUpServer();
    void ListenForNewClients();
};

#endif