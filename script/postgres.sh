sudo docker run --name postgres -p 5432:5432 -v /postgres:/var/lib/postgresql/data -e POSTGRES_PASSWORD=password -d postgres
