#include <iostream>
#include <sys/stat.h>
#include <pthread.h>
#include <string.h>
#include <stdint.h>
#include <stdbool.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <time.h>
#include <getopt.h>
#include <limits.h>
#include <ctype.h>
#include <iostream>
#include <sys/types.h>
#include <fcntl.h>
#include <sys/mount.h>
#include "logger.h"
#include "print.h"


#define STATE_POS 0



//struct KV kv{};


int main() {
    printf("test log\n");

    print();
    modify();

    //kv.key = 1;
    //kv.val = 10;

    return 0;
}
