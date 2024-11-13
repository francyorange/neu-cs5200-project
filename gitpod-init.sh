#!/bin/bash

sudo apt update && sudo apt install mariadb-server -y && sudo service mariadb start

sed -e 's/\s*\([\+0-9a-zA-Z]*\).*/\1/' << EOF | mariadb-secure-installation
      # current root password (emtpy after installation)
    y # Set root password?
    password # new root password
    password # new root password
    y # Remove anonymous users?
    y # Disallow root login remotely?
    y # Remove test database and access to it?
    y # Reload privilege tables now?
EOF
