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

int vals[] = {20, 20, 100, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10};

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
            std::cout << "1" << std::endl;
            std::cin.ignore();
            input = "";
            cin >> input;
            vector<Card> c[11];
            std::cout << "2" << std::endl;
            vector<std::string> cardSet;
            std::stringstream t;


            cardSet = split(input, "|");
            
            

            for(int i = 0; i < 11; i++){
                vector<std::string> cardStrings;
                t << cardSet[i];
                cardStrings = split(t.str(), ",");
                std::cout << cardSet[i] << std::endl;
                int value,suit,deckID;
                vector<Card> cards;
                if(!(cardSet[i].compare("") == 0)){
                    for(int j = 0; j < cardStrings.size(); j++){
                        t.flush();

                        t << cardStrings[j];
                        t >> value >> suit >> deckID;
                        Card test(false,value,suit,0,deckID);
                        cards.push_back(test);

                    }
                }

                t.flush();
                c[i] = cards;
                cards.clear();
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
        Card c(false,value,suit,0,deckID);
        turn[currentTurn]->discard(c);
          
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