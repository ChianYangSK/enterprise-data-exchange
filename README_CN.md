# Enterprise Data Exchange Gateway

## Phase 2 / 可运行工程

Spring Boot Maven 模块位于 `enterprise-cloud/enterprise-modules/enterprise-dmz-exchange`，可执行：

```bash
cd enterprise-cloud/enterprise-modules/enterprise-dmz-exchange
mvn clean test
```

Docker 启动：`docker compose -f docker/docker-compose.yml up -d --build`。健康检查：`GET /actuator/health`；接口包括 `/api/v1/auth/login`、`/api/v1/patients/{patientId}`、`/api/v1/schedules/{patientId}`。


企业级安全数据交换平台


## 项目简介


Enterprise Data Exchange Gateway 是一个面向企业系统集成场景设计的安全 API 数据交换平台。


用于解决：

- 外部应用访问内部系统
- 多系统数据交换
- API统一管理
- 网络隔离访问
- 安全审计


典型场景：


```
外部App

  |

HTTPS

  |

DMZ交换层

  |

内部业务系统

  |

数据库

```


---

# 核心能力


## 1. 企业级API网关


提供：

- API路由
- 请求鉴权
- 限流控制
- 异常处理
- 接口管理


---

## 2. DMZ安全交换架构


设计原则：


- 外网不能直接访问内网
- DMZ作为唯一交换入口
- 数据库禁止外部访问
- 所有调用必须审计


---

## 3. 安全控制


支持：


- Token认证
- JWT授权
- HMAC-SHA256签名
- 时间戳校验
- Nonce防重放


---

# 技术架构


```
Client

 |

Nginx Gateway

 |

Exchange Service

 |

Internal API

 |

Database

```


---

# 技术栈


后端：

- Java
- Spring Boot
- Spring Cloud
- Maven


基础设施：

- Nginx
- Redis
- Docker
- PostgreSQL


安全：

- HTTPS
- JWT
- HMAC
- IP白名单


---

# 项目结构


```
enterprise-data-exchange

├── docs

├── architecture

├── enterprise-cloud

├── enterprise-gateway

├── enterprise-auth

├── enterprise-common

├── enterprise-modules

│
└── enterprise-dmz-exchange

├── docker

├── database

└── examples

```


---

# DMZ交换模块


核心模块：


```
enterprise-dmz-exchange

```


负责：


- 外部请求接入
- 内部接口调用
- 安全验证
- 数据脱敏
- 审计记录


---

# 文档


包含：


```
docs

├── architecture.md

├── security-design.md

├── api-design.md

└── deployment-guide.md

```


---

# 项目目标


通过该项目展示：


- 企业级Java开发能力
- API集成能力
- 微服务设计能力
- 安全架构设计能力
- 生产部署能力
