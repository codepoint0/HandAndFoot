#ifndef CONTROLLER
#define CONTROLLER

/*
    HAND AND FOOT
    This file is the messanger between the outside world and the game.

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: controller.h
*/

#include <sstream>

#include "game.h"

class Controller{
    private:
        BoardState* b;

    public:
        Controller(int teams);
        ~Controller();

        // Determines the entire game.
        int play(stringstream& ss);
};
#endif