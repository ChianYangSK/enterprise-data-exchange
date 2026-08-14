# Security Design

## Implemented portfolio controls

The runnable module includes HMAC-SHA256 validation for `timestamp + nonce + requestBody`, timestamp expiry, Redis-backed nonce protection (with an in-memory fallback for unit tests), demo token issuing/validation, audit records, and null-safe masking. Production secrets are supplied through `EXCHANGE_SIGNATURE_SECRET` and should come from a secret manager. Gateway enforcement, mTLS, IP allowlists, and a production identity provider remain deployment responsibilities rather than claims of this demo service.

## 1. Overview

Enterprise Data Exchange Gateway is designed for secure communication between external applications and internal enterprise systems.

The security architecture follows enterprise integration best practices:

- Secure communication
- Identity authentication
- API request verification
- Data protection
- Access control
- Audit compliance


The security flow:

```
External Client

        |
        |
     HTTPS/TLS

        |
        v

+-----------------------+
| Enterprise Gateway     |
+-----------------------+

        |

        v

+-----------------------+
| Exchange Service       |
| Security Layer        |
+-----------------------+

        |

        v

Internal Business Systems

```


---

# 2. Transport Security


## HTTPS Encryption

All external communication uses HTTPS.


Example:

```
Client
   |
   |
 HTTPS 443
   |
   |
Enterprise Gateway

```


Benefits:

- Prevent data interception
- Protect sensitive information
- Ensure communication integrity


## Internal Communication


Communication between DMZ exchange service and internal services should use:

- HTTPS
- Mutual TLS (mTLS)
- IP whitelist


Example:


```
Exchange Service

        |

     HTTPS/mTLS

        |

Internal API Service

```


---

# 3. Authentication Design


## Token Based Authentication


The system uses token-based authentication.


Authentication flow:


```
Client

 |

Login Request

 |

Authentication Service

 |

Generate Access Token

 |

Client Request With Token

```


Example:


HTTP Header:

```http
Authorization: Bearer {access_token}

```


Token information:


```json
{
  "userId":"10001",
  "expireTime":7200,
  "scope":"patient-api"
}

```


---

# 4. API Signature Security


To prevent request tampering, API requests support HMAC-SHA256 signature validation.


## Signature Parameters


The signature contains:


```
APP_ID

TIMESTAMP

NONCE

HTTP_METHOD

REQUEST_PATH

REQUEST_BODY

```


Example:


```
patient-app

1719999999

abc123

POST

/api/app/auth/login

{"username":"demo"}

```


Signature:


```
HMAC-SHA256(secretKey, signatureContent)

```


---

# 5. Replay Attack Protection


## Nonce Validation


Each request contains a unique nonce.


Validation process:


```
Receive Request

        |

Check Nonce in Redis

        |

+----------------+

| Exists ?       |

+----------------+

   |
   |

 Reject Request


   |
   |

 Save Nonce


   |

 Continue

```


Redis example:


```
Key:

security:nonce:{nonce}


TTL:

300 seconds

```


---

# 6. Timestamp Validation


Each request must contain a timestamp.


Example:


```http
X-Timestamp: 1719999999

```


Validation:


```
Server Time - Request Time

        |

        |

<= 5 minutes

        |

Accept

```


Expired requests will be rejected.


---

# 7. Authorization Control


Security controls:


| Control | Description |
|-|-|
| IP Whitelist | Restrict trusted clients |
| API Permission | Control API access |
| Token Validation | Verify user identity |
| Rate Limiting | Prevent abuse |


---

# 8. Data Privacy Protection


Sensitive information must be masked before returning.


## Example


Original:


```
13812345678

```


Masked:


```
138****5678

```


Sensitive fields:


| Field | Protection |
|-|-|
| Phone Number | Masking |
| Identity Number | Masking |
| Medical Number | Filtering |
| Personal Data | Permission Control |


---

# 9. Audit Logging


All API requests should generate audit records.


Audit information:


```json
{
 "requestId":"abc123",
 "clientIp":"10.10.1.20",
 "api":"/api/app/patient/profile",
 "userId":"10001",
 "status":"SUCCESS",
 "timestamp":"2026-08-06 10:00:00"
}

```


Audit purpose:


- Security tracking
- Troubleshooting
- Compliance review


---

# 10. Network Security Model


Recommended deployment:


```
Internet Zone


        |

        |

      HTTPS


        |

        v


DMZ Zone


+--------------------+

| API Gateway        |

+--------------------+


+--------------------+

| Exchange Service   |

+--------------------+


        |

        |

   Restricted API


        |

        v


Internal Zone


+--------------------+

| Business Services |

+--------------------+


        |

        |

+--------------------+

| Database           |

+--------------------+

```


Security rules:


- External clients cannot access internal systems.
- Exchange service cannot directly access database.
- Database only accepts internal service connections.


---

# 11. Security Checklist


- [x] HTTPS enabled
- [x] Token authentication
- [x] HMAC signature validation
- [x] Timestamp verification
- [x] Nonce replay protection
- [x] Data masking
- [x] Audit logging
- [x] Network isolation
