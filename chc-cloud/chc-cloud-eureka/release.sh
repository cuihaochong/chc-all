#!/bin/bash
echo 'start upload eureka jar'

mvn clean package -DskipTests -f pom.xml

scp -P 22 target/eureka-server.jar root@47.93.131.24:/usr/chc/apps/jar/eureka/eureka-server.jar.new
echo 'end upload eureka jar'
ssh -p 22 root@47.93.131.24 "sh /usr/chc/apps/jar/eureka/eureka.sh"

