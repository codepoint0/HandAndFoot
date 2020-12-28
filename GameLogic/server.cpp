// Server side C/C++ program to demonstrate Socket programming
#include "controller.h"


int main(int argc, char const *argv[])
{
    std::srand(time(NULL));
    Controller c(2);

    c.SetUpServer();
    while (true)
    {
    }
}