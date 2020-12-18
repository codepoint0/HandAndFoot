#ifndef CONTROLLER
#define CONTROLLER

#include <sstream>

#include "game.h"

class Controller{
    private:
        BoardState* b;

    public:
        Controller(int teams);
        ~Controller();

        int play(stringstream& ss);
};
#endif