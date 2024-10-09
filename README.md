# Code Lounge - Social Network

![Java Spring](https://img.shields.io/badge/java-spring-cian?logo=spring) ![Micro Services](https://img.shields.io/badge/micro-services-pink) ![Docker Compose](https://img.shields.io/badge/docker-compose-white) ![PostgreSQL](https://img.shields.io/badge/postgre-sql-blue) ![Apache Kafka](https://img.shields.io/badge/apache-kafka-red) ![Websockets](https://img.shields.io/badge/websockets-green) ![Maven](https://img.shields.io/badge/maven-blue) ![SonarQube](https://img.shields.io/badge/sonar-qube-blue) ![Grafana](https://img.shields.io/badge/grafana-orange)
![ex-1](https://imgur.com/rGdqaQn.png)
## Description & Summary

Ready to use installation of this application is available on site: https://socialnetwork.ragaev.keenetic.pro

Code Lounge - social network primary for developers which providing wide functionality for users including:

- 🔑 Registration/Login/Logout
- 👍 Creating, editing and deleting likes, posts, comments, reactions
- 💬 Messaging/Friends/Dialogs
- 🧑‍🎤 Profile personalisation
- 🔔 Notification and it's settings
- ⚙️ Administration and control/Role pattern

### Backend technology stack:

- ☕ Java 17
- 💾Postgres & Redis
- 🌱 Spring 3.2.4 (Boot, Web, Security, Websockets, Cloud,...)
- 🐦 Apache Kafka
- 📦 Maven
- 📊 SonarQube/JaCoCo
- 📈 Grafana/Prometheus
- 🐳 Docker/Docker Compose
- ☁️ AWS (Yandex Cloud)
- 🧪 Tests (JUnit, Mockito, TestContainers)

### Frontend technology stack:

- 🌐 Vue
- 📡 Axios

*frontend technology stack not fully described, because of project is primary backend one

Grafana is available on site: http://grafana.ragaev.keenetic.pro

Swagger REST API docs you can find on site: http://swagger.ragaev.keenetic.pro

## Setting up & Starting
### Local
- 📦 Download project from repository or clone it through git system
- 🐳 Download and run Docker desktop
- 🖥️ Open directory ./docker (from project root) in terminal
- Run command: "docker compose -f docker-compose.local.yml -f docker-compose.services.local.yml up -d" or, if you have UNIX-like OS: "docker-compose -f docker-compose.local.yml -f docker-compose.services.local.yml up -d"
- It's done! Now you can go to http://localhost and it's ready for using.
### Global, if you want to use your own server
- 📦 Download project from repository or clone it through git system
- Copy project to your own gitlab-repository
- Setting up gitlab-runner for CI/CD process. (More detail instructions about runner https://timeweb.cloud/tutorials/ci-cd/ustanovka-i-ispolzovanie-gitlab-runner)
- Configuring all necessary settings for running gitlab-runner in gitlab. Below is the list of CI/CD variables:
![alt text](https://i.imgur.com/llu67ui.png)
- Set up and configure SonarQube, while copying folder ./docker/sonarqube and running command "docker compose -f ./sonarqube/docker-compose.yml up -d"
- Change string in file ./frontend/src/settings/plugins.js for your ip address for proper websocket running:
    Vue.use(chat, { server: 'socialnetwork.ragaev.keenetic.pro' });
- Rebuild frontend, using docker command: docker build --no-cache -f ./frontend/Dockerfile -t "YOUR_DOCKER_REPOSITORY/frontend" ./frontend
- Pushing docker image to your docker hub : docker push "YOUR_DOCKER_REPOSITORY/frontend"

## Using examples & GUI
Interaction user and system is doing through frontend. Below you can find some examples:
![ex-1](https://imgur.com/7pvF9TX.png)
🔑(Login & Registration)
![ex-2](https://imgur.com/GHGZ7PO.png)
👍(news example)
![ex-3](https://imgur.com/YORwwkG.png)
💬(Chat example)
![ex-4](https://imgur.com/1jmaR0u.png)
🧑‍🎤(Personalization)

You can also call REST API functions by using built-in Swagger docs: http://localhost:9090/swagger-ui.html

## Project structure
Project structure is a multi module maven-project, where most of modules are microservices, realized by model Spring MVC (except frontend) 
There are additional modules, which doing service functions, for example JaCoCoAggregator needs for aggregating results scanning of code coverage.
Briefly project structure based on the Spring Cloud architecture including gateway and microservices discovery service.
## License
Common project license - CC BY-NC-ND (Creative Commons Attribution-NonCommercial-NoDerivatives)
## Contacts
- Main developers - telegram: @sergei_ragaev @DJ_FRX

