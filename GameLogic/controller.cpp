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

Controller::Controller(int teams){
    b = new BoardState(teams);
}

Controller::~Controller(){
    delete b;
}


// THIS WILL HAVE TO BE MODIFIED FOR SERVER/CLIENT COMMUNICATIONS
int Controller::play(stringstream& ss){

    // Gets a vector of all of the players in their turn order
    // (players on the same team sit across from each other)
    vector<Player*> turn(b->teams*2);
    for(int i = 0; i < b->teams; i++){
        turn[i] = b->t[i]->p1;
        turn[i+b->teams] = b->t[i]->p2;
    }

    int currentTurn = 0;
    int winningScore = 0;

    bool done = false;
    std::string input;

    // While the game is not over keep playing
    while(!done){
        std::cout << currentTurn << "\nDraw or Pickup?\n";

        // Try to look at the top card on the pile (if not it is okay and not an error)
        try{
            Card c = b->b->pilePeek();
            std::cout << c.value << " " << c.suit <<  " " << c.deckID << std::endl;
        }
        catch(exception e){
            std::cout << e.what() << std::endl;
        }

        // Display the players hand
        Hand* handToDisplay;
        if(turn[currentTurn]->inFoot){
            handToDisplay = turn[currentTurn]->foot;
        }
        else{
            handToDisplay = turn[currentTurn]->hand;
        }

        for(int i = 0; i < handToDisplay->cards.size(); i++){
            std::cout << handToDisplay->cards[i].value << " " << handToDisplay->cards[i].suit << " " << handToDisplay->cards[i].deckID << ",";
        }
        std::cout << "\n";

        // PLAYER INPUT (D/P)
        std::cin >> input;
        if(input.compare("D") == 0){
            turn[currentTurn]->draw();
        }
        else{
            turn[currentTurn]->pickUp();
        }

        std::cout << "Play or Moveon\n";
        if(turn[currentTurn]->inFoot){
            handToDisplay = turn[currentTurn]->foot;
        }
        else{
            handToDisplay = turn[currentTurn]->hand;
        }

        for(int i = 0; i < handToDisplay->cards.size(); i++){
            std::cout << handToDisplay->cards[i].value << " " << handToDisplay->cards[i].suit << " " << handToDisplay->cards[i].deckID << ",";
        }
        std::cout << "\n";

        // PLAYER INPUT (P/M)
        std::cin >> input;

        while(input.compare("P") == 0){
            // Flush cin
            std::cin.ignore();

            // In order to lay cards down the input will come
            // in as:
            // A|4|5|6|7|8|9|10|J|Q|K|
            // With each card displayed as:
            // 1 1 0,1 0 1||5 0 1|...
            // Following: value suit deckID
            std::getline(std::cin, input);
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
            turn[currentTurn]->play(c);
            std::cout << "Play or Moveon\n";
            if(turn[currentTurn]->inFoot){
                handToDisplay = turn[currentTurn]->foot;
            }
            else{
                handToDisplay = turn[currentTurn]->hand;
            }

            for(int i = 0; i < handToDisplay->cards.size(); i++){
                std::cout << handToDisplay->cards[i].value << " " << handToDisplay->cards[i].suit << " " << handToDisplay->cards[i].deckID << ",";
            }

            // PLAYER INPUT (P/M)
            std::cin >> input;
        }
        
        // A player has to discard after every hand
        std::cout << "which card would you like to discard?\n";
        if(turn[currentTurn]->inFoot){
            handToDisplay = turn[currentTurn]->foot;
        }
        else{
            handToDisplay = turn[currentTurn]->hand;
        }

        for(int i = 0; i < handToDisplay->cards.size(); i++){
            std::cout << handToDisplay->cards[i].value << " " << handToDisplay->cards[i].suit << " " << handToDisplay->cards[i].deckID << ",";
        }
        std::cout << "\n";

        int value,suit,deckID;

        // PLAYER INPUT (value suit deckID)
        std::cin >> value >> suit >> deckID;
        if(value > 51){
            Card test(true,value,suit,50,deckID);
            turn[currentTurn]->discard(test);
        }
        else {
            Card test((value == 2),value,suit,vals[value-1],deckID);
            turn[currentTurn]->discard(test);
        }
        
        // If the player lays down and discards their last card in their foot then they are done!
        if(turn[currentTurn]->inFoot && turn[currentTurn]->foot->cards.size() == 0){
            
            // Reset the board
            b->reset();
            
            // Add up all the scores and determine if someone has won
            for(int i = 0; i < b->teams; i++){
                std::cout << b->scores[i] << " ";
                if(b->scores[i] >= 10000){
                    done = true;
                    winningScore = b->scores[i];
                }
            }
        }

        // Go to the next player's turn
        currentTurn++;
        if(currentTurn == 2*b->teams) {
            currentTurn = 0;
        }
    }
    return winningScore;
}