#!/usr/bin/env python3
import socket
import argparse
import random
from pathlib import Path
from typing import Tuple
from socket import *

FILE_DIR: Path = Path(__file__).parent.resolve()
HOST: str = "localhost"

# TODO: Choose a P value that is shared with the server.
P: int = 23
private_key = 5


def exchange_base_number(sock: socket, server_port: int) -> int:
    proposal = random.randint(1, 100)
    sock.connect(("127.0.0.1", server_port))
    sock.send(str(proposal).encode("ascii"))
    # TODO: Connect to the server and propose a base number.
    # TODO: This should be a random number.
    print("Base proposal successful.")
    return proposal


def calculate_shared_secret(x: int, y: int, z: int) -> int:
    # TODO: Calculate the shared secret and return it
    calculation = (z ** private_key) % P
    return calculation


def generate_shared_secret(server_port: int) -> Tuple[int, int, int]:
    tcp_client = socket(AF_INET, SOCK_STREAM)
    x = exchange_base_number(tcp_client, server_port)
    # TODO: Create a socket and send the proposed base number to the server.
    print("Base int is %s" % x)
    y = (x ** private_key) % P
    # TODO: Calculate the message the client sends using the secret integer.
    print("Y is %s" % y)
    # TODO: Send it to the server.
    data = tcp_client.recv(4096).decode("ascii")
    tcp_client.send(str(y).encode("ascii"))
    rx_int = int(data)
    # TODO: Calculate the secret based on the server reply.
    print("Int received from peer is %s" % rx_int)
    secret = calculate_shared_secret(x, y, rx_int)
    print("Shared secret is %s" % secret)
    tcp_client.close()
    # TODO: Do not forget to close the socket.
    # TODO: Return the base number, the private key, and the shared secret
    return x, y, secret


def main(args):
    if args.seed:
        random.seed(args.seed)
    generate_shared_secret(args.server_port)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()

    parser.add_argument(
        "-s",
        "--server-port",
        default="8000",
        type=int,
        help="The port the client will connect to.",
    )
    parser.add_argument(
        "--seed",
        dest="seed",
        type=int,
        help="Random seed to make the exchange deterministic.",
    )
    # Parse options and process argv
    arguments = parser.parse_args()
    main(arguments)
