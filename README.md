# CoinTracker
CoinTracking is a web application for tracking your cryptocurrency portfolio.
This app was created to easily and affordably track and analyze our transactions made in the cryptocurrency market.

### From developer
For me as a developer, the process of creating this application has mainly educational goals, 
although the motivation for starting the project was to create a tool that will offer the most important functionalities
from paid services of this type available on the Internet.

### Technologies used
Below there is listed current techstack of application:<br>
- Java 17
- Maven
- Spring Framework `2.7.3`
- H2 Database - for early development and tests
- Thymeleaf
- Holy trio `HTML` `CSS` `JavaScript`
- Data about coins from [CoinGeckoAPI](https://www.coingecko.com/en/api)
- Mockito and JUnit5 for testing

# Getting started locally

1. Clone this repository
2. Open application.properties file `src\main\resources` and change 
   `spring.jpa.hibernate.ddl-auto` to `create` and save file.
2. Then in main directory run command `./mvnw spring-boot:run`
3. Next, remember to change agaian property `ddl-auto` to `validate` or `update`.
4. If everything goes smoothly app should be running locally on `localhost:8080`.
5. When project is builded for the first time your next step is to run 2 endpoints:
   `/api/coins-list/update` and second `/api/coins-details/update`<br>
   NOTICE: This 2 endpoints are pretty heavy so response could take a while.
6. After that application should run smoothly, and you can start adding your transactions :wink:

# How it works and how to use it?

Here is a video presentation of application i recommend to watach: <br>

<p align="center">
    <a href="https://www.youtube.com/watch?v=bKvR75TJlIk">Coin Tracker Presentation</a>
<p>

[![Coin Tracker Presentation](https://i9.ytimg.com/vi_webp/bKvR75TJlIk/maxresdefault.webp?v=63f3ba9f&sqp=CNDzzp8G&rs=AOn4CLDHTZ2TiQa0qlSspUQtxyG0JDul2A)](https://www.youtube.com/watch?v=bKvR75TJlIk)
# Project structure 
```
src
├───main
│   ├───java
│   │   └───com
│   │       └───aveti
│   │           └───CoinTracker
│   │               ├───controller      <- controller classes with endpoints returning data
│   │               ├───locale          <- locale resolver configuration for multilanguage suppport
│   │               ├───logic           <- bussines logic services for processing data
│   │               ├───model           <- model database entities
│   │               │   ├───projection  <- projection of database entities to convert entity to/from primitive types
│   │               │   └───repository  <- jpa repositories for db entities
│   │               │   └───validation  <- custom model validation
│   │               ├───util            <- utilities classes, which helping in managing and viewing data
│   │               └───view            <- viewController.java with endpoints returning view
│   └───resources
│       ├───db
│       │   └───migration               <- sql db migrations, currently don't needed becasue spring data generates it automatically
│       ├───languages                   <- languages properties files containing translations
│       ├───static                      <- static files used in templates like css stylesheets and JS files
│       └───templates                   <- templates returned by endpoints in viewController.java
└───test
    ├───java
    │   └───com
    │       └───aveti
    │           └───CoinTracker
    │               └───logic
    └───resources
```
