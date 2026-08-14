# Enterprise Data Exchange Architecture


## Overview

The system adopts a layered enterprise integration architecture.


## Architecture Diagram

The repository image [enterprise-api-flow.png](../architecture/enterprise-api-flow.png) shows the implemented request and response chain. The runnable DMZ module performs controller-to-service-to-client delegation, writes audit records, and masks patient fields before returning a response. `InnerSystemClient` is a synthetic internal-API adapter for this phase; it deliberately has no database connection.


```
                 Client Applications

                         |

                      HTTPS

                         |

              +-------------------+

              | Enterprise Gateway|

              +-------------------+

                         |

              +-------------------+

              | Data Exchange     |

              | Service           |

              +-------------------+

                         |

          +--------------+--------------+

          |                             |

 Security Layer                 Audit Layer


                         |

              Internal Systems

                         |

                    Database

```


# Components


## Enterprise Gateway

Responsibilities:

- Request routing
- SSL termination
- Traffic control


## Enterprise Data Exchange Service

Responsibilities:

- API exchange
- Authentication
- Data transformation
- Internal communication


## Security Module

Provides:

- Signature validation
- Token management
- Replay attack protection


## Audit Module

Records:

- Request information
- User activity
- System result


# Data Flow


1. Client sends HTTPS request

2. Gateway receives request

3. Exchange service validates security information

4. Internal API is called

5. Response is filtered and returned
