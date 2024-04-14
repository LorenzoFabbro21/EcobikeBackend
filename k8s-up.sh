#! /bin/bash

# set color
YELLOW='\033[1;33m'
# reset color
NC='\033[0m'

DIRECTORY="."

for FILE in $DIRECTORY/*.yaml; do
    echo -e "${YELLOW}Applying $FILE...${NC}"
    kubectl apply -f $FILE
done

echo -e "${YELLOW}All files have been applied.${NC}"
