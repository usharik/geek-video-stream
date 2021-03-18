# Very simple video hosting application

You can find a very basic video hosting application in this repository. 
It was made to show basics of video streaming in web.

To build and run the app you need to have on your PC:
* [JDK 11 or later](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
* [NodeJS](https://nodejs.org/)
* [Angular CLI](https://angular.io/guide/setup-local)

To install Angular CLI run the command
```bash
npm install -g @angular/cli
```

Next we should install all NodeJS packages required to build application. Got the `geek-tube-angular-frontend directory` and run the command 
```bash
npm i
```

Next that we are ready to run frontend with testing server by command
```bash
ng serve
```

Frontend going to be available here http://localhost:4200/

Notice the backend proxying configuration is [here](/geek-tube-angular-frontend/proxy.conf.json) in the sources

After that go to the `geek-tube` in another console and run backend with
```bash
./mvnw spring-boot:run
```

Some recommended links about video streaming
* https://tutorial-academy.com/rest-jersey2-resume-video-streaming/
* https://saravanastar.medium.com/video-streaming-over-http-using-spring-boot-51e9830a3b8
* https://technicalsand.com/streaming-data-spring-boot-restful-web-service/#2-2-streamingresponsebody-as-return-type

