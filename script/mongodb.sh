docker run --name mongodb -v /mongodb:/data/db -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=boris -e MONGO_INITDB_ROOT_PASSWORD=boris123 -d mongo
