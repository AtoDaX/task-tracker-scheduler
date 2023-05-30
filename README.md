# task-tracker-scheduler
Spring Boot приложение с двумя модулями - Spring Scheduler и Spring AMQP.

Задача сервиса - раз в сутки итерировать всех пользователей, формировать для них отчёты об отогах дня, а также формировать email для отправки. Сформированные сообщения отправляются в RabbitMQ очередь.

## CI/CD
В рамках проекта с помощью GitHub Actions для репозитория реализована автоматическая сборка docker-образов и их загрузка в публичный репозиторий ([dockerhub](https://hub.docker.com/)) при пуше в master ветку.
