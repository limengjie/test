#add a comment in makefile
MKFILE      = Makefile

COMPILECPP  = g++ -g -O0 -Wall -Wextra -rdynamic -std=gnu++17

CPPSOURCE   = testLogger.cpp logger.cpp print.cpp
#CPPSOURCE   = bin_tree.cpp BSTtwoSum.cpp
#CPPSOURCE   = bin_tree.cpp 517.cpp
#CPPSOURCE   = 225.cpp linkedList.cpp
#CPPHEADER   = phase10.h   command.h
EXECBIN     = output

${EXECBIN} : ${CPPSOURCE}
	${COMPILECPP} -o $@ ${CPPSOURCE}


clean:
	-rm ${EXECBIN} *.o
