# Spring Boot and MySQL on Kubernetes

This repository contains a simple Spring Boot application configured to use MySQL as its database, deployed on a Kubernetes cluster.

## Prerequisites

- Docker
- Kubernetes (Minikube or a Kubernetes cluster)
- kubectl
- Maven (for building the Spring Boot application)

## Architecture

The deployment includes the following components:
- A MySQL database
- A Spring Boot application
- Kubernetes Services and Deployments for both

## Setup and Deployment

### 1. Build the Spring Boot Application

First, clone the repository and build the Spring Boot application using Maven.

```sh
git clone https://github.com/SunnySinghs/k8s-training.git
cd k8s-training
mvn clean install
```

### 2. Dockerize the Spring Boot Application

Create a `Dockerfile` at the root of your project and build the Docker image.

```
FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-app-0.0.1-SNAPSHOT.jar spring-boot-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","spring-boot-app-0.0.1-SNAPSHOT.jar"]
```

Build the Docker image:

```
docker build -t sprint-boot-app .
```

### 3. Push the Docker Image to a Registry

Push the image to Docker Hub or any other container registry.

```
docker login 
docker push <your-username>/sprint-boot-app
````

Docker Hub Image URL

```
https://hub.docker.com/repository/docker/sourabhsingh/spring-boot-app/general
```

### 4. Create Kubernetes Configurations

Create a k8s directory and add the following Kubernetes YAML configuration files:

1. **Secret**: This file will store sensitive information, such as database usernames and passwords, in an encoded format to ensure security.
2. **ConfigMap**: This file will hold non-sensitive configuration data, such as the database URL, which your applications can use.
3. **StatefulSet**: This file defines the configuration for the MySQL database container, ensuring that the state of the database is maintained across pod restarts.
4. **PersistentVolumeClaim**: This file requests and allocates persistent storage for the MySQL database, ensuring data persistence even if the database pod is deleted or rescheduled.
5. **NodePort Headless Service**: This service will be configured to allow internal cluster connections to the MySQL database, without exposing it externally.
6. **Deployment**: This file contains the configuration for the Spring Boot application container, specifying how it should be deployed and managed in the cluster.
7. **LoadBalancer Service**: This service will expose the Spring Boot application to external traffic, allowing users to access the application from outside the cluster.
8. **Horizontal Pod Autoscaler(HPA)**: This configuration automatically enables the Spring Boot application to scale in response to varying loads, ensuring optimal performance and resource usage.

Use the command below to create a pod with the MySQL client, which will allow you to connect to the deployed database:

```
kubectl run -it --rm --image=mysql:5.6 --restart=Never mysql-client -- mysql -h <Headless Service Name> -p <Password>
```

After connecting to the database, run the following query to create a database:

```
Create database employee;
```

Delete the MySQL client pod

```
kubectl delete pod mysql-client
```

### 5. Deploy to Kubernetes

Apply the configurations in the correct order using `kubectl apply`.

```
kubectl apply -f k8s/mysql-secret.yaml
kubectl apply -f k8s/mysql-configmap.yaml
kubectl apply -f k8s/mysql-statefulset.yaml
kubectl apply -f k8s/mysql-headless-service.yaml
kubectl apply -f k8s/webapp-deployment.yaml
```

### 6. Access the Application

Retrieve the external IP of the Spring Boot service and open a web browser to access your application.

```
kubectl get services springboot-app-service
```

### 7. Application APIs

Spring Boot application APIs curl

1. **Create employee**

```
curl --location --request POST 'http://<External IP>:8080/app/employees' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 1,
    "name": "Sam",
    "email": "sam.sam@gmail.com"
}'
```

2. **Fetch all employees**

```
curl --location --request GET 'http://<External IP>:8080/app/employees'
```

3. **Update employee**

```
curl --location --request PUT 'http://<External IP>:8080/app/employees' \
--header 'Content-Type: application/json' \
--data-raw '{
    "id": 2,
    "name": "Emma",
    "email": "emma.emma@gmail.com"
}'
```

4. **Delete employee**

```
curl --location --request DELETE 'http://<External IP>:8080/app/employees/{id}'
```

5. **To put the load on the pod to see HPA in action**

```
curl --location --request GET 'http://<External IP>:8080/app/load/{id}'
```

### 8. Video Tutorial URL
This URL is accessible to limited audience

```
https://github.com/SunnySinghs/k8s-training
```

## Cleaning Up

To delete the resources, use `kubectl delete` with the appropriate configuration files.

```
kubectl delete -f k8s/mysql-secret.yaml
kubectl delete -f k8s/mysql-configmap.yaml
kubectl delete -f k8s/mysql-statefulset.yaml
kubectl delete -f k8s/mysql-headless-service.yaml
kubectl delete -f k8s/webapp-deployment.yaml
```

## Conclusion

You have successfully deployed a Spring Boot application using MySQL on a Kubernetes cluster. Feel free to explore and modify the setup according to your needs.
