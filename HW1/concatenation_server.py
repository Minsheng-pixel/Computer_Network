from socket import *
# TODO: import socket library
import sys
import random
import string
RAND_STR_LEN = 10

# Function to generate random strings
def rand_str():
    ret = ''
    for i in range(RAND_STR_LEN):
        ret += random.choice(string.ascii_lowercase + string.ascii_uppercase + string.digits)
    return ret


NUM_TRANSMISSIONS=10
if (len(sys.argv) > 3) or len(sys.argv) < 2:                                    
    print("Usage: python3 "  + sys.argv[0] + " server_port [random_seed]")
    sys.exit(1)
                                                                                
if len(sys.argv) == 3:                                                          
    random_seed = int(sys.argv[2])                                              
    random.seed(random_seed)  

server_port=int(sys.argv[1])                       

# TODO: Create a socket for the server on localhost
tcp_server= socket(AF_INET, SOCK_STREAM)
# TODO: Bind it to a specific server port supplied on the command line
tcp_server.bind(('127.0.0.1', server_port))
# TODO: Put server's socket in LISTEN mode
tcp_server.listen()
# TODO: Call accept to wait for a connection
(comm_socket, client_addr) = tcp_server.accept()
# Repeat NUM_TRANSMISSIONS times
for i in range(NUM_TRANSMISSIONS):
  # TODO: receive data over the socket returned by the accept() method
    data = comm_socket.recv(4096)
  # TODO: print out the received data for debugging
    print (data.decode('ascii'))
  # TODO: Generate a new string of length 10 using rand_str
    str = rand_str()
  # TODO: Append the string to the buffer received
    data =  str + data.decode("ascii")
  # TODO: Send the new string back to the client
    comm_socket.send(data.encode("ascii"))
# TODO: Close all sockets that were created
comm_socket.close()