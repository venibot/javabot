# javabot
VeniBot, но на Java

# Зависимости
 - JDK 8(можно выше, но всё тестируется на 8). В Linux, основанном на Debian - ```sudo apt install openjdk-8-jdk```, ~~а в других ОС и дистрбутивах мучайтесь сами.~~
 
 - [Maven](https://maven.apache.org)
 
 # Запуск
  - Склонируйте репозиторий(```git clone https://github.com/venibot/javabot```)
  - Зайдите в склонированную папку(```cd javabot```)
  - Создайте в src/main/java/resources конфигурационный файл bot.properties, и укажите в нём token, prefix и errorWebhookUrl(с токеном, префиксом и вебхуком, через который будут отсылаться ошибки соответственно), а также db.properties, со свойствами uri и db(строка для подключеня к базе данных и имя базы данных соответственно)
  - Скомпилируйте проект через ```mvn compile```
  - Запускить скомпилируемый jar-файл ```java -Dfile.encoding=UTF-8 -jar Название файла.jar```
  - Радуйтесь жизни!
  
# Пожелания по боту
По пожеланиям/багам можно создавать Issue/Pull Request или обращаться в ЛС dmemsm#1706

# Поддержать бота
  - Мониторинги:
    - SD.C: https://bots.server-discord.com/728030884179083354
    - BotiCord: https://boticord.top/bot/728030884179083354
    - TopCord: https://bots.topcord.ru/bots/728030884179083354
