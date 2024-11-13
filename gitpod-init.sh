#!/bin/bash

sudo apt update && sudo apt install mariadb-server tomcat9 -y && sudo service mariadb start

sudo mariadb -sfu root <<EOS
GRANT ALL PRIVILEGES on *.* to 'root'@'localhost' IDENTIFIED BY 'password';
FLUSH PRIVILEGES
EOS
