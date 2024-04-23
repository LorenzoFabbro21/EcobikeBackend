#! /bin/bash

# Set color variables
YELLOW='\033[1;33m'
NC='\033[0m'

DIRECTORY="."

# Function to apply YAML files in the directory
apply_yaml_files() {
  for FILE in $DIRECTORY/*.yaml; do
      echo -e "${YELLOW}Applying $FILE...${NC}"
      kubectl apply -f $FILE
  done
  echo -e "${YELLOW}All files have been applied.${NC}"
}

# Function to wait for pods associated with services to be ready
wait_for_pods() {
    echo -e "${YELLOW}Waiting for pods to be ready...${NC}"
    kubectl wait --for=condition=Ready pods --all --timeout=300s
    echo -e "${YELLOW}All pods are ready.${NC}"
}

# Function to perform port forwarding for services
port_forward_services() {
    echo -e "${YELLOW}Starting port forwarding...${NC}"
    kubectl port-forward service/frontend 32000:32000 &
    kubectl port-forward service/api-gateway 30080:30080 &
    kubectl port-forward service/authentication-service 30090:30090 &
    echo -e "${YELLOW}Port forwarding started.${NC}"
}

# Main function
main() {
    apply_yaml_files
    wait_for_pods
    port_forward_services
    wait
}
# Execute the main function
main