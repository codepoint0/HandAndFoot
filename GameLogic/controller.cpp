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

Controller::Controller(int teams, int code, Client c){
    b = new BoardState(teams);
    players = teams*2;
    this->code = code;
    clients.push_back(c);
    buffer[4096] = {0};
}

Controller::Controller(const Controller& ctr){

}

Controller::~Controller(){
    delete b;
}


// THIS WILL HAVE TO BE MODIFIED FOR SERVER/CLIENT COMMUNICATIONS
void Controller::play(){
    std::fill_n(buffer, 4096, 0);
    for(int i = 0; i < players; i++){
        PreRead(i);
    }

    std::string input;
    vector<std::string> joinOrder;
    for(int i = 0; i < players; i++){
        std::string username;
        PreSendPlayerInfo(i, "GU:" + to_string(i) + " " + to_string(players));
        username = PreRead(i);
        joinOrder.push_back(username);
    }
    for(int i = 0; i < joinOrder.size(); i++){
        std::cout << "JOIN ORDER: " << joinOrder[i] << std::endl;
    }

    for(int i = 0; i < players; i++){
        for(int j = 0; j < players; j++){
            PreSendPlayerInfo(i, "US:" + to_string(j) + " " + joinOrder[j]);
            PreRead(i);
        }
        PreSendPlayerInfo(i, "LB:");
        PreRead(i);
    }

    PreRead(0);
    vector<int> selected;
    vector<int> correctPairs;
    for(int i = 0; i < players; i++){
        PreSendPlayerInfo(i, "SC:");
        input = PreRead(i);
        selected.push_back(std::stoi(input));
    }

    correctPairs = Pairing(selected);
    controlled = TurnOrder(correctPairs);

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
    for(int i = 0; i < turn.size()/2; i++){
        SendPlayerInfo(i, "LC:" + to_string(b->t[i]->p1->hand->cards[0].suit) + " " + to_string(b->t[i]->p1->hand->cards[0].value));
        Read(i);
        SendPlayerInfo(i, "RC:" + to_string(b->t[i]->p1->foot->cards[0].suit) + " " + to_string(b->t[i]->p1->foot->cards[0].value));
        Read(i);
        SendPlayerInfo((i+turn.size()/2)%turn.size(), "LC:" + to_string(b->t[i]->p2->hand->cards[0].suit) + " " + to_string(b->t[i]->p2->hand->cards[0].value));
        Read((i+turn.size()/2)%turn.size());
        SendPlayerInfo((i+turn.size()/2)%turn.size(), "RC:" + to_string(b->t[i]->p2->foot->cards[0].suit) + " " + to_string(b->t[i]->p2->foot->cards[0].value));
        Read((i+turn.size()/2)%turn.size());
        SendPlayerInfo(i, "PS:6");
        Read(i);
        SendPlayerInfo((i+turn.size()/2)%turn.size(), "PS:6");
        Read((i+turn.size()/2)%turn.size());
        SendPlayerInfo(i, "RD:");
        Read(i);
        SendPlayerInfo((i+turn.size()/2)%turn.size(), "RD:");
        Read((i+turn.size()/2)%turn.size());
    }

    sleep(20);

    for(int i = 0; i < turn.size()/2; i++){
        SendPlayerInfo(i, "SL:");
        std::string choice = Read(i);
        if(std::stoi(choice) == 2){
            b->t[i]->p1->swap();
        }
        SendPlayerInfo((i+turn.size()/2)%turn.size(), "SL:");
        choice = Read((i+turn.size()/2)%turn.size());
        if(std::stoi(choice) == 2){
            b->t[i]->p2->swap();
        }
    }

    for(int i = 0; i < turn.size(); i++){

        std::stringstream scores;
        for(int j = 0; j < b->t.size(); j++){
            SendPlayerInfo(i, "SB:" + to_string(j) + " " + usernames[j][0] + "" + usernames[j+b->teams][0] + " " + "0");
            Read(i);
        }  
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
                std::cout << "FOOT:" << turn[j]->hand->cards.size() << std::endl;
                ss << "HC:" << j << " " << turn[j]->foot->cards.size();
                SendPlayerInfo(i, ss.str());
                Read(i);
               ss.str("");
            }
            else{
                std::cout << "CARD:" << turn[j]->hand->cards.size() << std::endl;
                ss << "HC:" << j << " " << turn[j]->hand->cards.size();
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
        SendPlayerInfo(i, "RD:");
        Read(i);
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
        SendPlayerInfo(currentTurn, "RD:");
        Read(currentTurn);

        // Display the players hand
        std::string hand = StringHand(currentTurn, turn);
        SendPlayerInfo(currentTurn, hand);
        Read(currentTurn);
        SendPlayerInfo(currentTurn, "~");
        std::string PorD = Read(currentTurn);
        std::cout << PorD << std::endl;
        if(PorD[0] == 'D'){
            Card c = turn[currentTurn]->draw();
            SendPlayerInfo(currentTurn, "DW:" + to_string(c.suit) + " " + to_string(c.value));
            Read(currentTurn);
        }
        else{
            turn[currentTurn]->pickUp();
        }
        SendPlayerInfo(currentTurn, "RD:");
        Read(currentTurn);

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
            SendPlayerInfo(i, "RD:");
            Read(i);
        }

        SendPlayerInfo(currentTurn, "PS:2");
        Read(currentTurn);
        // Display the players hand
        hand = StringHand(currentTurn, turn);
        SendPlayerInfo(currentTurn, hand);
        Read(currentTurn);

        SendPlayerInfo(currentTurn, "RD:");
        Read(currentTurn);

        input = "";
        input = Read(currentTurn);
        std::cout << input << std::endl;
        while(input[0] != 'M'){
            // In order to lay cards down the input will come
            // in as:
            // A|4|5|6|7|8|9|10|J|Q|K|
            // With each card displayed as:
            // 1 1 0,1 0 1||5 0 1|...
            // Following: value suit deckID
            vector<Card> c[11];
            try{

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
            }
            catch(exception e){

            }
            try{
                turn[currentTurn]->play(c);
                hand = StringHand(currentTurn, turn);
                std::string groups = StringGroup(currentTurn, turn);
                SendPlayerInfo(currentTurn, hand);
                Read(currentTurn);
                SendPlayerInfo(currentTurn, groups);
                Read(currentTurn);
                std::string groupsPartner = StringGroup((currentTurn+turn.size()/2)%turn.size(), turn);
                SendPlayerInfo((currentTurn+turn.size()/2)%turn.size(), groupsPartner);
                Read((currentTurn+turn.size()/2)%turn.size());
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

            SendPlayerInfo(currentTurn, "RD:");
            Read(currentTurn);
            SendPlayerInfo((currentTurn+turn.size()/2)%turn.size(), "RD:");
            Read((currentTurn+turn.size()/2)%turn.size());

            input = Read(currentTurn);
        }
        
        // A player has to discard after every hand
        hand = StringHand(currentTurn, turn);
        SendPlayerInfo(currentTurn, hand);
        Read(currentTurn);
        SendPlayerInfo(currentTurn, "PS:4");
        Read(currentTurn);
    
        SendPlayerInfo(currentTurn, "RD:");
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
        
        SendPlayerInfo(currentTurn, "RD:");
        Read(currentTurn);

        // If the player lays down and discards their last card in their foot then they are done!
        if(turn[currentTurn]->inFoot && turn[currentTurn]->foot->cards.size() == 0){
            
            // Reset the board
            
            for(int i = 0; i < turn.size(); i++){
                std::stringstream scores;
                for(int j = 0; j < b->t.size(); j++){
                    SendPlayerInfo(i, "SB:" + to_string(j) + " " + usernames[j][0] + "" + usernames[j+b->teams][0] + " " + to_string(turn[j]->team->score()));
                    Read(i);
                    SendPlayerInfo(i, "RD:");
                    Read(i);
                }  
            }
            
            b->reset();
            for(int i = 0; i < turn.size()/2; i++){
                SendPlayerInfo(i, "LC:" + to_string(b->t[i]->p1->hand->cards[0].suit) + " " + to_string(b->t[i]->p1->hand->cards[0].value));
                Read(i);
                SendPlayerInfo(i, "RC:" + to_string(b->t[i]->p1->foot->cards[0].suit) + " " + to_string(b->t[i]->p1->foot->cards[0].value));
                Read(i);
                SendPlayerInfo((i+turn.size()/2)%turn.size(), "LC:" + to_string(b->t[i]->p2->hand->cards[0].suit) + " " + to_string(b->t[i]->p2->hand->cards[0].value));
                Read((i+turn.size()/2)%turn.size());
                SendPlayerInfo((i+turn.size()/2)%turn.size(), "RC:" + to_string(b->t[i]->p2->foot->cards[0].suit) + " " + to_string(b->t[i]->p2->foot->cards[0].value));
                Read((i+turn.size()/2)%turn.size());
                SendPlayerInfo(i, "PS:6");
                Read(i);
                SendPlayerInfo((i+turn.size()/2)%turn.size(), "PS:6");
                Read((i+turn.size()/2)%turn.size());
                SendPlayerInfo(i, "RD:");
                Read(i);
                SendPlayerInfo((i+turn.size()/2)%turn.size(), "RD:");
                Read((i+turn.size()/2)%turn.size());
            }

            sleep(20);

            for(int i = 0; i < turn.size()/2; i++){
                SendPlayerInfo(i, "SL:");
                std::string choice = Read(i);
                if(std::stoi(choice) == 2){
                    b->t[i]->p1->swap();
                }
                SendPlayerInfo((i+turn.size()/2)%turn.size(), "SL:");
                choice = Read((i+turn.size()/2)%turn.size());
                if(std::stoi(choice) == 2){
                    b->t[i]->p2->swap();
                }
            }

            for(int i = 0; i < turn.size(); i++){
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
                        std::cout << "FOOT:" << turn[j]->hand->cards.size() << std::endl;
                        ss << "HC:" << j << " " << turn[j]->foot->cards.size();
                        SendPlayerInfo(i, ss.str());
                        Read(i);
                    ss.str("");
                    }
                    else{
                        std::cout << "CARD:" << turn[j]->hand->cards.size() << std::endl;
                        ss << "HC:" << j << " " << turn[j]->hand->cards.size();
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
                SendPlayerInfo(i, "RD:");
                Read(i);
            }
            
            // Add up all the scores and determine if someone has won
            for(int i = 0; i < b->teams; i++){
                if(b->scores[i] >= 10000){
                    done = true;
                    winningScore = b->scores[i];
                    // Reset the board
            
                    for(int i = 0; i < turn.size(); i++){
                        std::stringstream scores;
                        for(int j = 0; j < b->t.size(); j++){
                            SendPlayerInfo(i, "EG:" + to_string(j) + " " + usernames[j][0] + "" + usernames[j+b->teams][0] + " " + to_string(turn[j]->team->score()));
                            Read(i);
                        }  
                    }
                    delete this;
                }
            }
        }

        SendPlayerInfo(currentTurn, "PS:0");
        Read(currentTurn);

        for(int i = 0; i < players; i++){
            stringstream ss;
            SendPlayerInfo(i, StringGroup(currentTurn, turn));
            Read(i);
            SendPlayerInfo(i, StringGroup((currentTurn+turn.size()/2)%turn.size(), turn));
            Read(i);
            if(turn[currentTurn]->inFoot){
                ss << "HF:" << currentTurn << " " << "0";
                SendPlayerInfo(i, ss.str());
                Read(i);
                ss.str("");
            }
            else{
                ss << "HF:" << currentTurn << " " << "1";
                SendPlayerInfo(i, ss.str());
                Read(i);
                ss.str("");
            }
            if(turn[currentTurn]->inFoot){
                ss << "HC:" << currentTurn << " " << turn[currentTurn]->foot->cards.size();
                SendPlayerInfo(i, ss.str());
                Read(i);
                ss.str("");
            }
            else{
                ss << "HC:" << currentTurn << " " << turn[currentTurn]->hand->cards.size();
                SendPlayerInfo(i, ss.str());
                Read(i);
                ss.str("");
            }
            // Display the players hand
            std::string hand = StringHand(i, turn);
            SendPlayerInfo(i, hand);
            Read(i);
            SendPlayerInfo(i, "NT:" + to_string((currentTurn+1)%players));
            Read(i);
            Card c = b->b->pilePeek();
            std::stringstream s;
            s << "TC:" << c.value << " " << c.suit << " " << c.deckID;
            SendPlayerInfo(i, s.str());
            Read(i);
            SendPlayerInfo(i, "PN:" + to_string(b->b->pile.size()));
            Read(i);
            SendPlayerInfo(i, "RD:");
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


vector<int> Controller::TurnOrder(vector<int> correctPairs){
    vector<bool> assigned;
    vector<int> order;
    for(int i=0;i<correctPairs.size();i++) {
        assigned.push_back(false);
        order.push_back(-1);
    }
    int index=0;
    for(int i=0;i<correctPairs.size()/2;i++) {
        while(assigned[index]) {
            index++;
        }
        order[i] = index;
        order[i + correctPairs.size()/2] = correctPairs[index];
        assigned[index] = true;
        assigned[correctPairs[index]] = true;
    }
    return order;
}


vector<vector<int>> Controller::enumeratedPairings(int k)
{
    vector<vector<int>> toReturn;
    if (k == 0)
    {
        return toReturn;
    }
    if (k == 2)
    {
        toReturn = {{1, 0}};
    }
    vector<vector<int>> recursive = enumeratedPairings(k - 2);
    for (int i = 1; i < k; i++)
    {
        vector<int> attempts;
        for (int j = 1; j < k; j++)
        {
            if (j != i)
            {
                attempts.push_back(j);
            }
        }
        for (vector<int> v : recursive)
        {
            vector<int> proto;
            for (int j = 0; j < k; j++)
            {
                proto.push_back(-1);
            }
            proto[0] = i;
            proto[i] = 0;
            for (int j = 0; j < k - 2; j++)
            {
                proto[attempts[j]] = attempts[v[j]];
                proto[attempts[v[j]]] = attempts[j];
            }
            toReturn.push_back(proto);
        }
    }
    return toReturn;
}

int Controller::score(vector<int> pairs, vector<int> selected)
{
    int scored = 0;
    for (int i = 0; i < pairs.size(); i++)
    {
        if (pairs[i] == selected[i])
        {
            scored++;
        }
    }
    return scored;
}

vector<int> Controller::Pairing(vector<int> selected)
{
    vector<vector<int>> enumeratedPairing = enumeratedPairings(selected.size());
    int maxScore = -1;
    vector<int> maxValue;
    for (vector<int> v : enumeratedPairing)
    {
        if (score(v, selected) > maxScore)
        {
            maxScore = score(v, selected);
            maxValue = v;
        }
    }
    for(int i = 0; i < maxValue.size(); i++){
        std::cout << maxValue[i] << std::endl;
    }
    return maxValue;
}

void Controller::PreSendPlayerInfo(int playerID, std::string message){
    std::fill_n(buffer, 4096, 0);
    std::cout << message << std::endl;
    send(clients[playerID].socket , message.c_str() , strlen(message.c_str()) , 0 );
    std::fill_n(buffer, 4096, 0);
}

std::string Controller::PreRead(int i){
    std::fill_n(buffer, 4096, 0);
    std::string valread;
    valread = read(clients[i].socket, buffer, sizeof(buffer)); 
    std::string input = buffer;
    std::cout << "PREREAD: " << input << std::endl;
    std::fill_n(buffer, 4096, 0);
    return input;
}


void Controller::SendPlayerInfo(int playerID, std::string message){
    std::fill_n(buffer, 4096, 0);
    std::cout << message << std::endl;
    send(clients[controlled[playerID]].socket , message.c_str() , strlen(message.c_str()) , 0 );
    std::fill_n(buffer, 4096, 0);
}

std::string Controller::Read(int i){
    std::fill_n(buffer, 4096, 0);
    std::string valread;
    valread = read(clients[controlled[i]].socket, buffer, sizeof(buffer)); 
    std::string input = buffer;
    std::cout << input << std::endl;
    std::fill_n(buffer, 4096, 0);
    return input;
}

void Controller::AcceptNewClient(Client c){

    clients.push_back(c);

}