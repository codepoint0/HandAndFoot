#ifndef SERVER
#define SERVER

/*
    HAND AND FOOT
    This file is the messanger between the outside world and the game.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 28, 2020
    Modified: January 3, 2021
    File: server.h
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
#include <algorithm>
#define PORT 8080 

#include "controller.h"

class Server{
    private:
    public:
    char buffer[4096];
    int new_socket;
    int valread;
    int server_fd;
    struct sockaddr_in address; 
    int opt = 1; 
    int addrlen = sizeof(address);
    int rc;
    vector<Client> clients;
    vector<Controller*> games;
    vector<int> codes;
    int runningGames;
    thread thds[100];
    bool exists[100];
    
    /*
        Clean up all the lose threads
    */
    ~Server();

    /*
        Set up the listeners on a given port and prepare to
        multithread.
    */
    void SetUpServer();

    /*
        Constantly listen for new clients, once a client
        connects determine if the user is trying to create
        a new game or join a game and then direct the client
        accordingly. 
    */
    void ListenForNewClients();

    /*
        Close any and all threads which have died in their
        process
    */
    void CloseThreads();
};

#endif