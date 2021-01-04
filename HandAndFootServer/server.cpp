/*
    The entry point of the server. Used to start, listen
    for connections and start games.

    Created by: Joshua Speckman and Tyler K. Gordon
    Date: December 28, 2020
    Modified: January 3, 2021
    File: server.cpp
 */ 
#include "server.h"
#include <vector>


int main(int argc, char const *argv[])
{
    Server s;
    s.SetUpServer();
    s.runningGames = 0;
    while (true)
    {
    }
}

Server::~Server(){
    for(int i = 0; i < games.size(); i++){
        delete games[i];
    }
}

void Server::SetUpServer(){

    // Start the server and set default values
    buffer[4096] = {0}; 
    for(int i = 0; i < 100; i++){
        exists[i] = false;
    }

    // Creating socket file descriptor 
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) 
    { 
        perror("socket failed"); 
        exit(EXIT_FAILURE); 
    } 
       
    // Forcefully attaching socket to the port 8080 
    if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, 
                                                  &opt, sizeof(opt))) 
    { 
        perror("setsockopt"); 
        exit(EXIT_FAILURE); 
    } 
    address.sin_family = AF_INET; 
    address.sin_addr.s_addr = INADDR_ANY; 
    address.sin_port = htons( PORT ); 
       
    // Forcefully attaching socket to the port 8080 
    if (bind(server_fd, (struct sockaddr *)&address,  
                                 sizeof(address))<0) 
    { 
        perror("bind failed"); 
        exit(EXIT_FAILURE); 
    }
    ListenForNewClients();
}

void Server::CloseThreads(){

    // Go through each of the threads and find ones that are dead
    // and clean them up.
    for(int i = 0; i < 100; i++){
        if(thds[i].joinable()){
            thds[i].join();
            exists[i] = false;
        }
    }
}

void Server::ListenForNewClients(){
    srand(time(NULL));
    thread s = std::thread(&Server::CloseThreads, this);
    s.detach();

    // Constantly listen for new clients
    while(true){
        Client c;
        if (listen(server_fd, 3) < 0) 
        { 
            perror("listen"); 
            exit(EXIT_FAILURE); 
        } 
        if ((c.socket = accept(server_fd, (struct sockaddr *)&address,  
                        (socklen_t*)&addrlen))<0) 
        { 
            perror("accept"); 
            exit(EXIT_FAILURE);
        }

        valread = read(c.socket, buffer, sizeof(buffer)); 
        std::stringstream input(buffer);
        int codeOrNumOfPlayers;
        input >> codeOrNumOfPlayers;

        if(codeOrNumOfPlayers == 4 || codeOrNumOfPlayers == 6 || codeOrNumOfPlayers == 8){
            int code = (rand() % 9000) + 1000;

            // Find a code that has not been used yet
            while(find(codes.begin(), codes.end(), code) != codes.end()){
               code = (rand() % 10000) + 999;
            }
            codes.push_back(code); 
            runningGames++;
            std::string codeString = "CO:" + to_string(code);
            Controller* ctr = new Controller(codeOrNumOfPlayers/2, code, c);

            // Send the code back to the user who requested the game
            send(c.socket , codeString.c_str(), strlen(codeString.c_str()), 0 );
            games.push_back(ctr);
        }
        else {

            // Go through each of the games and try to
            // find the game the user requested
            for(int i = 0; i < games.size(); i++){
                if(games[i]->code == codeOrNumOfPlayers){
                    games[i]->AcceptNewClient(c);
                    std::string conf = "CO:" + to_string(games[i]->code);
                    send(c.socket , conf.c_str(), strlen(conf.c_str()) , 0 );

                    // If it is time to start the game
                    if(games[i]->b->teams*2 == games[i]->clients.size()){
                        int j = 0;
                        while(exists[j]){
                            j++;
                            if(j == 99){
                                j = 0;
                            }
                        }

                        // Keep track of the game inside of the array and
                        // signify that it is running.
                        thds[j] = std::thread(&Controller::play, games[i]);
                        exists[j] = true;
                    }
                }
            }
        }
        std::fill_n(buffer, 4096, 0);
    }
}