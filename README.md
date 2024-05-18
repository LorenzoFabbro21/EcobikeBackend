# Ecobike
Welcome to Ecobike! This is a project that aims to build a platform for local businesses to showcase their products and services. This project is part of TAAS course of Computer Science Maaster's Degree at University of Turin.

## Index

- [Used Technologies](#used-technologies)
- [Get started](#get-started)
- [Run the project with Docker](#run-the-project-with-docker)
- [Run the project on Kubernetes](#run-the-project-on-kubernetes)
- [How to use the application](#how-to-use-the-application)

## Used Technologies
In this project are used the following technologies:

### Frontend is built with:
- [Angular](https://angular.io/) - Angular is a Single Page Application (SPA) development framework open-sourced by Google
- [PrimeNG](https://primeng.org/) -  UI Suite for Angular
- [Compodoc](https://compodoc.app/) - Documentation tool
- [JWT](https://www.npmjs.com/package/@auth0/angular-jwt) - Library provides an HttpInterceptor which automatically attaches a JSON Web Token to HttpClient requests.

### Backend
- [Spring Boot](https://spring.io/projects/spring-boot) - for building the RESTful APIs
- [Spring Security](https://spring.io/projects/spring-security) - for authentication and authorization and **oauth2** support
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa) - for database access
- [Spring Web](https://spring.io/guides/gs/serving-web-content/) - for RESTful APIs
- [Lombok](https://projectlombok.org/) - for reducing boilerplate code
- [PostgreSQL](https://www.postgresql.org/) - Relational Database
- [Docker](https://www.docker.com/) - for local development
- [Docker Compose](https://docs.docker.com/compose/) - for local development
- [Maven](https://maven.apache.org/) - Dependency Management
- [JWT](https://jwt.io/) - JSON Web Tokens
- [RabbitMQ](https://www.rabbitmq.com/) - MQTT broker for messaging
- [Eureka](https://spring.io/guides/gs/service-registration-and-discovery/) - Service registration and discovery
- [OpenAPI](https://swagger.io/specification/) - API documentation
- [Kubernetes](https://kubernetes.io/) - for deployment and container orchestration

### Miscellaneous
- [Postman](https://www.postman.com/) - API platform for building and using APIs
- [Git](https://git-scm.com/) - Version control system
- [GitExtension](https://gitextensions.github.io/) - UI tool for managing Git repositories

## Get started
First of all, you need to have installed the following tools:
- [Docker](https://www.docker.com/) Docker Engine
- [Docker Compose](https://docs.docker.com/compose/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)
- [Kubernets on Docker Desktop](https://docs.docker.com/desktop/kubernetes/) you can enable it from Docker Desktop settings
- [minikube](https://minikube.sigs.k8s.io/docs/start/) you can use it instead of Docker Desktop but requires some changes described in the [Run the project on Kubernetes](#run-the-project-on-kubernetes) section

You can choose to run the project in two ways:
- [Run the project with Docker](#run-the-project-with-docker)
- [Run the project on Kubernetes](#run-the-project-on-kubernetes)

In both cases, you need first to clone the repository:

```git clone https://github.com/LorenzoFabbro21/Ecobike_Taas.git ```

### Run the project with Docker
Using Docker Compose, you can run the project with the following command:

```docker-compose up -d```

(Use -d flag to run it in detached mode)


### Run the project on Kubernetes
As sayd at begin you have to enable Kubernetes on Docker Desktop.
Here we assume you have installer docker desktop and enabled kubernetes on it. Or you have installed minikube. And cloned the repository.

##### Start with Docker Destkop Context
After satisfying the previous requirements, if you can **run** the project executing the following script:

```./k8s-up.sh```

##### Start with Minikube Context
After satisfying the previous requirements, if you can **run** the project executing the following steps:

Open a cmd and type the following command: ```minikube start --cpus=4 --memory=8192```

and then run the following script:

```./k8s-up-minikube.sh```

to stop the execution of minikube do:

```minikube stop```

##### Stop
If you want to **stop** the project for each context, you can execute the following script:

```./k8s-down.sh```


## How to use the application
After running the project on Docker or Kubernets, you can access the application at the following URL:

```http://localhost:32000```
