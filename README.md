#How to run MongoDB as Docker Container

1. pull mongo image 
```
docker pull mongo
```
2. run mongo container
```
docker run -d -p 27017:27017 --name mongodb mongo -v /data/db:/data/db

```
#How to run application

./gradlew bootRun

#How to call endpoint

1. call
   ```
   http://localhost:9898/ids/?ids=1,2 
   http://localhost:9898/ids/?ids=ALL 
   http://localhost:9898/ids/?ids
    ```
2. login 
   ```
   [user: user password: user]
   ```
