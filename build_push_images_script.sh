#! /bin/bash

docker-compose build --no-cache
for IMAGE_ID in $(docker images --format "{{.ID}}"); do
    # Ottieni il nome dell'immagine e il suo tag
    IMAGE_NAME=$(docker inspect --format='{{index .RepoTags 0}}' $IMAGE_ID)

    # Fai il push dell'immagine nel tuo registro Docker
    docker push $IMAGE_NAME
done

