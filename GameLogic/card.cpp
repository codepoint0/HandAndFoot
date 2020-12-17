#include "card.h"
#include <random>
#include <algorithm>
#include <exception>

struct EmptyWreathException : public std::exception{
    const char * what() const throw(){
        return "Empty Wreath Exception: The wreath ran out of cards and the pile was empty!";
    }
};

struct EmptyPileException : public std::exception{
    const char * what() const throw(){
        return "Empty Pile Exception: The pile cannot be picked up if it is empty.";
    }
};

int values[] = {20, 20, 100, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10};

bool Card::operator==(const Card& c) {
    return ((wild == c.wild)&&(value==c.value)&&(suit==c.suit)&&(cardValue==c.cardValue)&&(deckID==c.deckID));
}

Board::Board(int decks){
    for(int i = 0; i < decks; i++){
        for(int j = 0; j < 54; j++){
            if(j < 52){
                if((j/13 == 1 || j/13 == 2) && j%13 == 2){
                    Card c((j%13 == 1), (j%13)+1, j/13, values[j%13] + 200, i);
                    wreath.push_back(c);
                }
                else{
                    // j%13 == 1 checks for 2 (wild)
                    Card c((j%13 == 1), (j%13)+1, j/13, values[j%13], i);
                    wreath.push_back(c);
                }
            } 
            else{
                Card c(true, j, -1, 50, i);
                wreath.push_back(c);
            }
        }
    }

    random_shuffle(wreath.begin(), wreath.end());
}

Card Board::draw(){
    if(wreath.empty()){
        if(pile.empty()){
            throw EmptyWreathException();
        }
        else{
            vector<Card> c = pickUp();
            while(!c.empty()){
                wreath.push_back(c.back());
                c.pop_back();
            }

            random_shuffle(wreath.begin(), wreath.end());

            Card d = wreath.front();
            wreath.pop_front();
            return d;
        }
    }
    else{
        Card c = wreath.front();
        wreath.pop_front();
        return c;
    }
}

vector<Card> Board::pickUp(){
    if(pile.empty()){
        throw EmptyPileException();
    }
    vector<Card> c;
    while(!pile.empty()){
        c.push_back(pile.back());
        pile.pop_back();
    }

    return c;
}

void Board::discard(Card c){
    pile.push_front(c);
}


Hand::Hand(Board* b) {
    board = b;
    for(int i=0;i<10;i++) {
        cards.push_back(b->draw());
    }
}

Hand::Hand(const Hand& h) {
    board = h.board;
    for(int i=0;i<h.cards.size();i++) {
        cards.push_back(h.cards[i]);
    }
}

Hand& Hand::operator=(const Hand& h) {
    if(this != &h) {
        this->board = h.board;
        this->cards.clear();
        for(int i=0;i<h.cards.size();i++) {
            this->cards.push_back(h.cards[i]);
        }
    }
}

void Hand::draw() {
    Card c = board->draw();
    try{
        cards.push_back(c);
    }
    catch (exception e){
        throw e;
    }
}

void Hand::discard(Card c){
    board->discard(c);
    vector<Card> card;
    card.push_back(c);
    remove(card);
}

void Hand::remove(vector<Card> c) {
    for(vector<Card>::iterator it = c.begin();it != c.end();it++) {
        for(vector<Card>::iterator jt = cards.begin();jt != cards.end();jt++) {
            if(*it == *jt) {
                cards.erase(jt,jt+1);
                jt = cards.end();
            }
        }
    }
}

void Hand::pickUp(){
    vector<Card> c;
    c = board->pickUp();
    for(int i = 0; i < c.size(); i++){
        cards.push_back(c[i]);
    }
}

int Hand::score(){
    int score;
    for(vector<Card>::iterator jt = cards.begin();jt != cards.end();jt++) {
        score += jt->cardValue;
    }
    return score;
}

Card Hand::peek(){
    return cards[0];
}

int Group::score(){
    int size = cards.size();
    int score = 0;
    for(int i = 0; i < size; i++){
        score += cards[i].cardValue;
    }
    if(size > 6){
        if(dirty){
            score += 300;
        }
        else{
            score += 500;
        }
    }
    
    return score;
}