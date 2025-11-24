# campus-sys-backend — 学习营活动管理平台后台接口

本项目实现学习营活动管理平台的后端接口服务，支持活动管理、教学班管理、教师管理、课程与学习单元管理、学员管理、用户管理等，遵循 Restful 风格并内置 JWT 鉴权与 Swagger 接口文档。

## 技术栈
- Spring Boot 3.3
- Spring Web / Validation
- Spring Data JPA / Hibernate
- H2（开发环境内存库）
- JJWT（JWT 鉴权）
- Springdoc OpenAPI（Swagger UI）
- JDK 17+

## 快速开始
- 启动服务（默认 MySQL 配置）
  - `mvn -f campus-sys-backend/pom.xml spring-boot:run`
  - 前端入口：`http://localhost:8081/`
  - 文档入口：`http://localhost:8081/swagger-ui/index.html`
- 备用方式（打包运行）
  - `mvn -f campus-sys-backend/pom.xml -DskipTests=true package`
  - `java -jar campus-sys-backend/target/campus-sys-backend-0.1.0.jar`

## 登录与鉴权
- 预置管理员：`username=admin`，`password=admin123`
- 登录接口：`POST /auth/login`
- 成功返回 `token`，在 Swagger 右上角点击 `Authorize`，选择 `bearer-jwt` 并“只粘贴纯 token”（形如 `eyJ...`），不要加 `Bearer ` 前缀；之后调用业务接口会自动携带 `Authorization: Bearer <token>`。
- 说明：除登录与 Swagger 资源外，其他接口均需有效 Token。

## 业务约束
- 活动发布后才能创建教学班与课程。
- 教学班最多配置两位教师。
- 学员支持审核与批量审核。

## 核心接口（部分）
- 活动 Activity
  - `POST /api/activities` 创建活动
  - `PUT /api/activities/{id}` 修改活动
  - `POST /api/activities/{id}/publish` 发布活动
  - `GET /api/activities?status=PUBLISHED|DRAFT` 或 `GET /api/activities?name=关键词`
- 教学班 Classroom
  - `POST /api/classrooms` 创建（需要已发布活动）
  - `PUT /api/classrooms/{id}` 修改
  - `GET /api/classrooms/{id}` 查询单个
  - `POST /api/classrooms/{id}/students` 请求体：`{"ids":[学生id...]}`
  - `POST /api/classrooms/{id}/teachers` 请求体：`{"ids":[教师id...]}`（最多两位）
- 教师 Teacher
  - `POST /api/teachers` 新增（自动生成系统用户）
  - `GET /api/teachers`、`GET /api/teachers/{id}` 查询
- 课程 Course 与学习单元 Unit
  - `POST /api/courses` 创建课程（需已发布活动）
  - `POST /api/units/course/{courseId}` 创建学习单元
  - `GET /api/units/course/{courseId}` 查询课程下的学习单元
- 学员 Student
  - `POST /api/students` 新增学员
  - `POST /api/students/{id}/audit` 审核学员
  - `POST /api/students/batchAudit` 批量审核，请求体：`{"ids":[...id]}`
- 用户 User
  - `POST /api/users` 新增用户
  - `GET /api/users/{id}`、`GET /api/users?name=xxx`、`GET /api/users?username=xxx`

## 典型流程示例
- 登录→创建活动→发布活动→创建教学班→创建学生→添加到教学班

```bash
# 登录获取 token
curl -s -X POST http://localhost:8081/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"admin","password":"admin123"}'

# 创建活动（将返回 id）
curl -s -X POST http://localhost:8081/api/activities \
  -H "Authorization: Bearer <token>" -H 'Content-Type: application/json' \
  -d '{"name":"冬令营","title":"数学特训"}'

# 发布活动
curl -s -X POST http://localhost:8081/api/activities/<活动id>/publish \
  -H "Authorization: Bearer <token>"

# 创建教学班
curl -s -X POST http://localhost:8081/api/classrooms \
  -H "Authorization: Bearer <token>" -H 'Content-Type: application/json' \
  -d '{"name":"一班","activityId":<活动id>}'

# 创建学生（返回学生 id）
curl -s -X POST http://localhost:8081/api/students \
  -H "Authorization: Bearer <token>" -H 'Content-Type: application/json' \
  -d '{"name":"张三","grade":"初二"}'

# 将学生加入教学班
curl -s -X POST http://localhost:8081/api/classrooms/<班级id>/students \
  -H "Authorization: Bearer <token>" -H 'Content-Type: application/json' \
  -d '{"ids":[<学生id>]}'
```

## 常见问题
- 401 未授权
  - 未在 Swagger `Authorize` 配置“纯 token”；或填入了 `Bearer <token>` 导致头变成 `Bearer Bearer ...`。
  - token 过期（默认 120 分钟），请重新登录。
  - 端口使用错误（当前为 `8081`）。
- 409 activity not published
  - 按约束，先 `POST /api/activities/{id}/publish` 再创建教学班/课程。
- 500 Internal Server Error
  - 路径参数未填真实 id（例如写了 `{id}` 或 `%7Bid%7D`），请用实际数值。
  - 操作目标不存在，先用 `GET` 查询确认 id 存在。

## 数据库
- 运行 Profile：`spring.profiles.active: mysql`（见 `campus-sys-backend/src/main/resources/application.yml`）
- MySQL 连接配置（默认库名 `zuoye`）：`campus-sys-backend/src/main/resources/application-mysql.yml`

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/zuoye?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: "<your_password>"
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

- 连接验证：启动日志应包含 `HikariPool-1 - Added connection ...`，调用接口时打印 `Hibernate: ...` SQL。
- 如需切换库名/主机，请修改 `url` 中的库和主机端口。

## 项目结构
- 入口：`campus-sys-backend/src/main/java/com/campus/sys/CampusSysBackendApplication.java`
- 配置：`config`（数据初始化、Swagger、全局异常）
- 鉴权：`auth`（登录、JWT 工具、过滤器）
- 领域模块：`activity`、`classroom`、`teacher`、`course`、`unit`、`student`、`user`
- 配置文件：`campus-sys-backend/src/main/resources/application.yml`
- MySQL Profile 配置：`campus-sys-backend/src/main/resources/application-mysql.yml`
- 静态前端：`campus-sys-backend/src/main/resources/static/index.html`、`static/app.js`、`static/styles.css`
- 接口测试：`campus-sys-backend/src/test/java/com/campus/sys/ApiTests.java`

## 前端使用
- 入口：`http://localhost:8081/`，先登录（`admin/admin123`）后，页面会自动携带 token 调用接口。
- 支持操作：创建/发布活动、创建教学班、添加学生、设置教师、创建课程、创建/查询学习单元、创建用户等。
- 活动字段：`name`、`title`、`description`、`term`、`grade`
- 单元字段：`chapter`、`section`、`docUrl?`、`videoUrl?`、`points`

## 测试
- 运行：`mvn -f campus-sys-backend/pom.xml test`
- 已包含 MockMvc 用例覆盖登录与活动发布的关键路径。

## 许可
- 学习项目，未设定许可证，可根据需要补充。