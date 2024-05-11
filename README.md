
![Angular](https://img.shields.io/badge/Angular-15.2.0-red)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green)

# Digital Banking System 
The Ebank  system is a full-stack web application that provides digital banking services. It is composed of a backend and a frontend, each with its own set of technologies and frameworks with JEE architecture.

## Backend Part 

The EBank project  backend is a Spring Boot application that provides RESTful APIs for a digital banking system. It is organized in a multi-layered architecture, including controllers, services, repositories, and domain entities.



## Class Diagram///*//--

-**Account**: This class might have attributes like accountId, balance, account type, and methods like debit(), credit(), transfer().  
-**Customer**: This class might have attributes like customer, name, address, email, and methods like addAccount(), removeAccount().  
-**Transaction**: This class might have attributes like transactionId, amount, transactionType, date, and methods like execute().

These enums are used to restrict the possible values for certain fields in the application : 

-**AccountStatus**: This enum represents the status of a bank account. It can have values like ACTIVE, INACTIVE, SUSPENDED, etc.

-**OperationType**: This enum represents the type of operation performed on a bank account, It can have values like DEPOSIT, WITHDRAWAL, TRANSFER, etc.

![image](https://github.com/YassinMk/JEE-EBank/assets/122708120/f0898e01-a232-46e3-895e-e193fbe7d9d6)



## Project Structure

The project follows a typical Maven project structure and is organized as follows:

```
org
└── example
    └── ebankingbackend
        ├── dtos
        ├── entities
        ├── enums
        ├── exceptions
        ├── mappers
        ├── repositories
        ├── security
        ├── services
        └── web
```

### Directories

- `dtos`: This directory contains Data Transfer Objects (DTOs) which are used to transfer data between processes or across network connections.
- `entities`: This directory contains JPA entities that are mapped to the database tables.
- `enums`: This directory contains all the enumeration classes used in the project.
- `exceptions`: This directory contains custom exception classes for handling specific exceptions.
- `mappers`: This directory contains mapper interfaces/classes used for converting entities to DTOs and vice versa.
- `repositories`: This directory contains Spring Data JPA repositories for accessing the database.
- `security`: This directory contains classes related to security configuration (JWT, Spring Security).
- `services`: This directory contains service classes where the business logic of the application is implemented.
- `web`: This directory contains controller classes which handle HTTP requests and responses.

## Dependencies

The project uses several dependencies, managed by Maven. Key dependencies include:

- Spring Boot: Provides the framework for creating stand-alone, production-grade Spring-based applications.
- Spring Data JPA: Simplifies the development of applications that need to access JPA data sources.
- Spring Security: Handles authentication and authorization at the application level.
- JWT: Used for stateless authentication.
- MySQL Connector: Enables the application to connect with MySQL database.

## Database

The application uses a relational database (MySQL) for persisting data. The `entities` directory contains JPA entities that are mapped to the database tables. The application uses Spring Data JPA repositories for accessing the database.

The database connection is configured in the `application.properties` file as follows:

- `spring.datasource.url`: The JDBC URL of the database. In this case, a MySQL database named `E-BANK` runs on localhost.
- `spring.datasource.username`: The username for the database. In this case, it is `root`.
- `spring.datasource.password`: The password for the database. In this case, it is not set.
- `spring.jpa.hibernate.ddl-auto`: This property is used to automatically create, update, or validate the database schema. In this case, it is set to `create`, which means the schema will be created at startup.
- `spring.jpa.properties.hibernate.dialect`: This property sets the SQL dialect. In this case, it is set to `org.hibernate.dialect.MariaDBDialect`, which is the dialect for MariaDB and MySQL databases.
- `spring.jpa.show-sql`: This property enables logging of SQL statements. In this case, it is set to `false`, which means SQL statements will not be logged.
- `server.port`: This property sets the port on which the server will run. In this case, it is set to `8082`.

## Setup and Running

To set up and run the project, you must install Java and Maven on your machine. You can then clone the project and navigate to the project directory. Run the following command to build the project:

```bash
mvn clean install
```

To run the project, use the following command:

```bash
mvn spring-boot:run
```

The application will start and by default, it will be accessible at `http://localhost:8082`.

## API Documentation

The API documentation is provided with Swagger and can be accessed at `http://localhost:8082/swagger-ui.html` when the application is running.

# E-Banking Frontend

This project is an Angular application for an e-banking system. It provides a user interface for managing customers and their bank accounts.

## Technologies and Packages

The application uses several technologies and packages:
- JWT: Used for authentication and secure data transfer.
- Bootstrap: A CSS framework for designing responsive and mobile-first websites.

## Project Structure

The project is organized into several components, each with its own directory:

- `accounts`: Handles the display and management of bank accounts.
- `admin-template`: Serves as the layout for the admin interface.
- `customer-accounts`: Manages the accounts of a specific customer.
- `customers`: Handles the display and management of customers.
- `guards`: Contains the AuthentificationGuard and AuthorizationGuard for route protection.
- `interceptors`: Contains the HTTP interceptors used in the application.
- `login`: Provides the login interface.
- `model`: Contains the data models used in the application.
- `navbar`: Contains the navigation bar displayed on all pages.
- `new-customers`: Provides the interface for adding new customers.
- `not-authorized`: Displays a message when a user tries to access a route they are not authorized for.
- `services`: Contains the services used for data handling and business logic.

## Routing

The application uses Angular's routing system to navigate between different components. Here is a brief overview of the routes:

- `/login`: Displays the LoginComponent.
- `/`: Redirects to `/login`.
- `/admin`: Displays the AdminTemplateComponent. This route is protected by the AuthentificationGuard.
  - `/admin/customers`: Displays the CustomersComponent.
  - `/admin/accounts`: Displays the AccountsComponent.
  - `/admin/new-customers`: Displays the NewCustomersComponent. This route is protected by the AuthorizationGuard and requires the user to have an "ADMIN" role.
  - `/admin/customer-accounts/:id`: Displays the CustomerAccountsComponent for a specific customer, where `:id` is the customer's ID.
  - `/admin/notAuthorized`: Displays the NotAuthorizedComponent.

## Running the Application

To run the application, navigate to the project directory and run `npm install` to install the necessary dependencies. Then, run `ng serve` to start the development server. Open your web browser and navigate to `http://localhost:4200/` to view the application.

## Contributing

Contributions are welcome. Please make sure to update tests as appropriate.

## License

[MIT](https://choosealicense.com/licenses/mit/)
