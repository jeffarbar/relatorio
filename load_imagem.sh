docker build -t jeffersonfarias/trajeto-relatorio:latest  -f Dockerfile .
docker login --username=jeffersonfarias
docker push jeffersonfarias/trajeto-relatorio:latest
