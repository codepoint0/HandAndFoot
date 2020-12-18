#include <exception>

struct EmptyWreathException : public std::exception{
    const char * what() const throw(){
        return "Empty Wreath Exception: The wreath ran out of cards and the pile was empty!";
    }
};

struct EmptyPileException : public std::exception{
    const char * what() const throw(){
        return "Empty Pile Exception: The pile cannot be picked up if it is empty.";
    }
};

struct CardDoesNotExistException : public std::exception{
    const char * what() const throw(){
        return "Card Does Not Exist Exception: The card you have selected does not exist. Check it and try again.";
    }
};

struct NotEnoughToMeldExcpetion : public std::exception{
    const char * what() const throw(){
        return "";
    }
};

struct NotCorrectGroupException : public std::exception{
    const char * what() const throw(){
        return "";
    }
};

struct NotEnoughCardsException : public std::exception{
    const char * what() const throw(){
        return "";
    }
};

struct DiscardEndsGameException : public std::exception{
    const char * what() const throw(){
        return "";
    }
};

struct NoCleanBookException : public std::exception{
    const char * what() const throw(){
        return "";
    }
};