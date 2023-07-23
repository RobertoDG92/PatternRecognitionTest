# PatternRecognitionTest

PatternRecognitionTest is a Spring Boot application that provides a REST API for a pattern recognition problem involving points and line segments.

## Features

This application exposes the following REST endpoints:

- `POST /point`: Add a point to the space
- `GET /space`: Get all points in the space
- `GET /lines/{n}`: Get all line segments passing through at least N points
- `DELETE /space`: Remove all points from the space

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed the latest version of Java 17 and Maven.
- You have a Windows/Linux/Mac machine.

## Installing PatternRecognitionTest

To install PatternRecognitionTest, follow these steps:

1. Clone the repository:
    ```bash
    git clone https://github.com/RobertoDG92/PatternRecognitionTest.git
    ```
2. Navigate to the project directory:
    ```bash
    cd PatternRecognitionTest
    ```
3. Build the project:
    ```bash
    mvn clean install
    ```

## Using PatternRecognitionTest

To use PatternRecognitionTest, follow these steps:

1. Run the project:
    ```bash
    mvn spring-boot:run
    ```
2. The application will start running at: `http://localhost:8080`

You can now use a tool like [Postman](https://www.postman.com/) or [curl](https://curl.haxx.se/) to interact with these endpoints.

## Testing the Application
Once the application is running, you can interact with it using Postman, a popular tool for API testing.

Here are the basic steps for testing the different endpoints:

1. Add a new point to the space:

    Open Postman
    Select the "POST" method
    Enter http://localhost:8080/test/point in the URL field
    Click on the "Body" tab, select "raw" and "JSON" format
    Enter the following in the body:
    json
    Copy code
    {
    "x": 1,
    "y": 1
    }
    Click "Send"
2. Retrieve all points in the space:

   Select the "GET" method
   Enter http://localhost:8080/test/space in the URL field
   Click "Send"
3. Get all line segments passing through at least N points:

   Select the "GET" method
   Enter http://localhost:8080/test/lines/{n} in the URL field (replace {n} with the desired number of points)
   Click "Send"
4. Remove all points from the space:

   Select the "DELETE" method
   Enter http://localhost:8080/test/space in the URL field
   Click "Send"
Make sure that the application is running at http://localhost:8080 when you perform these tests. If it is running on a different port, replace 8080 with the appropriate port number in the above URLs.

## Notes
I Created the Point class, instead of using the Point of Java.awt so that i can implements in it the methods equals and hashCode; this way I can avoid, using my own class and a hashSet, to insert duplicate points.

## Contact

If you want to contact me, you can reach me at <digiovanni.roberto92@gmail.com>.
