# Before You Start

This is an open-source project — any bright ideas are welcome.
开源项目，欢迎您加入到本项目

We reserve the right to ensure accountability for any commercial use without our authorization.
任何未经授权的商用行为，我们保留追责的权利

# Introduction

This project is dedicated to providing a simple and lightweight development framework that allows you to quickly build any Java backend monolithic application.

From an architectural perspective, the project aims for high efficiency, simplicity, low coupling, high cohesion, and easy splitting or recomposition. All non–core business extension modules are named **backend-tool-xxx**, isolated from core code and flexibly integrated.

## Architecture

- **backend-common**: Contains entities, enums, and general utilities (SpringUtil, RedisUtil, HttpUtil, etc.). It does **not** depend on specific frameworks and is intended to reuse business-agnostic capabilities.
- **backend-framework**: Provides Spring Boot auto-configuration (CORS, interceptors, context, serialization, MyBatis Plus, Druid, Redis, thread pools, Spring-Doc/OpenAPI, etc.) and injects `backend-system` via a starter pattern.
- **backend-system**: The actual business module, exposing REST APIs, RBAC permissions, login/authentication, file & dictionary management. Profiles such as `application-*.yml` (dev/test/prod) are used for environment separation.

Request pipeline: **Controller → Service → MyBatis Plus Mapper → Multi-Datasource Druid → Redis / external resources**.
Security layer uses JWT + Redis to maintain online state. Unified global exception handling and `Result` wrapper; front-end may integrate with **weapp-taro-templates** or other apps.

## Feature Highlights

- **Core Framework**: Spring Boot, custom interceptors (anti-duplicate submission), XSS filtering, global exceptions, context propagation, local file mapping, dynamic configuration (`@profiles.active@`).
- **Threading & Caching**: Basic / fixed / async thread pools; Redis initialization & serialization, Spring Cache integration.
- **Data Access**: Druid multi-data-source, MyBatis Plus auto-filling (`createdBy/createdAt/updatedBy/updatedAt`), Snowflake ID, XML mapper filtering, data dictionary annotation for faster lookup.
- **API Capabilities**: Spring-Doc OpenAPI 3.0 grouped docs, Logback colored logging + Plumelog, code generator.
- **Security & Permissions**: Login (password MD5+Salt), JWT+Redis sessions, token renewal, RBAC permission management.
- **Additional Tools**: File management, common utilities, recommended ecosystem modules such as **weapp-taro-templates**, **ynfy-tool-easypoi/httpconnect/pay/encrypt**, etc.

# Tech Stack

- Java 17, multi-module Maven (aggregator `pom.xml`)
- Spring Boot 2.7.x, Spring Cloud 2021.0.3
- MyBatis Plus & MyBatis Plus Join, Druid, Redis
- Spring-Doc OpenAPI 3.x, Lombok, Hutool, Fastjson, Logback / Plumelog
- JUnit 4 testing framework

# What You Can Do

## How to Use

1. **Environment Requirements**: Install JDK 17+, Maven 3.8+, Redis/MySQL (or any configured datasource). Prepare additional schemas if you use multiple datasources.

2. **Clone & Build**:

   ```
   git clone https://github.com/ynfy-tech/java-base-multidb.git
   cd java-base-multidb
   mvn clean install
   ```

   To rebuild framework modules only:
   `mvn clean install -pl backend-framework -am`

3. **Configuration**: Configure datasource, Redis, JWT secrets, etc. in
   `backend-system/src/main/resources/application-dev.yml`.
   When creating new profile files, also update resource filtering in `backend-system/pom.xml`.

4. **Run**:

   ```
   mvn -pl backend-system spring-boot:run -Pdev
   # Or
   mvn -pl backend-system package -Pdev
   java -jar backend-system/target/backend-system-1.0.0-SNAPSHOT.jar --spring.profiles.active=dev
   ```

5. **API Testing**: After startup, visit `http://localhost:8080/doc.html` (or your Spring-Doc UI address) for auto-generated OpenAPI documentation. Use Postman or a frontend template for integration testing.

6. **Testing**: Default profiles skip tests. Run:

   ```
   mvn test -DskipTests=false
   ```

   This covers multi-datasource routing, permission/auth flows, and other key logic.

## Tutorials

Coming soon: scaffold usage, code generator examples, and integration tutorials with **weapp-taro-templates**.
If you need more, feel free to submit an issue.