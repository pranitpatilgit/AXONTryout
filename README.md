# AXON-Tryout

## Steps to start the application 

Start AXON Server in Docker Container
    
    docker run -d --name axon-server -p 8024:8024 -p 8124:8124 axoniq/axonserver

Run ProductCartApplication as SpringBoot Application
    
    By Default this app runs on 8084 port
    You can change th port and other things by changing src/main/resources/application.yml
    
## TODO

   - Saga Implementation
   - Change Repository and Code for saving and retrieving
   - Use Mongo as EventStore
   - Use Kafka as EventStore
