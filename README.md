# PortScanner
Scan any TCP port on any host.

/** 
 * -i for defining ip address
 * -p for defining port
 * -p2 for defining range default same as p
 * -t number of threads to use for connections   Default 50
 * -o timeOut limit in millis. default 1000ms. Range 0 - Integer.MAX_VALUE
 */

 //checks port 56 on localhost
-i localhost -p 56  

//checks all ports of localhost and uses pool of 100 threads
-i localhost -p 0 -p2 65535 -t 100
