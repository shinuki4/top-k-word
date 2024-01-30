#  Top K most frequent words

Backend Developer Assignment

The goal of this challenge is to write a program that takes a text file and finds the top K most frequent words. The program should use Java 11+ and Spring Boot for the backend API. Here are the specific requirements:

Backend Requirements:

- The API should have an endpoint that takes a text file and a value for K as input.
- The API should read the text file and find the K most frequent words.
- The API should return the K most frequent words and their frequency in descending order.
- The API should have tests to ensure its functionality.

## Pre requisite

After cloning the project don't forget to:
- Make sure you have gradle installed
- Check out the Framework requirements:
  - https://spring.io/projects/spring-boot
  - Used version is the 3.2.2 
- App needs Redis server to be able to cache with default port:
  - https://redis.io/download/

## Run

- Make sure Redis server is running
- To run it -> gradle bootRun or simply use IDE
  - Once the app is started:
    - port: 8777
- To run the test use the IDE 
  - there's integration and unit test


## Assumption
- Some more custom exception can be thrown in case there's issue to read the file
- The result returned could be in a custom object with it's ranking
- The authentication is yet to be implemented
- The cas if the input files doesn't fit the memory
- Containerized the service make life easier to run and deploy it through a pipeline 

## Issues
- Had long time hesitated between using a Map or Tree implementation but after considering the both pros and cons I decided to go on map implementation.
  - Map is most of the time more efficient and cost less space but if the input start to be really huge maybe it would be good to consider using tree.
- Configuring redis on windows as I m used to unix system for work
