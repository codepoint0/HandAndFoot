#include <cstdlib>
#include <vector>
#include "card.h"

using namespace std;

class Player{
    private:
        Hand* hand;
        Hand* foot;
        Team* team;
        bool inFoot;
    public:
        Player(Team* t, Board* b);
        ~Player();

        void draw();
        void discard(Card c);
        void pickUp();
        bool play(vector<Card>* c);
        int score();
        void reset(Board* b);
        vector<Card> peek();
        void swap();
};

class Team{
    private:
        Player* p1;
        Player* p2;
    public:
        Team(Board* b);
        ~Team();

        bool meld;
        Group groups[11];

        int score();
        void reset(Board* b);
};

class BoardState{
    private:
        Board* b;
        Team* t[];
        int scores[];
        void reset();
    public:
        BoardState();
        ~BoardState();
};