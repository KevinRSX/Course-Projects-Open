from socket import *
import threading

class Client:
    """
    Client class of the text messenger
    attributes: clientSocket, remoteIP, portNum, contFlag
    """
    def __init__(self):
        # assume that IP address and port number input have no mistakes
        self.remoteIP = input("Please input the remote IP address: ")
        self.portNum = int(input("And the port number: "))
        self.clientSocket = socket(AF_INET, SOCK_STREAM)
        self.contFlag = True

    def receive(self, clientSocket):
        while self.contFlag:
            try:
                rText = clientSocket.recv(2048).decode()
                if rText == "quit":
                    print("System message: It looks like the server has quitted...")
                    self.contFlag = False
                print("\n" + self.remoteIP + " says: " + rText)
            except OSError:
                self.contFlag = False


    def mfunc(self):
        self.clientSocket.connect((self.remoteIP, self.portNum))
        self.receiveThread = threading.Thread(target=self.receive, args=(self.clientSocket, ))
        self.receiveThread.start()
        while self.contFlag:
            try:
                sText = input()
                print("You say: " + sText)
                self.clientSocket.send(sText.encode())
                if sText == "quit":
                    self.contFlag = False
            except OSError:
                self.contFlag = False
        self.clientSocket.close()


class Server:
    """
    Server class of the text messenger
    attributes: doorSock, serverSocket, localIP, clientAddr, portNum, contFlag
    """
    def __init__(self):
        self.localIP = input("Please input local machine IP: ")
        self.portNum = int(input("Please input the port number you want to bind: "))
        self.doorSock = socket(AF_INET, SOCK_STREAM)
        self.contFlag = True

    def create(self):
        self.doorSock.bind((self.localIP, self.portNum))
        self.doorSock.listen(1)
        print("Listening...")
        self.serverSocket, self.clientAddr = self.doorSock.accept()
        acFlag = input("Accept connection from " + self.clientAddr[0] + "? (y/n)")
        if acFlag == 'y' or acFlag == 'Y':
            return True
        else:
            return False

    def receive(self, serverSocket):
        while self.contFlag:
            try:
                rText = serverSocket.recv(2048).decode()
                if rText == "quit":
                    print("System message: It looks like the client has quitted...")
                    self.contFlag = False
                print("\n" + self.clientAddr[0] + " says: " + rText)
            except OSError:
                self.contFlag = False


    def mfunc(self):
        self.receiveThread = threading.Thread(target=self.receive, args=(self.serverSocket, ))
        self.receiveThread.start()
        while self.contFlag:
            sText = input()
            print("You say: " + sText)
            self.serverSocket.send(sText.encode())
            if sText == "quit":
                self.contFlag = False
        self.doorSock.close()
        self.serverSocket.close()

def clientProgram():
    """
    client program that controls the program flow
    """
    while True:
        cli = Client()
        cli.mfunc()
        quitFlag = input("Quit? (y/n)")
        if quitFlag == 'y' or quitFlag == 'Y':
            break

def serverProgram():
    """
    server program that controls the program flow
    """
    while True:
        ser = Server()
        if ser.create():
            ser.mfunc()
        quitFlag = input("Quit? (y/n)")
        if quitFlag == 'y' or quitFlag == 'Y':
            break

# main function
print("(C)onnect\n(W)ait\n(Q)uit")
choice = input("Please choose the role you want to play: ")

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
