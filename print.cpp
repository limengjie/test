#include "print.h"

//using namespace std;

struct KV kv;
int k = 10;

void print()
{
	std::cout << "hello world" << std::endl;
	std::cout << "key : " << kv.key << ", val : " << kv.val << std::endl;
	std::cout << "k = " << k << std::endl;
}
