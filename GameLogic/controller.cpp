/*
    HAND AND FOOT
    This file is the messanger between the outside world and the game.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: controller.cpp
*/
#include <iostream>
#include <string>
#include "controller.h"
char* message;
char buffer[4096];
int new_socket;
int valread;
int server_fd;
struct sockaddr_in address; 
int opt = 1; 
int addrlen = sizeof(address);
std::thread threads[10];
int rc;
int ThreadID = 0;
std::mutex mtx;
vector<Client> clients;
int players;
bool wait;
vector<std::string> usernames;

/*
    Splits a string into a vector upon a given token
*/
vector<string> split(string str, string token){
    vector<string>result;
    while(str.size()){
        int index = str.find(token);
        if(index!=string::npos){
            result.push_back(str.substr(0,index));
            str = str.substr(index+token.size());
            if(str.size()==0)result.push_back(str);
        }else{
            result.push_back(str);
            str = "";
        }
    }
    return result;
}

int vals[13] = {20, 20, 100, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10};

Controller::Controller(int teams){
    
}

Controller::~Controller(){
    delete b;
}


// THIS WILL HAVE TO BE MODIFIED FOR SERVER/CLIENT COMMUNICATIONS
void Controller::play(){

    std::string input;
    for(int i = 0; i < players; i++){
        SendPlayerInfo(i, "SG:" + to_string(i) + " " + to_string(players));
        input = Read(i);
        std::cout << input << std::endl;
        usernames.push_back(input);
        input = "";
    }
    

    for(int i = 0; i < players; i++){
        std::cout << usernames[i] << " " << std::endl;
    }

    

    // Gets a vector of all of the players in their turn order
    // (players on the same team sit across from each other)
    vector<Player*> turn(b->teams*2);
    for(int i = 0; i < b->teams; i++){
        turn[i] = b->t[i]->p1;
        turn[i+b->teams] = b->t[i]->p2;
    }
    for(int i = 0; i < turn.size(); i++){
        if(i == 0){
            SendPlayerInfo(i, "PS:1");
            Read(i);
        }
        // Try to look at the top card on the pile (if not it is okay and not an error)
        try{
            Card c = b->b->pilePeek();
            std::stringstream s;
            s << "TC:" << c.value << " " << c.suit << " " << c.deckID;
            SendPlayerInfo(i, s.str());
            Read(i);
            SendPlayerInfo(i, "PN:" + to_string(b->b->pile.size()));
            Read(i);
        }
        catch(exception e){
            SendPlayerInfo(i, "TC:0 0 0");
            Read(i);
            SendPlayerInfo(i, "PN:" + to_string(b->b->pile.size()));
            Read(i);
        }

        // Display the players hand
        std::string hand = StringHand(i, turn);
        SendPlayerInfo(i, hand);
        Read(i);

        stringstream ss;
        for(int j = 0; j < turn.size(); j++){
            SendPlayerInfo(i, StringGroup(j, turn));
            Read(i);
            if(turn[j]->inFoot){
                ss << "HF:" << j << " " << "0";
                SendPlayerInfo(i, ss.str());
                Read(i);
                ss.str("");
            }
            else{
                ss << "HF:" << j << " " << "1";
                SendPlayerInfo(i, ss.str());
                Read(i);
                ss.str("");
            }
            if(turn[j]->inFoot){
                ss << "HC:" << j << " " << turn[j]->foot->cards.size();
                SendPlayerInfo(i, ss.str());
                Read(i);
               ss.str("");
            }
            else{
                ss << "HC:" << j << " " << turn[j]->foot->cards.size();
                SendPlayerInfo(i, ss.str());
                Read(i);
                ss.str("");
            }
            ss.str("");
            ss << "UN:" << j << " " << usernames[j];
            SendPlayerInfo(i, ss.str());
            Read(i);
            ss.str("");
        }
    }

    int currentTurn = 0;
    int winningScore = 0;

    bool done = false;
    

    // While the game is not over keep playing
    while(!done){
        SendPlayerInfo(currentTurn, "PS:1");
        Read(currentTurn);

        // Try to look at the top card on the pile (if not it is okay and not an error)
        try{
            Card c = b->b->pilePeek();
            std::stringstream s;
            s << "TC:" << c.value << " " << c.suit << " " << c.deckID;
            SendPlayerInfo(currentTurn, s.str());
            Read(currentTurn);
            SendPlayerInfo(currentTurn, "PN:" + to_string(b->b->pile.size()));
            Read(currentTurn);
        }
        catch(exception e){
            SendPlayerInfo(currentTurn, "TC:0 0 0");
            Read(currentTurn);
            SendPlayerInfo(currentTurn, "PN:" + to_string(b->b->pile.size()));
            Read(currentTurn);
        }

        // Display the players hand
        std::string hand = StringHand(currentTurn, turn);
        SendPlayerInfo(currentTurn, hand);
        Read(currentTurn);
        std::string PorD = Read(currentTurn);
        std::cout << PorD << std::endl;
        if(PorD[0] == 'D'){
            turn[currentTurn]->draw();
        }
        else{
            turn[currentTurn]->pickUp();
        }

        for(int i = 0; i < players; i++){
            // Try to look at the top card on the pile (if not it is okay and not an error)
            try{
                Card c = b->b->pilePeek();
                std::stringstream s;
                s << "TC:" << c.value << " " << c.suit << " " << c.deckID;
                SendPlayerInfo(i, s.str());
                Read(i);
                SendPlayerInfo(i, "PN:" + to_string(b->b->pile.size()));
                Read(i);
            }
            catch(exception e){
                SendPlayerInfo(i, "TC:0 0 0");
                Read(i);
                SendPlayerInfo(i, "PN:" + to_string(b->b->pile.size()));
                Read(i);
            }
        }

        SendPlayerInfo(currentTurn, "PS:2");
        Read(currentTurn);
        // Display the players hand
        hand = StringHand(currentTurn, turn);
        SendPlayerInfo(currentTurn, hand);
        Read(currentTurn);

        input = "";

        input = Read(currentTurn);
        std::cout << input << std::endl;
        while(input[0] != 'M'){
            // Flush cin

            // In order to lay cards down the input will come
            // in as:
            // A|4|5|6|7|8|9|10|J|Q|K|
            // With each card displayed as:
            // 1 1 0,1 0 1||5 0 1|...
            // Following: value suit deckID
            vector<Card> c[11];
            vector<std::string> cardSet;

            // Split on the pipes
            cardSet = split(input, "|");
            
            // Go through each of the piped groups
            for(int i = 0; i < 11; i++){

                // Split by cards
                vector<std::string> cardStrings;
                cardStrings = split(cardSet[i], ",");

                int value,suit,deckID;
                vector<Card> cards;
                if(!(cardSet[i].compare("") == 0)){
                    for(int j = 0; j < cardStrings.size(); j++){
                        vector<std::string> s;
                        s = split(cardStrings[j], " ");

                        // Extract the different values
                        value = std::stoi(s[0]);
                        suit = std::stoi(s[1]);
                        deckID = std::stoi(s[2]);
                        
                        // Create cards based on the values
                        if(value > 51){
                            Card test(true,value,suit,50,deckID);
                            cards.push_back(test);
                        }
                        else {
                            Card test((value == 2),value,suit,vals[value-1],deckID);
                            cards.push_back(test);
                        }

                    }
                }
                c[i] = cards;
            }
            try{
                turn[currentTurn]->play(c);
                hand = StringHand(currentTurn, turn);
                std::string groups = StringGroup(currentTurn, turn);
                SendPlayerInfo(currentTurn, hand);
                Read(currentTurn);
                SendPlayerInfo(currentTurn, groups);
                Read(currentTurn);
                if(!turn[currentTurn]->team->meld){
                    turn[currentTurn]->team->meld = true;
                }
            }
            catch(exception e){
                std::stringstream s;
                s << "EE:" << e.what();
                SendPlayerInfo(currentTurn, s.str());
                Read(currentTurn);
            }

            SendPlayerInfo(currentTurn, "PS:2");
            Read(currentTurn);

            input = Read(currentTurn);
        }
        
        // A player has to discard after every hand
        hand = StringHand(currentTurn, turn);
        SendPlayerInfo(currentTurn, hand);
        Read(currentTurn);
        SendPlayerInfo(currentTurn, "PS:4");
        Read(currentTurn);

        int value,suit,deckID;

        // PLAYER INPUT (value suit deckID)
        std::stringstream ss1;
        input = Read(currentTurn);
        ss1 << input;
        std::cout << input << std::endl;
        ss1 >> value >> suit >> deckID;
        std::cout << value << " " << suit << " " << deckID << std::endl;
        if(value > 51){
            Card test(true,value,suit,50,deckID);
            turn[currentTurn]->discard(test);
        }
        else {
            Card test((value == 2),value,suit,vals[value-1],deckID);
            turn[currentTurn]->discard(test);
        }

        hand = StringHand(currentTurn, turn);
        SendPlayerInfo(currentTurn, hand);
        Read(currentTurn);
        
        // If the player lays down and discards their last card in their foot then they are done!
        if(turn[currentTurn]->inFoot && turn[currentTurn]->foot->cards.size() == 0){
            
            // Reset the board
            b->reset();
            
            // Add up all the scores and determine if someone has won
            for(int i = 0; i < b->teams; i++){
                if(b->scores[i] >= 10000){
                    done = true;
                    winningScore = b->scores[i];
                }
            }
        }

        SendPlayerInfo(currentTurn, "PS:0");
        Read(currentTurn);

        for(int i = 0; i < players; i++){
            stringstream ss;
            for(int j = 0; j < players; j++){
                SendPlayerInfo(i, StringGroup(j, turn));
                Read(i);
                if(turn[j]->inFoot){
                    ss << "HF:" << j << " " << "0";
                    SendPlayerInfo(i, ss.str());
                    Read(i);
                    ss.str("");
                }
                else{
                    ss << "HF:" << j << " " << "1";
                    SendPlayerInfo(i, ss.str());
                    Read(i);
                    ss.str("");
                }
                if(turn[j]->inFoot){
                    ss << "HC:" << j << " " << turn[j]->foot->cards.size();
                    SendPlayerInfo(i, ss.str());
                    Read(i);
                    ss.str("");
                }
                else{
                    ss << "HC:" << j << " " << turn[j]->foot->cards.size();
                    SendPlayerInfo(i, ss.str());
                    Read(i);
                    ss.str("");
                }
            }
            Card c = b->b->pilePeek();
            std::stringstream s;
            s << "TC:" << c.value << " " << c.suit << " " << c.deckID;
            SendPlayerInfo(i, s.str());
            Read(i);
            SendPlayerInfo(i, "PN:" + to_string(b->b->pile.size()));
            Read(i);
           // HAND COUNT, FOOT, TOP CARD OF PILE, PILE NUM
        }

        // Go to the next player's turn
        currentTurn++;
        if(currentTurn == 2*b->teams) {
            currentTurn = 0;
        }
    }
}

std::string Controller::StringHand(int PlayerID, vector<Player*> turn){
        // Display the players hand
    Hand* handToDisplay;
    if(turn[PlayerID]->inFoot){
        handToDisplay = turn[PlayerID]->foot;
    }
    else{
        handToDisplay = turn[PlayerID]->hand;
    }
    
    std::stringstream cardsInHand;
    cardsInHand << "PH:";
    for(int i = 0; i < handToDisplay->cards.size(); i++){
        cardsInHand << handToDisplay->cards[i].value << " " << handToDisplay->cards[i].suit << " " << handToDisplay->cards[i].deckID << ",";
    }
    return cardsInHand.str();
}

std::string Controller::StringGroup(int PlayerID, vector<Player*> turn){
    std::stringstream m;
    m << "PG:" << PlayerID << " ";
    for(int i = 0; i < 11; i++){
        for(int j = 0; j < turn[PlayerID]->team->groups[i].cards.size(); j++){
            Card c(turn[PlayerID]->team->groups[i].cards[j]);
            m << c.value << " " << c.suit << " " << c.deckID;
            if(j != turn[PlayerID]->team->groups[i].cards.size() - 1){
                m << ",";
            }
        }
        m << "|";
    }

    return m.str();
}

int Controller::SetUpServer(){
    ThreadID = 0;
    wait = true;
    players = 10000000;

    buffer[4096] = {0}; 

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
    return 0;
}

void test(int threadid){
    while(true){

    }
}

void Controller::ListenForNewClients(){
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

        std::cout << "Someone try to connect!" << std::endl;

        std::cout << "Someone connected!" << std::endl;
        clients.push_back(c);
        
        if(ThreadID == 0){
            std::cout << "Top" << std::endl;
            valread = read(clients[0].socket, buffer, sizeof(buffer)); 
            std::stringstream input(buffer);
            int t;
            input >> t;
            players = t;
            std::cout << "Num of players is " << players << std::endl;
            b = new BoardState(players/2);
            buffer[4096] = {0}; 
        }
        else{
            buffer[4096] = {0}; 
            std::cout << "mid" << std::endl;
            valread = read(clients[ThreadID].socket, buffer, sizeof(buffer)); 
            std::stringstream input(buffer);
            buffer[4096] = {0}; 
        }

        ThreadID++;

        if(ThreadID == players){
            std::cout << clients.size() << std::endl;
            play();
        }
        
        
        
        
    }
}



void Controller::SendPlayerInfo(int playerID, std::string message){
    std::cout << message << std::endl;
    send(clients[playerID].socket , message.c_str() , strlen(message.c_str()) , 0 );
    buffer[4096] = {0};
}

void Controller::SendAllInfo(std::string message){
    for(int i = 0; i < clients.size(); i++){
        send(clients[i].socket , message.c_str() , strlen(message.c_str()) , 0 ); 
    }
}

std::string Controller::Read(int i){
    std::fill_n(buffer, 4096, 0);
    std::string valread;
    valread = read(clients[i].socket, buffer, sizeof(buffer)); 
    std::string input = buffer;
    std::cout << input << std::endl;
    std::fill_n(buffer, 4096, 0);
    return input;
}