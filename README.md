### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - [x] Add tests
  - [x] Change syntax
  - [x] Protect controller end points
  - [x] Add caching logic for database calls
  - [x] Improve doc and comments
  - [x] Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you
  had more time, etc.
- Send us the link of your repository.

#### What we will look for

- Readability of your code
- Documentation
- Comments in your code
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I have 3 years experience in Java and I started to use Spring Boot from last year
- I'm a beginner and just recently learned Spring Boot
- I know Spring Boot very well and have been using it for many years

**Answer:**

- I have 4 years experience in Java and I use Spring Boot for 0.5 year.
- I have some experience in GC and know how to check gc log and heapdump.

## About the implementation of this project

### How to mock an auth server

I am using keycloak locally which hold in docker.

Check more here: [How to mock an auth server for testing](/mock/auth-server/README.md)

After setup keycloak I can make a request to fetch accessToken and use that to test API via Postman.

### Install redis for testing

Install Docker in your local pc and run the following command to start redis.

```shell
docker pull redis
docker run -p 6379:6379 --name redis \
-v {your-local-folder}:/data \
-d redis redis-server --appendonly yes
```

After that you can run junit tests in your IDE or with follow command under root folder.

```shell
mvn -B test --file pom.xml
```

### Add tests

I added some tests for testing just controller layer.

I also raise an [issue](https://github.com/zydmayday/java-challenge/issues/13) for adding junits for
other layers.

In order to test other layers, we can use Mockito to reduce the impact from other layers.

### Protect controller end points

I used Spring-security to protect controller, so user need to provide access-token to call API.

For further implementation, we can support other different auth methods to get more users.

### Add caching logic for database calls

I used spring-redis to cache data fetched from database,  
I just use the basic configuration,  
we can also apply some complex rules to make redis more usable.

### Improve doc and comments

I added some JavaDoc for service layer,  
because it is the core business logic part for this service.

And for Junit tests, I used @DisplayName to make test case more readable.

### Fix any bug you might find

I add some validations for both controller and repository,  
such as if we can not find employee with given id, we respond with 404,  
if there is any invalid request body, we respond with 400 status code.

I also make getEmployees pageable to increase the performance.

### Future works

I added some other issues here: https://github.com/zydmayday/java-challenge/issues

Feel free to have a check!
