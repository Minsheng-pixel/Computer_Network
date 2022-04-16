import sys
from socket import *
import re
# TODO: import socket libraries


NUM_TRANSMISSIONS=200
if (len(sys.argv) < 2):
  print("Usage: python3 " + sys.argv[0] + " relay_port")
  sys.exit(1)
assert(len(sys.argv) == 2)
relay_port=int(sys.argv[1])

# TODO: Create a relay socket to listen on relay_port for new connections
tcp_relay = socket(AF_INET, SOCK_STREAM)
# TODO: Bind the relay's socket to relay_port
tcp_relay.bind(('127.0.0.1', relay_port))
# TODO: Put relay's socket in LISTEN mode
tcp_relay.listen(5)
# TODO: Accept a connection first from sender.py (accept1)
(comm_socket1, client_addr2) = tcp_relay.accept()
# TODO: Then, accept a connection from receiver.py (accept2)
(comm_socket2, client_addr2) = tcp_relay.accept()
# Repeat NUM_TRANSMISSIONS times
for i in range(NUM_TRANSMISSIONS):
  # TODO: Receive data from sender socket (the return value of accept1)
  # Be careful with the length of data you receive
  data= comm_socket1.recv(201).decode("ascii")
  # TODO: Check for any bad words and replace them with the good words
  data=data.replace(r'virus', 'groot')
  data=data.replace(r'worm', 'hulk')
  data=data.replace(r'malware', 'ironman')
  # Replace 'virus' with 'groot'
  # Replace 'worm' with 'hulk'
  # Replace 'malware' with 'ironman'

  # TODO: and forward the new string to the receiver socket (the return value of accept2)
  comm_socket2.send(data.encode("ascii"))
  # TODO: print data that was relayed
  print("relayed: "+ data)
# TODO: Close all open sockets
tcp_relay.close()