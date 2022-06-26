.PHONY: clean all 

CC = gcc 
CFLAGS = -Wall -g
BIN = server client
OBJECTS = $(BIN:=.o)
#SOURCES = $(BIN:=.c)

#.SUFFIXES: .c .o


all: $(BIN)

server:server.o

client:client.o

.c.o: 
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	-rm -rf $(BIN) $(OBJECTS) 
