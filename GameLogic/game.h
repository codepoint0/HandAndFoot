#ifndef GAME
#define GAME
#include <cstdlib>
#include <vector>
#include "card.h"

using namespace std;

class Team;
class Player;

class Player{
    private:
        
    public:
        Hand* hand;
        Hand* foot;
        bool inFoot;
        Team* team;
        int playerID;

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
        
    public:
        Team(Board* b);
        ~Team();

        Player* p1;
        Player* p2;

        bool meld;
        Group groups[11];

        int score();
        void reset(Board* b);
};

class BoardState{
    private:

    public:
        BoardState(int ts);
        ~BoardState();

        Board* b;
        vector<Team*> t;
        vector<int> scores;
        int teams;

        void reset();
};
#endif