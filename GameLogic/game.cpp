/*
    HAND AND FOOT
    Game code manages the player, teams and the board as a whole.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: game.cpp
*/
#include <iostream>
#include "game.h"
#include "card.h"
#include "errors.cpp"

// Shows what piles can be played in
int GS[11] = {1, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};

Player::Player(Team* t, Board* b){
    hand=foot=NULL;
    team = t;
    reset(b);
}

Player::~Player(){
    delete hand;
    delete foot;
}

void Player::draw(){
    if(inFoot){
        foot->draw();
    }
    else{
        hand->draw();
    }
}

void Player::discard(Card c){
    if(inFoot){
        foot->discard(c);
    }
    else{
        hand->discard(c);
        if(hand->cards.size() == 0){
            inFoot = true;
        }
    }
}

void Player::pickUp(){
    if(inFoot){
        foot->pickUp();
    }
    else{
        hand->pickUp();
    }
}

bool Player::play(vector<Card>* c){
    
    if(!team->meld){
        int score;

        // Counts up the cards to see if a player can meld
        for(int i = 0; i < 11; i++){
            for(int j = 0; j < c[i].size(); j++){
                score += c[i][j].cardValue;
            }
        }
        if(score < 70){
            throw NotEnoughToMeldExcpetion();
        }
    }
    
    bool clean = false;
    int cardNum = 0;
    int numWilds = 0;

    // Create the two piles together in a temp array, see if
    // the two piles combined are valid, then combine them if so.
    for(int i = 0; i < 11; i++){
        numWilds = 0;
        vector<Card> temp = team->groups[i].cards;
        temp.insert(temp.end(), c[i].begin(), c[i].end());

        // Determines if there are too many wilds in the pile to
        // place more wilds in and counts the number of cards being
        // placed down
        if(temp.size() > 0){
            for(int j = 0; j < temp.size(); j++){
                if(temp[j].wild){
                    numWilds++;
                }
                else{
                    if(temp[j].value != GS[i]){
                        throw NotCorrectGroupException();
                    }
                }
                cardNum++;
            }

            // If therer are not 3 cards in the pile OR there are more wilds
            // than the number card then not valid
            if(temp.size() < 3 || numWilds > (temp.size()/2)){
                throw NotEnoughCardsException();
            }

            if(numWilds == 0 && temp.size() > 6){
                clean = true;
            }
        }

    }

    if(inFoot){
        if(cardNum == foot->cards.size()){
            throw DiscardEndsGameException();
        }

        if((cardNum == foot->cards.size() - 1) && !clean){
            throw NoCleanBookException();
        }
    }

    vector<Card> toRemove;

    for(int i = 0; i < 11; i++){
        for(int j = 0; j < c[i].size(); j++){
            toRemove.push_back(c[i][j]);
        }
    }

    if(inFoot){
        foot->remove(toRemove);
    }
    else{
        hand->remove(toRemove);
        if(hand->cards.size() == 0){
            inFoot = true;
        }
    }

    for(int i = 0; i < 11; i++){
        for(int j = 0; j < c[i].size(); j++){
            team->groups[i].cards.push_back(c[i][j]);
            if(c[i][j].wild){
                team->groups[i].dirty = true;
            }
        }
    }
    return true;
}

int Player::score(){
    return foot->score() + hand->score();
}

void Player::reset(Board* b){
    delete hand;
    delete foot;
    inFoot = false;

    hand = new Hand(b);
    foot = new Hand(b);
}

vector<Card> Player::peek(){
    vector<Card> topCards;
    topCards.push_back(hand->peek());
    topCards.push_back(foot->peek());
    return topCards;
}

void Player::swap(){
    Hand* temp;
    temp = hand;
    hand = foot;
    foot = temp;
}

Team::Team(Board* b){
    meld = false;
    p1 = new Player(this, b);
    p2 = new Player(this, b);
}

Team::~Team(){
    delete p1;
    delete p2;
}

void Team::reset(Board* b){
    meld = false;
    for(int i = 0; i < 11; i++){
        groups[i].cards.clear();
        groups[i].dirty = false;
    }

    p1->reset(b);
    p2->reset(b);
}

int Team::score(){
    int score = 0;
    for(int i = 0; i < 11; i++){
        score += groups[i].score();
    }
    return score - (p1->score() + p2->score());
}

BoardState::BoardState(int ts){
    teams = ts;
    b = new Board(ts*3);
    for(int i = 0; i < ts; i++){
        Team* team = new Team(b);
        t.push_back(team);
        scores.push_back(0);
    }
}

BoardState::~BoardState(){
    for(int i = 0; i < teams; i++){
        delete t[i];
    }
    delete b;
}

void BoardState::reset(){
    for(int i = 0; i < teams; i++){
        scores[i] += t[i]->score();
    }
    delete b;
    b = new Board(teams);
    for(int i = 0; i < teams; i++){
        t[i]->reset(b);
    }
}