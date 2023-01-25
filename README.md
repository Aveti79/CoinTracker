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

# Getting started locally

1. Clone this repository
2. Open project in your IDE then compile and run or <br>
by using command in terminal `./mvnw spring-boot:run` when in main directory
3. If everything goes smoothly app should be running locally on `localhost:8080`

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
│   │               ├───util            <- utilities classes, which helping in managing and viewing data
│   │               └───view            <- viewController.java with endpoints returning view
│   └───resources
│       ├───db
│       │   └───migration               <- sql db migrations, currently don't needed becasue spring data generates it automatically
│       ├───languages                   <- languages properties files containing translations
│       ├───static                      <- static files used in templates like css stylesheets and JS files
│       └───templates                   <- templates returned by endpoints in viewController.java
└───test
    └───java
        └───com
            └───aveti
                └───CoinTracker
                    └───logic
```
