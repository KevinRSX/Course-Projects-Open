from socket import *
from threading import *
import server
import client

print("(C)onnect\n(W)ait\n(Q)uit")
choice = input("Please choose the role you want to play: ")

while True:
    if choice == 'Q' or choice == 'q':
        exit()
    elif choice == 'W' or choice == 'w':
        server.server_init()
        contFlag = input("Are you sure to quit? (y/n): ")
        while contFlag == 'n' or contFlag == 'N':
            server.server_init()
            contFlag = input("Are you sure to quit? (y/n): ")
        break
    elif choice == 'C' or choice == 'c':
        client.client_init()
        contFlag = input("Are you sure to quit? (y/n): ")
        while contFlag == 'n' or contFlag == 'N':
            client.client_init()
            contFlag = input("Are you sure to quit? (y/n): ")
        break
    print("\n(C)onnect\n(W)ait\n(Q)uit")
    choice = input("Please input a valid choice again: ")
