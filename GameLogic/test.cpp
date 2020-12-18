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