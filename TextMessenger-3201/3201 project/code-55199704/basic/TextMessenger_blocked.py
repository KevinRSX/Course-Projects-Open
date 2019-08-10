from socket import *

def clientProgram():
    while True:
        serverName = input("Please input the remote IP address: ")
        portNum = input("And the port number: ")
        clientSocket = socket(AF_INET, SOCK_STREAM)
        clientSocket.connect((serverName, int(portNum)))

        while True:
            message = input("You say: ")
            clientSocket.send(message.encode())
            if message == "quit":
                break
            response = clientSocket.recv(2048).decode()
            print(serverName + " says: " + response)
            if response == "quit":
                print("The server has left...")
                break

        quitFlag = input("Do you want to quit (y/n)? ")
        if quitFlag == "Y" or quitFlag == "y":
            clientSocket.close()
            break

    print("See you next time!")


def serverProgram():
    serverName = input("Please input local machine IP: ")
    portNum = input("Please input the port number you want to bind: ")
    serverSocket = socket(AF_INET, SOCK_STREAM)
    serverSocket.bind(('', int(portNum)))
    while True:
        serverSocket.listen(1)
        print("This server is ready to receive")
        print("Listening...")
        (connectionSocket, clientAddress) = serverSocket.accept()
        clientName = clientAddress[0]
        acFlag = input("Accept connection from " + clientName + "? (y/n) ")
        if acFlag == 'n' or acFlag == 'N':
            break
        while True:
            message = connectionSocket.recv(2048).decode()
            print(clientName + " says: " + message)
            if message == "quit":
                print("The client has left...")
                break
            response = input("You say: ")
            connectionSocket.send(response.encode())
            if response == "quit":
                break
        quitFlag = input("Do you want to quit (y/n)? ")
        if quitFlag == "Y" or quitFlag == "y":
            serverSocket.close()
            break

    print("See you next time!")


# main
print("(C)onnect\n(W)ait\n(Q)uit")
choice = input("Please choose the role you want to play:")

while True:
    if choice == 'Q' or choice == 'q':
        exit()
    elif choice == 'C' or choice == 'c':
        clientProgram()
        break
    elif choice == 'W' or choice == 'w':
        serverProgram()
        break
    print("\n(C)onnect\n(W)ait\n(Q)uit")
    choice = input("Please input a valid choice again: ")
