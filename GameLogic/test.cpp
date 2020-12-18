/*
    HAND AND FOOT
    A file to run the game and test all the inputs from a player

    Created by: Joshua Speckman and Tyler Gordon
    Date: December 15, 2020
    Modified: December 17, 2020
    File: test.cpp
*/
#include "controller.h"
#include <iostream>
#include <sstream>

int main(){
    std::srand(time(NULL));
    Controller c(2);
    stringstream ss;
    c.play(ss);
    return 0;
}