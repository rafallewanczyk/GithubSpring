# Aplikacja odczytująca api serwisu GitHub

## Wykorzystane zostały:

* Java 17
* Spring Boot
* Maven
* Testy Spock
* Api serwisu `github.com`

## Uruchomienie

* `mvn spring-boot:run` - w głównej ścieżce projektu - uruchomienie projektu na domyślnym `localhost:8080`
* `mvn test` - uruchomienie testów

## Opisy endpoint'ów

### GET `/api/v1/github/{user}/repos`

listowanie repozytoriów użytkownika `{user}` \
**Parametry:**

* `user` - nazwa użytkownika na serwisie Github

**Przykładowa odpowiedź**\
lista obiektów gdzie:

* `name` - nazwa repozytorium
* `stars` - liczba gwiazdek repozytorium

```
[
   {
      "name":"-PSD-Flink-annomaly-detection",
      "stars":1
   },
   {
      "name":"AAL",
      "stars":0
   },
   {
      "name":"AI-complete",
      "stars":0
   },
   {
      "name":"cs224n",
      "stars":0
   },
   {
      "name":"deeplearning.ai",
      "stars":0
   }

]
```

### GET `/api/v1/github/{user}/stars`

suma gwiazdek wszystkich publicznych repozytoriów użytkownika Github `{user}` \
**Parametry:**

* `user` - nazwa użytkownika na serwisie Github

**Przykładowa odpowiedź**\
obiekt gdzie:

* `stars` - suma gwiazdek użytkownika

```
{"stars":1}
```

### GET `/api/v1/github/{user}/popular`

listowanie języków programowania używanych przez użytkownika `{user}` wraz z sumą rozmiarów porjektów w bajtach\
**Parametry:**

* `user` - nazwa użytkownika na serwisie Github

**Przykładowa odpowiedź**\
słownik`popularRepos` zawierający klucze reprezentujące języki programowania oraz wartości reprezentujące sumę rozmiarów
repozytoriów w danym języku w bajtach:

* `popularRepos` - słownik par `język : rozmiar`

```
{
   "popularRepos":{
      "C#":1202,
      "QML":4,
      "Java":21341,
      "C++":4820,
      "C":91744,
      "unknown_language":752020,
      "HTML":14351,
      "Jupyter Notebook":534145,
      "Assembly":1,
      "Python":57386
   }
}
```

## Struktura Projektu

```
.
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    ├── main
    │   ├── java
    │   │   └── pl
    │   │       └── allegro
    │   │           └── github
    │   │               ├── adapter
    │   │               │   ├── GithubAdapter.java
    │   │               │   └── GithubRepoDto.java
    │   │               ├── controller
    │   │               │   ├── GithubController.java
    │   │               │   ├── GithubPopularReposResponse.java
    │   │               │   ├── GithubRepoResponse.java
    │   │               │   └── GithubStarsResponse.java
    │   │               ├── domain
    │   │               │   ├── GithubErrorHandler.java
    │   │               │   ├── GithubService.java
    │   │               │   └── NotFoundException.java
    │   │               └── GithubApplication.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── pl
                └── allegro
                    └── github
                        ├── domain
                        │   ├── GithubRepoDtoBuilder.groovy
                        │   └── GithubServiceTest.groovy
                        └── GithubApplicationTests.java

```

### Opis rozwiązania

Projekt został podzielony na pakiety realizujące następujące zadania:

* `pl.allegro.github.adapter` - pakiet odpowiedzialny za komunikowanie się z zewnętrznymi serwisami (tutaj GitHub) w
  jego skład wchodzą:
    * `GithubAdapter.java` - adapter odpowiedzialny za konsumowanie api serwisu Github. Składa się z jedej
      funkcji `getRepos(user)` pobierającej wszystkie publiczne repozytoria zadanego użytkownika
    * `GithubRepoDto.java` - Data Transfer Object reprezentujący pojedyńcze repozytorium użytkownika. Służy do
      przesłania danych dotyczących do warstwy realizującej logikę. Posiada pola
        ```
      name - nazwa repozytorium
      stars - liczba gwiazdek repozytorium
      language - główny język projektu 
      sizeInBytes - rozmiar projektu w bajtach
       ```
* `pl.allegro.github.controller` - pakiet odpowidzialny za serwowanie odpowiedzi do użytkownika, w jego skład wchodzą: 
  * `GithubController.java` - warstwa Controllera wywołująca logikę systemu oraz serwująca odpowiedzi (patrz opisy endpointów)
  * `GithubRepoResponse.java` - klasa realizująca mapowanie `GithubRepoDto` na odpowiedź pod `GET /api/v1/github/{user}/repos`
  * `GithubStarsResponse.java` - klasa realizująca mapowanie `GithubRepoDto` na odpowiedź pod `GET /api/v1/github/{user}/stars`
  * `GithubPopularResponse.java` - klasa realizująca mapowanie `GithubRepoDto` na odpowiedź pod `GET /api/v1/github/{user}/popular`
* `pl.allegro.github.domain` - pakiet odpowiedzialny za realizację logki systemu w jego skład wchodzą: 
  * `GithubService.java`  - pakiet odpowiedzialny za logikę wyliczania danych dotyczących repozytorióœ użytkownia. Składa się z 
    * `getRepos(user)` - zwrócenie repozytoriów użytkownika
    * `sumStars(user)` - sumowanie gwiazdek użytkownika
    * `groupByLanguage(user)` - sumowanie rozmiarów repozytoriów na podstawie języka 
  * `GithubErrorHandler.java` - obsługa błędów zwracanych przez serwis Giuthub
  * `NotFoundException.java` - wyjątek zwracany przy nieistniejącym użytkowniku 

### Testy 
Testy zostały zrealizowane przy pomocy biblioteki `Spock`: 
* `pl.allegro.github.domain` - testy logiki aplikacji: 
  * `GithubServiceTest.groovy` - testy funkcji serwisu `GithubService` dla przypadków zwracających `null`, `[]` oraz poprawne dane 
  * `GithubRepoBuilder.groovy` - fabryka obiektów `GithubRepoDto` na potrzeby ułatwienia testów 
