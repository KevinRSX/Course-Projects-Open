from socket import *
from threading import *

def client_init():
    client_sock = socket(AF_INET, SOCK_STREAM)
    REMOTE_IP = input("Please input the remote IP address: ")
    PORT = int(input("And port number: "))
    ADDR = (REMOTE_IP, PORT)
    client_sock.connect(ADDR)
    Thread(target=receive, args=(client_sock,)).start()
    while True:
        msg = input()
        client_sock.send(msg.encode())
        if msg == "quit":
            break
    client_sock.close()

def receive(client_sock):
    """receiving messages"""
    while True:
        try:
            msg = client_sock.recv(2048).decode()
            print(msg)
            if msg == "quit":
                break
        except OSError:
            client_sock.close()
            break
