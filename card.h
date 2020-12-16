#include <cstdlib>
#include <vector>
#include <deque>

using namespace std;

struct Card{
    bool joker;
    int value;
    int suit;
    int cardValue;
    int deckID;

    Card(bool j, int v, int s, int cv, int ID):
    joker(j), value(v), suit(s), cardValue(cv), deckID(ID)
    { };

    bool operator==(const Card& c);
};

struct Group{
    vector<Card> cards;
    bool dirty;
    bool complete;
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
        vector<Card> cards;
        Board* board;
        void draw();
        void discard(Card c);
        void pickUp();
        void remove(vector<Card> c);
    public:
        Hand(Board* b);
        Hand(const Hand& h);

        Hand& operator=(const Hand& h);
};