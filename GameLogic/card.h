#ifndef CARD
#define CARD

/*
    HAND AND FOOT
    Card code which holds the struct of a card, a group of cards
    the board and a hand. Holds the logic for each of these things.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: card.h
*/
#include <cstdlib>
#include <vector>
#include <deque>

using namespace std;

/*
    Creates a card which holds its value, suit and actual card
    value.
*/
struct Card{
    bool wild;

    // This is the number on the actual card
    int value;
    int suit;

    // This is the gameplay value, for example a 4 is worth
    // 5 points.
    int cardValue;
    int deckID;

    // Constructor to assign the given values to the corresponding
    // value 
    Card(bool j, int v, int s, int cv, int ID):
    wild(j), value(v), suit(s), cardValue(cv), deckID(ID)
    { };

    bool operator==(const Card& c);
};

/*
    Each team has a set of groups which they can
    lay down their cards in. A group is shared in
    a team.
*/
struct Group{
    vector<Card> cards;
    bool dirty;
    
    // Scores the points in the group.
    int score();

    Group():
    dirty(false)
    { };
};

/*
    A Board keeps track of all of the cards which
    are on the board.
*/
class Board{
    private:
        deque<Card> wreath;
        deque<Card> pile;
    public:
        
        // Randomizes the wreath at the start and
        // puts a random card on top of pile
        Board(int decks);

        // Draws a card from the wreath
        Card draw();

        // Picks up the entire pile
        vector<Card> pickUp();

        // Discard onto the top of the pile
        void discard(Card c); 

        // Peeks at the top card of the pile
        Card pilePeek();
};

/*
    A hand (can also be a hand or foot) holds the player's
    cards and communicates with the board what it is going
    to do.
*/
class Hand{
    private:
        Board* board;
    public:
        Hand(Board* b);
        Hand(const Hand& h);

        vector<Card> cards;

        Hand& operator=(const Hand& h);
        void draw();
        void discard(Card c);
        void pickUp();
        void remove(vector<Card> c);

        // Adds up all the points in the player's hand
        int score();
        Card peek();
        bool contains(Card c);
};
#endif