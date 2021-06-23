#include "logger.h"

extern struct KV kv;
extern int k;


void modify()
{
    kv.key = 2;
    kv.val = 20;
    k = 100;
}

