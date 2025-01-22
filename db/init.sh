#!/bin/bash
echo "Creating sharding database $SHARD_DB"
mysql -u root -p$MYSQL_ROOT_PASSWORD -h localhost -e "CREATE DATABASE $SHARD_DB" 
mysql -u root -p$MYSQL_ROOT_PASSWORD -h localhost $SHARD_DB  < /db/init.sql
echo "Initalize shard done"