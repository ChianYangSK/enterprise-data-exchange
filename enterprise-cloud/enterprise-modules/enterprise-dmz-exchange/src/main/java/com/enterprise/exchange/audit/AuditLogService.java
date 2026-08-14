package com.enterprise.exchange.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {
    private static final Logger log = LoggerFactory.getLogger(AuditLogService.class);

    public void record(AuditRecord record) {
        log.info("audit id={} requestId={} clientId={} api={} method={} success={} ip={} message={}", record.getId(), record.getRequestId(), record.getClientId(), record.getApi(), record.getMethod(), record.isSuccess(), record.getIp(), record.getMessage());
    }
}
