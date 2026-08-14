# API Design Specification

## Implemented Phase 2 endpoints

| Method | Endpoint | Behaviour |
|-|-|-|
| POST | `/api/v1/auth/login` | Validates username/password and returns a demo token in the unified response envelope. |
| GET | `/api/v1/patients/{patientId}` | Retrieves synthetic internal data and masks name, ID card, and phone. |
| GET | `/api/v1/schedules/{patientId}` | Retrieves a synthetic schedule. |
| GET | `/actuator/health` | Spring Boot health check. |

All application endpoints return `{ success, code, message, data }`. Earlier `/api/app/...` examples are conceptual legacy paths; the table above is the runnable implementation.


## 1. Overview


Enterprise Data Exchange Gateway provides unified REST APIs for external applications.


Design principles:


- RESTful API style
- JSON data format
- Secure authentication
- Standard error response
- Audit support


---

# 2. API Base Information


Base URL:


```
https://api.example.com
```


Protocol:


```
HTTPS

```


Data Format:


```
application/json

```


---

# 3. Common Request Headers


All APIs require:


| Header | Required | Description |
|-|-|-|
| X-App-Id | Yes | Application identifier |
| X-Timestamp | Yes | Request timestamp |
| X-Nonce | Yes | Unique request ID |
| Authorization | Yes | Access token |
| X-Signature | Yes | HMAC signature |


Example:


```http
POST /api/app/auth/login HTTP/1.1

Host: api.example.com

X-App-Id: patient-app

X-Timestamp: 1719999999

X-Nonce: abc123

Authorization: Bearer xxxx

X-Signature: xxxx

```


---

# 4. Authentication API


## Login


### Endpoint


```
POST /api/app/auth/login

```


### Request


```json
{
  "username":"demo",
  "password":"password"
}

```


### Response


```json
{
  "code":200,
  "message":"success",
  "data":{
      "token":"eyJhbGciOiJIUzI1",
      "expire":7200
  }
}

```


---

# 5. Patient Profile API


## Query Patient Information


Endpoint:


```
POST /api/app/patient/profile

```


Request:


```json
{
 "patientId":"10001"
}

```


Response:


```json
{
 "patientId":"10001",
 "name":"John",
 "phone":"138****5678",
 "status":"ACTIVE"
}

```


Features:


- Permission validation
- Sensitive data masking
- Audit logging


---

# 6. Password Change API


Endpoint:


```
POST /api/app/patient/password/change

```


Request:


```json
{
 "patientId":"10001",
 "oldPassword":"******",
 "newPassword":"******"
}

```


Validation:


- Old password verification
- Password strength checking
- Confirmation validation


---

# 7. Schedule Query API


Endpoint:


```
POST /api/app/patient/schedule/page

```


Request:


```json
{
 "patientId":"10001",
 "startDate":"2026-08-01",
 "endDate":"2026-08-31",
 "pageNum":1,
 "pageSize":10
}

```


Response:


```json
{
 "total":10,
 "rows":[
   {
    "date":"2026-08-10",
    "time":"08:00",
    "location":"Center A"
   }
 ]
}

```


---

# 8. Error Response


Unified error format:


```json
{
 "code":40001,
 "message":"Invalid signature",
 "requestId":"abc123"
}

```


Error codes:


| Code | Description |
|-|-|
| 40001 | Signature invalid |
| 40002 | Token expired |
| 40003 | Permission denied |
| 50000 | Internal error |


---

# 9. API Security Flow


```
Client

 |

Create Request

 |

Generate Signature

 |

Send HTTPS Request

 |

Gateway Validation

 |

Exchange Service

 |

Internal API

 |

Return Response

```


---

# 10. API Version Management


Recommended:


```
/api/v1/app/auth/login

/api/v1/app/patient/profile

```


Benefits:


- Backward compatibility
- Smooth upgrades
- Multiple client support
