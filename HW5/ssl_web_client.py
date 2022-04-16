#!/usr/bin/env python3
import ssl
import pprint
import urllib
from socket import *
import argparse
from typing import Dict, Any
from pathlib import Path
import http.client


FILE_DIR: Path = Path(__file__).parent.resolve()
# TODO: Pick the right port number that corresponds to the SSL/TLS port
SSL_PORT: int = 443


def create_ssl_socket(website_url: str) -> ssl.SSLSocket:
    # TODO: Create a TCP socket and wrap it in an SSL context.
    hostname = website_url
    sock = socket(AF_INET, SOCK_STREAM)
    context = ssl.create_default_context()
    ssl_socket = context.wrap_socket(sock, server_hostname=hostname)
    ssl_socket.connect((hostname, SSL_PORT))
    print(ssl_socket.version())
    return ssl_socket

def craft_https_request_string(page: str, website_url: str) -> str:
    #  TODO: Craft a well-formatted HTTP GET string.
    if len(page) == 0:
        page = "/"
    get_string = f"GET {page} HTTP/1.1\r\nHost: {website_url}\r\n\r\n"
    return get_string


def get_peer_certificate(ssl_socket: ssl.SSLSocket) -> Dict[str, Any]:
    # TODO: Get the peer certificate from the connected SSL socket.
    peer_cert = ssl_socket.getpeercert()
    return peer_cert


def send_ssl_https_request(ssl_socket: ssl.SSLSocket, request_string: str) -> str:
    # TODO: Send an HTTPS request to the server using the SSL socket.
    ssl_socket.send(request_string.encode("ascii"))
    response = ssl_socket.recv(1048).decode("ascii")
    # context = ssl.SSLContext()
    # conn = http.client.HTTPSConnection("www.example.com", port=SSL_PORT, context=context)
    # conn.request("GET", 'url' == request_string)
    # response = conn.getresponse()
    # response = response.read(1024).decode('utf-8')
    # conn.close()

    return response


def main(args):
    website_url = args.website_url
    # TODO: Implement the helper functions to connect to a remote SSL server.
    ssl_socket = create_ssl_socket(website_url)
    cert = get_peer_certificate(ssl_socket)
    request_string = craft_https_request_string(args.html_doc, website_url)
    pprint.pprint(cert)
    response = send_ssl_https_request(ssl_socket, request_string)
    pprint.pprint(response.split("\r\n"))
    ssl_socket.close()



if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "-w",
        "--website-url",
        default="www.example.com",
        type=str,
        help="The website URL we query.",
    )
    parser.add_argument(
        "-d",
        "--html-doc",
        default="/index.html",
        type=str,
        help="The html document we want to fetch from the URL.",
    )
    # Parse options and process argv
    arguments = parser.parse_args()
    main(arguments)
