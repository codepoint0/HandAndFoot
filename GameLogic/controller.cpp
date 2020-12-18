#include <iostream>
#include <string>
#include "controller.h"

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

int Controller::play(stringstream& ss){
    vector<Player*> turn(b->teams*2);
    for(int i = 0; i < b->teams; i++){
        turn[i] = b->t[i]->p1;
        turn[i+b->teams] = b->t[i]->p2;
    }

    int currentTurn = 0;
    int winningScore = 0;

    bool done = false;
    std::string input;

    while(!done){
        std::cout << currentTurn << "\nDraw or Pickup?\n";

        try{
            Card c = b->b->pilePeek();
            std::cout << c.value << " " << c.suit <<  " " << c.deckID << std::endl;
        }
        catch(exception e){
            std::cout << e.what() << std::endl;
        }
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
        std::cin >> input;

        while(input.compare("P") == 0){
            std::cin.ignore();
            std::getline(std::cin, input);
            vector<Card> c[11];
            vector<std::string> cardSet;


            cardSet = split(input, "|");
            
            

            for(int i = 0; i < 11; i++){
                vector<std::string> cardStrings;
                cardStrings = split(cardSet[i], ",");
                int value,suit,deckID;
                vector<Card> cards;
                if(!(cardSet[i].compare("") == 0)){
                    for(int j = 0; j < cardStrings.size(); j++){
                        vector<std::string> s;
                        s = split(cardStrings[j], " ");

                        value = std::stoi(s[0]);
                        suit = std::stoi(s[1]);
                        deckID = std::stoi(s[2]);
                        
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
            std::cin >> input;
        }
        
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
        std::cin >> value >> suit >> deckID;
        if(value > 51){
            Card test(true,value,suit,50,deckID);
            turn[currentTurn]->discard(test);
        }
        else {
            Card test((value == 2),value,suit,vals[value-1],deckID);
            turn[currentTurn]->discard(test);
        }
          
        if(turn[currentTurn]->inFoot && turn[currentTurn]->foot->cards.size() == 0){
            b->reset();
            
            for(int i = 0; i < b->teams; i++){
                std::cout << b->scores[i] << " ";
                if(b->scores[i] >= 10000){
                    done = true;
                    winningScore = b->scores[i];
                }
            }

        }
        currentTurn++;
        if(currentTurn == 2*b->teams) {
            currentTurn = 0;
        }
    }
    return winningScore;
}