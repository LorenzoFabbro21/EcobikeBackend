#! /bin/bash

# Set color
YELLOW='\033[1;33m'
# reset color
NC='\033[0m'

COMPOSE_FILE="compose.yml"

# Check if kompose is installed
if ! command -v kompose &> /dev/null
then
    echo -e "${YELLOW}Kompose isn't installed.${NC}"
    exit 1
fi

kompose convert -f "$COMPOSE_FILE"

echo "# Deployments" > deployment.yaml
echo "# Services" > services.yaml
echo "# Persistent Volume Claims" > persistentVolumeClaim.yaml

for f in *-deployment.yaml; do
  (echo "---"; cat "${f}") >> deployment.yaml
  rm -f "${f}"
done

for f in *-service.yaml; do
  (echo "---"; cat "${f}") >> services.yaml
  rm -f "${f}"
done

for f in *-persistentvolumeclaim.yaml; do
  (echo "---"; cat "${f}") >> persistentVolumeClaim.yaml
  rm -f "${f}"
done

echo -e "${YELLOW}Creation of deployment.yaml, services.yaml, and persistentVolumeClaim.yaml completed.${NC}"
