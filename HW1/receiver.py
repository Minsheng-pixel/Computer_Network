import sys
import os
import random
import string
#TODO: import socket libraries
from socket import *

NUM_TRANSMISSIONS=200
if (len(sys.argv) < 2):
  print("Usage: python3 " + sys.argv[0] + " relay_port")
  sys.exit(1)
assert(len(sys.argv) == 2)
relay_port=int(sys.argv[1])

# TODO: Create a socket for the receiver
tcp_receiver= socket(AF_INET, SOCK_STREAM)
# TODO: Connect this socket to the relay at relay_port
tcp_receiver.connect(("127.0.0.1", relay_port))
# Iterate NUM_TRANSMISSIONS times
for i in range(NUM_TRANSMISSIONS):
  # TODO: Receive any data relayed from the relay (i.e., any data sent by the sender to the relay)
  data = tcp_receiver.recv(201)
  # TODO: Print received data
  print("received: "+data.decode("ascii"))
# TODO: Close any open sockets
tcp_receiver.close()