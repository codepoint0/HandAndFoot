/*
    Card code which holds the struct of a card, a group of cards
    the board and a hand. Holds the logic for each of these things.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 15, 2020
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

struct Group{
    vector<Card> cards;
    bool dirty;
    int score();

    Group():
    dirty(false)
    { };
};

class Board{
    private:
        deque<Card> wreath;
        deque<Card> pile;
    public:
        Board(int decks);

        Card draw();
        vector<Card> pickUp();
        void discard(Card c); 
};

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
        int score();
        Card peek();
};