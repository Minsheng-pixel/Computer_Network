#!/usr/bin/env python3
import ssl
import argparse
from pathlib import Path
import socket


FILE_DIR: Path = Path(__file__).parent.resolve()
HOST: str = "localhost"


def create_ssl_context(cert_file: str) -> ssl.SSLContext:
    # TODO: Create an SSL context for the server side. You need to load your certificate.
    #ctx = ssl.SSLContext(ssl.PROTOCOL_TLS_CLIENT)
    #certfile = Path(cert_file)
    #ctx.load_verify_locations(certfile)
    ctx = ssl.create_default_context(ssl.Purpose.CLIENT_AUTH)
    ctx.load_cert_chain(cert_file)
    return ctx


def create_server_socket(host_ip: str, host_port: int) -> socket.socket:
    # TODO: Create a TCP server socket that is configured correctly.
    bindsocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    bindsocket.bind((host_ip, host_port))
    bindsocket.listen(5)
    return bindsocket


def wait_for_ssl_connection(ssl_context: ssl.SSLContext, server_socket: socket.socket) -> ssl.SSLSocket:
    # TODO: Wait for an SSL connection and wrap the new socket in an SSL context.
    newsocket, fromaddr = server_socket.accept()
    connstream = ssl_context.wrap_socket(newsocket, server_side=True)
    # Hint: You should call accept here.
    return connstream


def launch_server(server_port: int, cert_file: str) -> bytes:
    # TODO: Use the helper functions to create an SSL server.
    ctx = create_ssl_context(cert_file)

    bindsocket = create_server_socket(HOST, server_port)
    connstream = wait_for_ssl_connection(ctx, bindsocket)
    data = connstream.recv(1024)
    print("Received client SSL message %s", data.decode("ascii"))
    # TODO: Do not forget to close the socket.

    return data


def main(args):
    launch_server(args.server_port, args.cert_file)


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "-s",
        "--server-port",
        default="8000",
        type=int,
        help="The port the server will listen on.",
    )
    parser.add_argument(
        "-c",
        "--cert-file",
        default="cert.pem",
        type=str,
        help="The certificate file the server will use for SSL.",
    )
    # Parse options and process argv
    arguments = parser.parse_args()
    main(arguments)
