#!/usr/bin/env python3
import socket
import argparse
from pathlib import Path
from typing import Tuple


FILE_DIR: Path = Path(__file__).parent.resolve()
HOST: str = "localhost"


# TODO: Choose a P value that is shared with the client.
P: int = 23
private_key = 4


def calculate_shared_secret(x: int, y: int, z: int) -> int:
    # TODO: Calculate the shared secret and return it
    calculation = (z ** private_key) % P
    return calculation


def exchange_base_number(sock: socket.socket) -> int:
    # TODO: Wait for a client message that sends a base number.
    # TODO: Return a message that the base number has been received.
    data = sock.recv(4096).decode("ascii")
    proposal = int(data)
    return proposal


def launch_server(server_port: int) -> Tuple[int, int, int]:
    tcp_server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    tcp_server.bind(('127.0.0.1', server_port))
    tcp_server.listen()
    (comm_socket, client_addr) = tcp_server.accept()
    x = exchange_base_number(comm_socket)
    # TODO: Create a server socket. can be UDP or TCP.
    # TODO: Wait for the client to propose a base for the key exchange.
    print("Base int is %s" % x)
    # TODO: Wait for the nonce computed by the client.
    # TODO: Also reply to the client.
    y = (x ** private_key) % P
    comm_socket.send(str(y).encode("ascii"))
    print("Y is %s" % y)
    rx_int = comm_socket.recv(4096).decode("ascii")
    rx_int = int(rx_int)

    print("Int received from peer is %s" % rx_int)
    # TODO: Compute the shared secret using the secret number.

    secret = calculate_shared_secret(x, y , rx_int)
    print("Shared secret is %s" % secret)
    # TODO: Do not forget to close the socket.
    # TODO: Return the base number, the secret integer, and the shared secret
    base = x
    secret_integer = private_key
    shared_secret = secret
    tcp_server.close()
    return base, secret_integer, shared_secret



def main(args):
    launch_server(args.server_port)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "-s",
        "--server-port",
        default="8000",
        type=int,
        help="The port the server will listen on.",
    )
    # Parse options and process argv
    arguments = parser.parse_args()
    main(arguments)
