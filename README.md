# Code Lounge - Social Network

![Java Spring](https://img.shields.io/badge/java-spring-cian?logo=spring) ![Micro Services](https://img.shields.io/badge/micro-services-pink) ![Docker Compose](https://img.shields.io/badge/docker-compose-white) ![PostgreSQL](https://img.shields.io/badge/postgre-sql-blue) ![Apache Kafka](https://img.shields.io/badge/apache-kafka-red) ![Websockets](https://img.shields.io/badge/websockets-green) ![Maven](https://img.shields.io/badge/maven-blue) ![SonarQube](https://img.shields.io/badge/sonar-qube-blue) ![Grafana](https://img.shields.io/badge/grafana-orange)
![ex-1](https://i.imgur.com/mLnGxgG.png)
## Введение & Описание

Code Lounge - социальная сеть с уклоном на аудиторию разработчиков, предоставляющая широкий функционал пользователям, включающая в себя:

- 🔑 Регистрация/Вход/Выход
- 👍 Создание, редактирование и удаление лайков, постов, комментариев, реакций
- 💬 Переписки/Друзья/Диалоги
- 🧑‍🎤 Персонализация профиля
- 🔔 Уведомления/Оповещения и их настройка
- ⚙️ Администрирование и управление/Ролевая модель

### Стек серверных технологий:

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

### Стек фронтенд технологий:

- 🌐 Vue
- 📡 Axios

*стек технологий frontend не полный, поскольку проект позиционирует себя в первую очередь как сервер

## Установка & Запуск
### Локально
- 📦Скачать проект архивом или клонировать через систему git
- 🐳 Скачать Docker если отсутствует и запустить его
- 🖥️Открыть папку /docker (от корня проекта) в терминале
- Прописать команду docker compose -f docker-compose.local.yml -f docker-compose.services.local.yml up или при работе с UNIX-подобными системами docker-compose -f (далее точно так же как в Windows)
- Готово! Теперь приложение можно использовать и тестировать на локальном ПК
### Глобально, с использованием своего сервера
- 📦Скачать проект архивом или клонировать через систему git
- Скопировать проект в свой gitlab-репозиторий
- Настроить gitlab-runner на сервере, на котором должны работать раннеры для запуска кода. (Подробнее про настройку runner https://timeweb.cloud/tutorials/ci-cd/ustanovka-i-ispolzovanie-gitlab-runner)
- Сконфигурировать все необходимые настройки для запуска gitlab-runner в gitlab. Ниже приведен список обязательных переменных:
![alt text](https://i.imgur.com/llu67ui.png)
- Установить и сконфигурировать сервер SonarQube на удаленной машине, скопировав на неё содержимое папки
  ./docker/sonarqube и запустив команду docker compose -f ./sonarqube/docker-compose.yml up -d
- Изменить в файле ./frontend/src/settings/plugins.js строку на ip вашего сервера:
    Vue.use(chat, { server: 'socialnetwork.ragaev.keenetic.pro' }); // <- для стэнда, чтобы работал websocket.
- Пересобрать фронт, используя команду докера:
  docker build --no-cache -f ./frontend/Dockerfile -t "skillboxgroup/frontend" ./frontend
- Выгрузить образ фронтэнда на докер хаб:
  docker push "skillboxgroup/frontend"

## Примеры использования & Интерфейс
Взаимодействия пользователя с системой осуществляется через фронтенд. Ниже приведен ряд примеров:
![ex-1](https://i.imgur.com/mLnGxgG.png)
🔑(Вход & Регистрация)
![ex-2](https://i.imgur.com/ND9m1jy.png)
👍(пример поста)
![ex-3](https://i.imgur.com/bKUU1ol.png)
💬(Пример чата)
![ex-4](https://i.imgur.com/VEcMuII.png)
🧑‍🎤(Персонализация)
## Документация
Ссылка на текущую версию внутренней документации проекта (возможно необходимо запросить доступ)
https://docs.google.com/document/d/1IF1YWKqyIu6gRQVdlz0ENsehh4UVNyK9U7_jFlAjPlI
## Структура проекта
Структура проекта представляет из себя многомодульный maven-проект, где есть ряд большинство модулей - микросервисы, реализованные по модели Spring MVC (за исключением фронтенда) (более подробная структура микросервиса доступна в файле project code style в разделе структура микросервиса)
Присутствуют также доп. модули, которые выполняют служебные функции, например - JaCoCoAggregator нужен для агрегирования результатов сканирования покрытия.
Архитектура проекта более подробно описана в разделе документации, но если кратко, то она реализует архитектуру Srping Cloud через шлюз gateway и сервис обнаружения микросервисов discovery.
##  Участие в разработке (Contributing)
О принятии участии в разработке необходимо заранее договариваться с командой разработчиков (смотреть раздел контактов)!
#### Командная работа
- разработчик создает код, делает коммит и пуш (при работе  с кодом обязательно установить плагин SonarLint для среды разработки)
- далее нужно создать Merge-request с актуальной веткой для mr (актуальная ветка уточняется у администрации, сейчас - develop)
- Далее будет запущен пайплайн, если он проходит проверку SonarQube и корректно компилируется, можно назначать проверку других разработчиков (желательно более опытных)
- Если разработчик принимает MR, то сделать слияние, если нет, то изучать комментарии, которые он оставил и скорректировать, после чего запросить повторной проверки
## Лицензия
Общая лицензия проекта - CC BY-NC-ND (Creative Commons Attribution-NonCommercial-NoDerivatives)
Для дополнительной информации и целей использования проекта необходимо уточнять у администрации и разработчиков
## Контактные данные
- Главные разработчики - telegram: @sergei_ragaev @DJ_FRX

