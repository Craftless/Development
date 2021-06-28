#include <iostream>
using namespace std;

int difficulty;

bool play(int difficulty)
{
    std::cout << "\n\nHELLO \n";

    int a = random() * difficulty;
    int b = random() * difficulty;
    int c = random() * difficulty;

    int input;

    std::cout << a;
    std::cout << b;
    std::cout << c;

    std::cin >> input;

    if (input == a + b + c)
    {
        std::cout << "Correct";
        return true;
    }

    std::cout << "bad";
    return false;

    std::cout << std::endl;
}

int main()
{
    while (true)
    {
        if (play(difficulty))
        {
            difficulty++;
        }
        std::cin.clear();
        std::cin.ignore();
    }
    return 0;
}