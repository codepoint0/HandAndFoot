#include <iostream>
#include <string>
#include "controller.h"

vector<string> split(string str, string token){
    vector<string>result;
    while(str.size()){
        int index = str.find(token);
        if(index!=string::npos){
            result.push_back(str.substr(0,index));
            str = str.substr(index+token.size());
            if(str.size()==0)result.push_back(str);
        }else{
            result.push_back(str);
            str = "";
        }
    }
    return result;
}

int main(){
    std::string input;
    std::getline(cin, input);
    vector<Card> c[11];
    vector<std::string> cardSet;
    std::stringstream t;
    std::string temp;


    cardSet = split(input, "|");
    
    

    for(int i = 0; i < 11; i++){
        vector<std::string> cardStrings;
        t << cardSet[i];
        cardStrings = split(t.str(), ",");
        std::cout << cardSet[i] << std::endl;
        int value,suit,deckID;
        vector<Card> cards;
        if(!(cardSet[i].compare("") == 0)){
                for(int j = 0; j < cardStrings.size(); j++){
                t.flush();

                t << cardStrings[j];
                t >> value >> suit >> deckID;
                Card test(false,value,suit,0,deckID);
                cards.push_back(test);

            }
        }

        t.flush();
        c[i] = cards;
        cards.clear();
    }


    for(int i = 0; i < 11; i++){
        std::cout << c[i].size() << " ";
    }
}