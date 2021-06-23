#include "print.h"

using namespace std;

struct KV kv{};
int k = 10;

void print()
{
	cout << "hello world" << endl;
    cout << "key : " << kv.key << ", val : " << kv.val << endl;
	cout << "k = " << k << endl;
}
