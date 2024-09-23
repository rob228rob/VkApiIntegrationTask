# Разработка системы взаимодействия с VK API

## Описание
Система включает в себя следующие основные компоненты:
- **Spring Web**
- **Basic Auth & Registration** (Spring Security 6)
- **Кэширование** с помощью Redis
- **Сервис интеграции** с VK API
- **OpenAPI документация**
- **Контейнеризация** с помощью Docker

## Запуск Docker Compose
Для запуска приложения с помощью Docker Compose выполните следующие шаги:

1. **Убедитесь, что Docker и Docker Compose установлены на вашем компьютере.**

2. **Перейдите в директорию с файлом `docker-compose.yml`:**
   ```bash
   cd /path/to/project
   ```

3. **Запустите Docker Compose:**
   ```bash
   docker-compose up --build
   ```

   Эта команда создаст и запустит все сервисы, указанные в файле `docker-compose.yml`.

4. **Запуск в фоновом режиме:**
   Если вы хотите запустить сервисы в фоновом режиме, добавьте флаг `-d`:
   ```bash
   docker-compose up -d
   ```

5. **Остановка сервисов:**
   Чтобы остановить работающие сервисы, используйте:
   ```bash
   docker-compose down
   ```

6. **Просмотр логов:**
   Чтобы просмотреть логи сервисов, выполните:
   ```bash
   docker-compose logs
   ```

## Формы
### Форма логина
![Форма логина](https://github.com/user-attachments/assets/897d48a9-4e7c-4fb2-bd3b-7077c1a762cc)

### Форма регистрации
![Форма регистрации](https://github.com/user-attachments/assets/68513798-c9c2-48f2-96a0-dcffc1e861ac)

### Форма для отправки запроса
![Форма для отправки запроса](https://github.com/user-attachments/assets/8dd2cee8-4972-4179-a996-614857cb9962)

## Пример успешного ответа
![Пример успешного ответа](https://github.com/user-attachments/assets/77782d16-588d-4b86-ae1c-e452c6a1104b)

## Документация
Все методы контроллеров задокументированы по адресу: /swagger-ui/index.html


