# ***REST API Weather Forcast Application**
This project is a Gradle multi-module Spring Boot application that fetches weather forecast data from the 
<a href="http://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php?lang=eng" target="_blank">Estonian XML Weather Service API</a>. It consists of two independent modules that can run as separate applications:

Module 1 - Write Microservice: A scheduled service that fetches weather data from a public XML API every 30 minutes and stores it in a PostgreSQL database.

Module 2 - Query Microservice Web API: A REST API service that allows users to query the weather forecast for specific locations or retrieve the day’s forecast for all available locations.

A third module exists to store common configurations or utility functions shared by the two primary modules.

## **🔭 Table of Contents**
- [Project Overview](#-project-overview)
- [Demo](#demo)
- [Tech Stack](#-tech-stack)
- [Getting Started](#-getting-started)
- [Usage Instructions](#usage-instructions)
- [API Documentation](#-api-documentation)
- [Features](#features)
- [Testing](#testing)
- [Docker Support](#docker-support)
- [Contributing](#contributing)
- [License](#license)
- [Connect With Me](#-connect-with-me)

## **🌱 Project Overview**
- Module 1 - Background Process
This service fetches weather data from the Estonian Weather Service API, parses it (excluding wind and sea data), and saves the forecast in a PostgreSQL database.

Database Setup: On startup, the module will automatically set up the required database schema if it does not exist.
Scheduled Task: The @Scheduled annotation is used to run a task every 30 minutes that retrieves and processes the latest forecast data.

- Module 2 - Query Microservice Web API
The web module provides a REST API for querying forecast data:

Endpoints:
GET /forecast/{location}: Takes a location as input and returns the weather forecast for that specific location in JSON format.
GET /forecast/today: Returns the current day's forecast for all locations in JSON format.

Response Structure: Each response includes location, date, temperature, and relevant weather details to make it as user-friendly as possible.


## **💼 Tech Stack**
- **![](https://img.shields.io/badge/Code-Spring_Boot-informational?style=flat&logo=Spring-Boot&color=DB7093)**
- **![](https://img.shields.io/badge/Code-Spring_JPA-informational?style=flat&logo=Spring-JPA&color=CC342D)**
- **![](https://img.shields.io/badge/Test-JUnit5-informational?style=flat&logo=JUnit5&color=003B57)**
- **![](https://img.shields.io/badge/Tools-Maven-informational?style=flat&logo=Gradle&color=430098)**


### **Database:**
- **![](https://img.shields.io/badge/Code-MySQL-informational?style=flat&logo=PostgreSQL&color=336791)**

## **🔧 Getting Started**
To set up the project locally:

Prerequisites
- Java 17 (or later)
- PostgreSQL (configured with the specified database and user details)
- Gradle (or use the included Gradle wrapper)

1. **Clone the Repository:**

      ```sh
      git clone https://github.com/Olayiwola72/oneot-weather-forcast
      ```

1. **Environment Setup:**

      Before building and running the project, create a .env file in the root of the project directory with the following contents to configure the database connection:

      ```sh
      # Database Name
      SPRING_DATASOURCE_NAME=weather

      # Database Configuration for SQL localhost
      SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/${SPRING_DATASOURCE_NAME}
      SPRING_DATASOURCE_USERNAME=weather
      SPRING_DATASOURCE_PASSWORD=weather
      ```

      The application will use these environment variables to connect to the PostgreSQL database. This is because it leverages paulschwarz:spring-dotenv library for environment variables management.

1. **Build and Run:**

   - Build the Project:

      ```sh
      ./gradlew clean build
      ```

   - Run Module 1 - Background Process: Start the background write service that fetches and stores weather data:
   
      ```sh
      ./gradlew :write-microservice:bootRun --args='--spring.profiles.active=prod'
      ```

   - Run Module 2 - Query Microservice Web API: Start the web API server that serves forecast data via REST endpoints:
      
      ```sh
      ./gradlew :query-microservice:bootRun --args='--spring.profiles.active=prod'
      ```    

## **Usage Instructions**
For detailed API information and testing, access the Swagger UI:

<p align="center">
   <a href="http://localhost:8093/swagger-ui.html" target="_blank">http://localhost:8093/swagger-ui.html</a>
</p>

Alternatively, you can access the Web API

   - Get forecast for a specific location:
   
      ```sh
      GET http://localhost:8093/api/forecasts/places?place={place}
      ```

   - Get the current day's forecast for all locations:
      
      ```sh
      GET http://localhost:8093/api/forecast/today
      ```

## **Testing**
To run the tests locally:

   ```sh
   ./gradlew test
   ```

## **Docker Support**
This project can deployed using Docker and Docker Compose, which simplifies the process of setting up and running the application in a containerized environment. Follow these instructions to get the application running using Docker.

<br/>

🛠 Prerequisites
- **Docker: Install Docker**
- **Docker Compose: Install Docker Compose**

<br/>

🚀 Quick Start
1. **Clone the Repository:**

   If you haven't already cloned the repository, do so with:

      ```sh
      git clone https://github.com/Olayiwola72/oneot-weather-forcast

1. **Build and Start the Application::**

   Use Docker Compose to build and start the application. This will build the Docker images and start the containers defined in docker-compose.yml in the root directory.

      ```sh 
      docker-compose up --build
      ```

   **Note:** --build forces Docker Compose to rebuild the images. You can omit this flag if you don't need to rebuild.


1. **📜 Configuration:**
Both modules use application-specific configuration files under src/main/resources/application.properties for database settings, scheduling intervals, and server ports.

Database Configuration: Modify PostgreSQL connection details if necessary.
Scheduling Interval: The default interval for the background service to fetch weather data is 30 minutes.

## **Contributing**
Contributions to this project are welcome. Please follow the standard GitHub fork, branch, and pull request workflow. Feel free to raise issues or feature requests to enhance this project.

## **📈 GitHub Stats**

[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=Olayiwola72&layout=compact)](https://github.com/Olayiwola72)

## **🤝 Connect With Me**

<a href="https://www.linkedin.com/in/olayiwola-akinnagbe/" target="_blank">
   <img 
      align="left" 
      src="https://github.com/Olayiwola72/my-profile/blob/main/linkedin.png" 
      alt="Olayiwola Akinnagbe | LinkedIn" 
      width="21px"
   />
</a>

<a href="https://twitter.com/OlayiwolaAkinn1" target="_blank">
   <img 
      align="left" 
      src="https://github.com/Olayiwola72/my-profile/blob/main/twitter.png" 
      alt="Olayiwola Akinnagbe | Twitter" 
      width="21px"
   />
</a>

<a href="https://drive.google.com/file/d/119Hkfzy2sHD9gm9V5Oe4m0Xm5vamPNgt/view?usp=sharing" target="_blank">
   <img 
      align="left" 
      src="https://github.com/Olayiwola72/my-profile/blob/main/cv.png" 
      alt="Olayiwola Akinnagbe | Resume" 
      width="21px"
   />
</a>

<br>
<br>

- 💬 If you have any questions or feedback, feel free to reach out!
