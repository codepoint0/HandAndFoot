#ifndef CONTROLLER
#define CONTROLLER

/*
    HAND AND FOOT
    This file is the messanger between the outside world and the game.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: January 3, 2021
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

/*
    A struct to keep track of all client information
*/
struct Client{
    int socket;
};

struct DeadThreadException : public std::exception{
    const char * what() const throw(){
        return "Thread has died while processing, did someone disconnect?";
    }
};

/*
    The part of the server which dictates the game to each player.
    The work done here is done in play.
*/
class Controller{
    private:
    public:

        // The current game's board
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

        /*
            Starts the game and figures out teams, then starts by
            sending the player's their hands, groups, foots ect.
            Each time through the play loop a new player starts playing.
            Directs the entire game.
        */
        void play();

        /* 
            Read from client i
        */
        std::string Read(int i);

        /*
            Send PlayerID the message
        */
        void SendPlayerInfo(int PlayerId, std::string message);
        vector<int> Pairing(vector<int> selected);

        /*
            Takes the player hand and translates it into the protocol
            to be able to send it to the client.
        */
        std::string StringHand(int playerID, vector<Player*> turn);

        /*
            Takes the player groups and translates it into the protocol
            to be able to send it to the client.
        */
        std::string StringGroup(int PlayerID, vector<Player*> turn);

        /*
            Used to determine teams
        */
        vector<int> TurnOrder(vector<int> correctPairs);

        /*
            Before teams are determined use this to send information
            to players and to read from players.
        */
        void PreSendPlayerInfo(int playerID, std::string message);
        std::string PreRead(int i);

        /*
            Used to determine teams
        */
        vector<vector<int>> enumeratedPairings(int k);
        int score(vector<int> pairs, vector<int> selected);
        void AcceptNewClient(Client c);
};

#endif