/*
    HAND AND FOOT
    Card code which holds the struct of a card, a group of cards
    the board and a hand. Holds the logic for each of these things.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: card.cpp
*/

#include "card.h"
#include <random>
#include <iostream>
#include <algorithm>
#include "errors.cpp"

// Keeps all of the values of the cards in a single array
int values[] = {20, 20, 100, 5, 5, 5, 5, 5, 10, 10, 10, 10, 10};

bool Card::operator==(const Card& c) {
    // A card is equal if their value, suit and deckID is the same
    return ((value==c.value)&&(suit==c.suit)&&(deckID==c.deckID));
}

Board::Board(int decks){
    
    // Go through all of the decks and create 54 cards for each deck
    for(int i = 0; i < decks; i++){
        for(int j = 0; j < 54; j++){
            
            // If the card is less than 52 then not a Joker
            if(j < 52){
                
                // If it is a red 3 then it is 200 points more than a normal 3
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
            
            // Create Joker cards
            else{
                Card c(true, j, -1, 50, i);
                wreath.push_back(c);
            }
        }
    }
    
    // Random shuffle the cards for drawing (NOTE: Seeding is needed)
    random_shuffle(wreath.begin(), wreath.end());

    // Put a card on the top of the pile
    discard(draw());
}

Card Board::draw(){
    
    // Can't draw from an empty wreath
    if(wreath.empty()){
        
        // Try to shuffle the pile into the wreath
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

    // Put all of the cards into a vector that
    // will be returned
    while(!pile.empty()){
        c.push_back(pile.back());
        pile.pop_back();
    }

    return c;
}

void Board::discard(Card c){
    pile.push_front(c);
}

Card Board::pilePeek(){
    if(pile.empty()){
        throw CardDoesNotExistException();
    }
    return pile[0];
}


Hand::Hand(Board* b) {
    board = b;

    // To start the game each hand has
    // 10 cards
    for(int i=0;i<10;i++) {
        cards.push_back(b->draw());
    }
}

Hand::Hand(const Hand& h) {
    // TODO: Check this copy constructor?
    board = h.board;
    for(int i=0;i<h.cards.size();i++) {
        cards.push_back(h.cards[i]);
    }
}

Hand& Hand::operator=(const Hand& h) {
    if(this != &h) {
        this->board = h.board;
        this->cards.clear();

        // Push back all the hands from the given hand
        for(int i=0;i<h.cards.size();i++) {
            this->cards.push_back(h.cards[i]);
        }
    }
    return *this;
}

Card Hand::draw() {
    Card c = board->draw();
    try{
        cards.push_back(c);
    }
    catch (exception e){
        throw e;
    }
    return c;
}

void Hand::discard(Card c){
    board->discard(c);
    vector<Card> card;
    card.push_back(c);
    remove(card);
}

void Hand::remove(vector<Card> c) {

    // Have to check if all the cards exist
    for(int i = 0; i < c.size(); i++) {
        if(!(contains(c[i]))) {
            throw CardDoesNotExistException();
        }
    }

    // Then go ahead and erase the cards if they do
    for(int i = 0; i < c.size(); i++) {
        for(int j = 0; j < cards.size(); j++) {
            if(c[i] == cards[j]) {
                cards.erase(cards.begin()+j);
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

bool Hand::contains(Card c){
    return find(cards.begin(), cards.end(), c) != cards.end();
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