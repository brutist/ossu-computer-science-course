import socket

mysock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
mysock.connect(('data.pr4e.org', 80))
cmd = 'GET http://data.pr4e.org/intro-short.txt HTTP/1.0\r\n\r\n'.encode()
mysock.send(cmd)

header = ''
while True:
    data = mysock.recv(512)
    if len(data) < 1:
        break
    temp = data.decode()
    header += temp
  
mysock.close()

end_of_header = 0

for char in header:
  end_of_header = header.find('\n\n')

header = header[:end_of_header]

file = open('socket1_header.txt', 'w')

file.write(header)

file.close()
  