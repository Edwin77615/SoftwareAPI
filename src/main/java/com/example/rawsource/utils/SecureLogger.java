package com.example.rawsource.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.rawsource.config.SecureLogFilter;

@Component
public class SecureLogger {
    
    private static final Logger logger = LoggerFactory.getLogger(SecureLogger.class);

    public static void info(String message) {
        logger.info(SecureLogFilter.sanitizeLogMessage(message));
    }

    public static void info(String message, Object... args) {
        // Sanitize the message first
        String sanitizedMessage = SecureLogFilter.sanitizeLogMessage(message);
        
        // Sanitize the arguments
        Object[] sanitizedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                sanitizedArgs[i] = SecureLogFilter.sanitizeLogMessage(args[i].toString());
            } else {
                sanitizedArgs[i] = null;
            }
        }
        
        // Use SLF4J logger directly with sanitized message and args
        logger.info(sanitizedMessage, sanitizedArgs);
    }
    
    public static void warn(String message) {
        logger.warn(SecureLogFilter.sanitizeLogMessage(message));
    }
    
    public static void warn(String message, Object... args) {
        // Sanitize the message first
        String sanitizedMessage = SecureLogFilter.sanitizeLogMessage(message);
        
        // Sanitize the arguments
        Object[] sanitizedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                sanitizedArgs[i] = SecureLogFilter.sanitizeLogMessage(args[i].toString());
            } else {
                sanitizedArgs[i] = null;
            }
        }
        
        // Use SLF4J logger directly with sanitized message and args
        logger.warn(sanitizedMessage, sanitizedArgs);
    }
    
    public static void error(String message, Throwable throwable) {
        logger.error(SecureLogFilter.sanitizeLogMessage(message), throwable);
    }
}

