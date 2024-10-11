# Fda DrugRecord Application RESTful API

This project is a RESTful API that exposes food-drug-service application records from the Fda API. Users can search for DrugRecord
records by FDA manufacturer name and store specific DrugRecord records in the local database.

## Prerequisites

- Java 17
- Maven

## How to Build and Run Locally

1. Clone the repository:
    ```
    git clone  https://github.com/rinkuls/food-drug-service.git
    ```

2. Navigate to the project directory:
    ```
    cd food-drug-service
    ```

3. Build the project using Maven:
    ```
    mvn clean install
    ```

4. Run the application:
    ```
    mvn spring-boot:run
    ```

5. check health of application
    ```
    http://localhost:8989/actuator/health
    ```

6. go to swagger to get all information and from here API can be tested
    ```
    http://localhost:8989/swagger-ui/index.htm
    ```
7. second option Open your browser or API client (Postman) and interact with the API:

    - Search API: `GET /api/fda/search`
    - Store a DrugRecord record: `POST /api/fda`
    - Retrieve stored records: `GET /api/fda/record`
   
