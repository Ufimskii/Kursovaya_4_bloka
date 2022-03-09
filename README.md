##Курсовой проект

### О проекте
#### Проект это комплексное автоматизированное тестирование сервиса для покупки туров через интернет-банк. Купить тур можно двумя способами:
* По дебетовой карте
* В кредит
#### Приложение данные по картам не обрабатывает, а пересылает их банковским сервисам:
* сервис платежей (Payment Gate)
* кредитный сервис (Credit Gate)
#### Приложение в собственной СУБД сохраняет информацию о том, успешно ли он был совершён и каким способом.

### Запуск Авто-Тестов.

1. Запустить контейнеры в терминале командой: 
**docker-compose up -d**
2. Запустить SUT, для запуска в новой вкладке в терминале вводим следующую команду:
* Для СУБД MySQL: 
**java -jar ./artifacts/aqa-shop.jar**
* Для СУБД PostgreSQL: 
**java -jar ./artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app**
3. Запустить автотесты командой:
* Для MySQL:
**gradlew clean test**
* Для PostgreSQL: 
**gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app**

* **P.S.** Дополнительно через  флаг -Dservice.url можно задать адрес для внешнего сервиса для тестирования
    * например: **gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app -Dservice.url=http://172.168.1.8:8080/**
4. Сгенерировать отчеты: 
* **gradlew allureReport**
* **gradlew allureServ**
5. Для завершения работы allureServe выполнить команду: **Ctrl + С далее Y**
