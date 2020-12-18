#ifndef GAME
#define GAME

/*
    HAND AND FOOT
    Game code manages the player, teams and the board as a whole.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: game.h
*/
#include <cstdlib>
#include <vector>
#include "card.h"

using namespace std;

// Declared above to ensure that a player can know about its
// team and a team can know about its players
class Team;
class Player;

/*
    Players have a hand and a foot they can play from.
    They have specific things they can do in their turn
    represented by the methods and helper methods.
*/
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

        // The biggest method, used to determine
        // if a play is valid
        bool play(vector<Card>* c);
        int score();

        // Gives a player a new hand and a new foot
        void reset(Board* b);

        // Allows the player to peek at the bottom
        // of their hands at the start of the game
        // to determine which deck they want to use
        vector<Card> peek();

        // Swaps so that their hand is now their foot
        // and their foot is now their hand (start game)
        void swap();
};

/*
    A team consists of two players. It determines if the
    players can meld, and where each of their cards can
    be played
*/
class Team{
    private:
        
    public:
        Team(Board* b);
        ~Team();

        Player* p1;
        Player* p2;

        bool meld;

        // This is where players can lay
        // down their cards
        Group groups[11];

        int score();

        // Resets the players
        void reset(Board* b);
};

/*
    A BoardState keeps track of all teams, players, hands
    boards and scores. It will be the determiner if the game
    has ended
*/
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