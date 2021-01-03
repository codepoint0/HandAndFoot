// Server side C/C++ program to demonstrate Socket programming
#include "server.h"
#include <vector>


int main(int argc, char const *argv[])
{
    
    Server s;
    s.SetUpServer();

    s.runningGames = 0;
    while (true)
    {
    }
}

Server::~Server(){
    for(int i = 0; i < games.size(); i++){
        delete games[i];
    }
}

void Server::SetUpServer(){
    buffer[4096] = {0}; 

    // Creating socket file descriptor 
    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) 
    { 
        perror("socket failed"); 
        exit(EXIT_FAILURE); 
    } 
       
    // Forcefully attaching socket to the port 8080 
    if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR | SO_REUSEPORT, 
                                                  &opt, sizeof(opt))) 
    { 
        perror("setsockopt"); 
        exit(EXIT_FAILURE); 
    } 
    address.sin_family = AF_INET; 
    address.sin_addr.s_addr = INADDR_ANY; 
    address.sin_port = htons( PORT ); 
       
    // Forcefully attaching socket to the port 8080 
    if (bind(server_fd, (struct sockaddr *)&address,  
                                 sizeof(address))<0) 
    { 
        perror("bind failed"); 
        exit(EXIT_FAILURE); 
    }
    ListenForNewClients();
}

void Server::ListenForNewClients(){
    srand(time(NULL));
    while(true){
        Client c;
        if (listen(server_fd, 3) < 0) 
        { 
            perror("listen"); 
            exit(EXIT_FAILURE); 
        } 
        if ((c.socket = accept(server_fd, (struct sockaddr *)&address,  
                        (socklen_t*)&addrlen))<0) 
        { 
            perror("accept"); 
            exit(EXIT_FAILURE); 
        } 

        std::cout << "Waiting" << std::endl;
        valread = read(c.socket, buffer, sizeof(buffer)); 
        std::stringstream input(buffer);
        int t;
        input >> t;
        std::cout << t << std::endl;

        if(t == 4 || t == 6 || t == 8){
            int code = (rand() % 9000) + 1000;
            while(find(codes.begin(), codes.end(), code) != codes.end()){
               code = (rand() % 10000) + 999;
            }
            codes.push_back(code); 
            std::cout << code << std::endl;
            runningGames++;
            std::string codeString = "CO:" + to_string(code);
            Controller* ctr = new Controller(t/2, code, c);
            send(c.socket , codeString.c_str(), strlen(codeString.c_str()), 0 );
            games.push_back(ctr);
        }
        else {
            std::cout << "Else" << std::endl;
            for(int i = 0; i < games.size(); i++){
                if(games[i]->code == t){
                    games[i]->AcceptNewClient(c);
                    std::string conf = "CO:" + to_string(games[i]->code);
                    send(c.socket , conf.c_str(), strlen(conf.c_str()) , 0 );
                    if(games[i]->b->teams*2 == games[i]->clients.size()){
                        thread s = std::thread(&Controller::play, games[i]);
                        s.detach();
                        std::cout << "abc" << std::endl;
                    }
                }
            }
        }
        std::fill_n(buffer, 4096, 0);
    }
}