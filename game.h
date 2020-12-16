#include <cstdlib>
#include <vector>
#include "card.h"

using namespace std;

class Player{
    private:
        Hand hand;
        Hand foot;
        bool inFoot;
        Team* t;
        void draw();
        bool discard(Card c);
        void pickUp();
        bool play();
        int score();
    public:
        Player(Team* t, Board* b);        
};

class Team{
    private:
        Group groups[11];
        Player* p1;
        Player* p2;
        bool meld;
        int score();
    public:
        Team(BoardState* b);
        Team(const Team& t);
        ~Team();

        Team& operator=(const Team& other);
};

class BoardState{
    private:
        Board* b;
        Team t[];
        int scores[];
        void reset();
    public:
        BoardState();
        ~BoardState();
};