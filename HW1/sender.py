import sys
import os
import random
import string
from socket import *
#TODO: import socket libraries

from socket import *
NUM_TRANSMISSIONS=200

if (len(sys.argv) < 3):
  print("Usage: python3 " + sys.argv[0] + " relay_port test_filename")
  sys.exit(1)
assert(len(sys.argv) == 3)
relay_port=int(sys.argv[1])

# Read test cases
test_filename=sys.argv[2]
test_file = open(test_filename)
testcases = test_file.readlines()


# TODO: Create a socket for the sender
tcp_sender= socket(AF_INET, SOCK_STREAM)
# TODO: Connect sender to the relay at the relay_port
tcp_sender.connect(("127.0.0.1", relay_port))
# Wait until the receiver has also connected to the relay
input("Press enter to start transmissions")

# Repeat NUM_TRANSMISSIONS times
for i in range(NUM_TRANSMISSIONS):
  # Read a randomly generated string from testcases list
  data=testcases[i]
  # TODO: Send this to the relay server for it to relay to the receiver
  tcp_sender.send(data.encode("ascii"))
  # print debugging information
  print("sent: " + data)

# TODO: Close any open sockets
tcp_sender.close()