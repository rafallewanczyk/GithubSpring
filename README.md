# Aplikacja odczytująca api serwisu GitHub
### Wykorzystane zostały:
* Java 17
* Spring Boot 
* Maven
* Testy Spock
* Api serwisu `github.com`

### Uruchomienie
* `mvn spring-boot:run` - w głównej ścieżce projektu - Uruchomienie projektu na domyślnym `localhost:8080`
* `mvn test` - uruchomienie testów 

### Opisy endpoint'ów
* `GET /api/v1/github/{user}/repos` - listowanie repozytoriów użytkownika 
* `GET /api/v1/github/{user}/stars` - suma gwiazdek użytkownika 
* `GET /api/v1/github/{user}/popular` -  suma bajtów repozytoriów według języka, w celu uzyskania najpopularniejszych sortowanie leży po stronie klienta

### Opis rozwiązania 
Projekt został podzielony na pakiety realizujące następujące zadania: 
* `adapter` - komunikowanie się z zewnętrznym serwisem, transfer danych
* `controller` - serwowanie odpowiedzi
* `domain` - realizacja logiki
