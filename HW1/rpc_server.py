#TODO: import socket library
import sys
from socket import *
NUM_TRANSMISSIONS=10
if(len(sys.argv) < 2):
  print("Usage: python3 " + sys.argv[0] + " server_port")
  sys.exit(1)
assert(len(sys.argv) == 2)
server_port = int(sys.argv[1])

def primality_check(num):
    if num > 1:
        # check for factors
        for i in range(2, num):
            if (num % i) == 0:
                return "no"
                break
        return "yes"

# TODO: Create a socket for the server
UDP_server  = socket(AF_INET, SOCK_DGRAM)
# TODO: Bind it to server_port 
UDP_server.bind(("127.0.0.1",server_port))
# Repeat NUM_TRANSMISSIONS times
for i in range(NUM_TRANSMISSIONS):
    # TODO: Receive RPC request from client
     data = UDP_server.recvfrom(4096)
    # TODO: Turn byte array that you received from client into a string variable called rpc_data
     rpc_data = data[0].decode("ascii")
     address = data[1]
    # TODO: Parse rpc_data to get the argument to the RPC.
    # Remember that the RPC request string is of the form prime(NUMBER)
     rpc_data = rpc_data[6:-1]
    # TODO: Print out the argument for debugging
     print(rpc_data)
    # TODO: Compute if the number is prime (return a 'yes' or a 'no' string)
     result = primality_check(int(rpc_data))
    # TODO: Send the result of primality check back to the client who sent the RPC request
     UDP_server.sendto(result.encode("ascii"), address)
# TODO: Close server's socket
UDP_server.close()
