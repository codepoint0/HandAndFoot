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

struct DeadThreadException : public std::exception{
    const char * what() const throw(){
        return "";
    }
};

class Controller{
    private:
        

    public:
        BoardState* b;
        int code;
        char* message;
        char buffer[4096];
        int new_socket;
        int valread;
        int server_fd;
        struct sockaddr_in address; 
        int opt = 1; 
        int addrlen = sizeof(address);
        int rc;
        int ThreadID = 0;
        vector<Client> clients;
        int players;
        bool wait;
        vector<std::string> usernames;
        vector<int> controlled;

        Controller(int teams, int code, Client c);
        Controller(const Controller& ctr);
        ~Controller();

        // Determines the entire game.
        void play();
        std::string Read(int i);
        void CountDown(int threadid);
        void SendPlayerInfo(int PlayerId, std::string message);
        vector<int> Pairing(vector<int> selected);
        std::string StringHand(int playerID, vector<Player*> turn);
        std::string StringGroup(int PlayerID, vector<Player*> turn);
        vector<int> TurnOrder(vector<int> correctPairs);
        void PreSendPlayerInfo(int playerID, std::string message);
        std::string PreRead(int i);
        vector<vector<int>> enumeratedPairings(int k);
        int score(vector<int> pairs, vector<int> selected);
        void AcceptNewClient(Client c);
};

#endif