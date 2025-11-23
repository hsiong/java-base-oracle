# Before You Start

Open source project, any bright idea would be welcomed.  
开源项目, 欢迎您加入到本项目

We reserves the right to ensure accountability of any commercial use without our authorization.  
任何非授权的商用行为, 我们保留追责的权利

# Introduce

本项目致力于实现一个简易、轻量的开发框架, 方便您能够快速开发任意一个 java 后端单体应用。

本项目在架构设计上, 力求实现高效、简洁、低耦合、高聚合, 易于拆分和重组。所有与核心业务无关的拓展模块, 均以 backend-tool-xxx 命名, 与核心代码隔离, 灵活集成。

## Architecture 架构

- **backend-common**: 封装实体、枚举、通用工具类(SpringUtil、RedisUtil、HttpUtil 等), 不依赖具体框架, 用于复用业务无关能力。  
- **backend-framework**: 提供 Spring Boot 自动配置(跨域、拦截器、上下文、序列化、MyBatis Plus、Druid、Redis、线程池、Spring-Doc/OpenAPI 等), 通过 starter 模式注入 `backend-system`。  
- **backend-system**: 实际业务模块, 暴露 REST API、RBAC 权限、登录/鉴权、文件/字典等功能, `src/main/resources/application-*.yml` 区分 dev/test/prod。  

请求链路: Controller → Service → MyBatis Plus Mapper → 多数据源 Druid → Redis/外部资源。安全层由 JWT + Redis 维护在线状态, 全局异常与 Result 统一返回; 前端再结合 weapp-taro-templates 或其他应用调用。

## Feature Highlights 功能介绍

- **基础框架**: Spring Boot + 自定义拦截器(防重复提交)、XSS 过滤、全局异常、上下文透传、本地文件映射、动态配置(@profiles.active@)。
- **线程与缓存**: 基础/定长/异步线程池配置; Redis 初始化、序列化、Spring Cache 接入。
- **数据访问**: Druid 多数据源, MyBatis Plus 自动注入 `createdBy/createdAt/updatedBy/updatedAt`, 雪花 ID, XML Mapper 过滤, 数据字典注解加速查询。
- **接口能力**: Spring-Doc OpenAPI 3.0 分组文档, Logback 彩色日志 + Plumelog, 代码生成器。
- **安全与权限**: 登录(密码 MD5+Salt)、JWT+Redis 会话、Token 续约、RBAC 权限管理。
- **附加工具**: 文件管理、常用工具类、推荐 weapp-taro-templates、ynfy-tool-easypoi/httpconnect/pay/encrypt 等生态组件。

# Tech Stack

- Java 17, Maven 多模块(aggregator `pom.xml`)
- Spring Boot 2.7.x, Spring Cloud 2021.0.3
- MyBatis Plus + MyBatis Plus Join, Druid, Redis
- Spring-Doc OpenAPI 3.x, Lombok, Hutool, Fastjson, Logback/Plumelog
- JUnit 4 测试框架

# What Can We Do

## How to Use 如何使用

1. **环境要求**: 安装 JDK 17+, Maven 3.8+, Redis/MySQL(或您配置的任意数据源); 如需多数据源请准备相应 schema。  
2. **克隆与编译**:
   ```bash
   git clone https://github.com/ynfy-tech/java-base-multidb.git
   cd java-base-multidb
   mvn clean install
   ```
   `mvn clean install -pl backend-framework -am` 可局部重建框架与其依赖。
3. **配置**: 在 `backend-system/src/main/resources/application-dev.yml` 设置数据源、Redis、JWT 密钥等; 新增 profile 文件请同步 `backend-system/pom.xml` 的 resource 过滤配置。  
4. **运行**:
   ```bash
   mvn -pl backend-system spring-boot:run -Pdev
   # 或
   mvn -pl backend-system package -Pdev
   java -jar backend-system/target/backend-system-1.0.0-SNAPSHOT.jar --spring.profiles.active=dev
   ```
5. **接口调试**: 启动后访问 `http://localhost:8080/doc.html`(或 Spring-Doc UI 地址) 查看自动生成的 OpenAPI 文档, 使用 Postman/前端模板进行联调。  
6. **测试**: 默认 profile 跳过测试, 执行 `mvn test -DskipTests=false` 运行 JUnit, 覆盖多数据源路由、权限/登录流程等关键逻辑。  

## Tutorials

即将补充: 包括脚手架使用、代码生成器示例、以及与 weapp-taro-templates 的联动教程。如有需求欢迎提交 issue。
