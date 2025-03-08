# Training Admin

Training Admin is a fictitious application.
It is not designed to be production ready, nor will it ever be.
I wrote it solely to illustrate the capabilities and the disabilities of digital coding assistants such as GitHub Copilot Workspaces.

The functional scope is a training provider that wants to administer
- The courses they offer
- When the courses are delivered
- The reservations for course deliveries
- Student details

## Technologies
The project uses the following technologies
- [Kotlin](https://kotlinlang.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Thymeleaf](https://www.thymeleaf.org/)
- [Tailwind CSS](https://tailwindcss.com/) & [DaisyUI](https://daisyui.com/)

## Getting Started
To start working on this project, have two terminals ready.

In the first terminal, run

```shell
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

In the second terminal, run

```shell
npm run build && npm run watch
```

Finally, navigate to http://localhost:3000/ and start working on your code.

## License
The code is licensed under the MIT license.
See [./LICENSE](LICENSE) for details.