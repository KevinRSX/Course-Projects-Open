from socket import *
from threading import *

addresses_dic = {}

def server_init():
    """initialization of IP address and port according to user input"""
    LOCAL_IP = input("Please input the local IP address: ")
    LOCAL_PORT = int(input("Please input the port number you want to bind: "))
    ADDR = (LOCAL_IP, LOCAL_PORT)
    ACCEPTOR = socket(AF_INET, SOCK_STREAM)
    ACCEPTOR.bind(ADDR)
    ACCEPTOR.listen(5)
    print("Listening...")
    AC_THREAD = Thread(target=accept_connection, args=(ACCEPTOR,))
    AC_THREAD.start()
    sending(LOCAL_IP)
    ACCEPTOR.close()

def accept_connection(ACCEPTOR):
    """handling incoming connections from clients"""
    while True:
        try:
            client, client_address = ACCEPTOR.accept()
            connection_message = client_address[0] + " has connected";
            broadcast(connection_message)
            addresses_dic[client] = client_address[0]
            Thread(target=recv_msg, args=(client,)).start()
        except ConnectionAbortedError:
            break

def sending(LOCAL_IP):
    while True:
        msg = input()
        if msg != "quit":
            broadcast(msg, LOCAL_IP)
        else:
            broadcast("The server has quitted")
            break

def recv_msg(client):
    while True:
        msg = client.recv(2048).decode()
        if msg != "quit":
            broadcast(msg, addresses_dic[client])
        else:
            quit_msg = addresses_dic[client] + " has quitted."
            del addresses_dic[client]
            client.close()
            broadcast(quit_msg)
            break

def broadcast(msg, prefix=""):
    """broadcast the message to all clients"""
    if prefix == "":
        to_send = msg
    else:
        to_send = prefix + " says: " + msg
    print(to_send)
    for sock in addresses_dic:
        sock.send(to_send.encode())
