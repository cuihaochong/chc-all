#!/bin/bash
echo 'start upload fastdfs jar'

mvn clean package -DskipTests -f pom.xml

scp -P 22 target/fastdfs-server.jar root@47.93.131.24:/usr/chc/apps/jar/fastdfs/fastdfs-server.jar.new
echo 'end upload fastdfs jar'
ssh -p 22 root@47.93.131.24 "sh /usr/chc/apps/jar/fastdfs/fastdfs.sh"

