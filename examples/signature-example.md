# API Signature Example


## Signature Algorithm

HMAC-SHA256


## Signature Content


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
{"username":"test"}
```


Generate:

```
HMAC-SHA256(secret,key)
```
