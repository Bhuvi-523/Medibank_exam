package com.audition.common.logging;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;

@Component
public class AuditionLogger {

    public void info(final Logger logger, final String message) {
        if (logger.isInfoEnabled()) {
            logger.info(message);
        }
    }

    public void info(final Logger logger, final String message, final Object object) {
        if (logger.isInfoEnabled()) {
            logger.info(message, object);
        }
    }

    public void debug(final Logger logger, final String message) {
        if (logger.isDebugEnabled()) {
            logger.debug(message);
        }
    }

    public void warn(final Logger logger, final String message) {
        if (logger.isWarnEnabled()) {
            logger.warn(message);
        }
    }

    public void error(final Logger logger, final String message) {
        if (logger.isErrorEnabled()) {
            logger.error(message);
        }
    }

    public void logErrorWithException(final Logger logger, final String message, final Exception e) {
        if (logger.isErrorEnabled()) {
            logger.error(message, e);
        }
    }

    public void logStandardProblemDetail(final Logger logger, final ProblemDetail problemDetail, final Exception e) {
        if (logger.isErrorEnabled()) {
            final var message = createStandardProblemDetailMessage(problemDetail);
            logger.error(message, e);
        }
    }

    public void logHttpStatusCodeError(final Logger logger, final String message, final Integer errorCode) {
        if (logger.isErrorEnabled()) {
            logger.error(createBasicErrorResponseMessage(errorCode, message) + "\n");
        }
    }

    private String createStandardProblemDetailMessage(final ProblemDetail standardProblemDetail) {
       StringBuilder messageBuilder = new StringBuilder();

        messageBuilder.append("Problem Detail - ");
        if (StringUtils.isNotBlank(standardProblemDetail.getTitle())) {
            messageBuilder.append("Title: ").append(standardProblemDetail.getTitle()).append(", ");
        }
        if (standardProblemDetail.getStatus() != null) {
            messageBuilder.append("Status: ").append(standardProblemDetail.getStatus()).append(", ");
        }
        if (StringUtils.isNotBlank(standardProblemDetail.getDetail())) {
            messageBuilder.append("Detail: ").append(standardProblemDetail.getDetail()).append(", ");
        }
        if (StringUtils.isNotBlank(standardProblemDetail.getInstance())) {
            messageBuilder.append("Instance: ").append(standardProblemDetail.getInstance()).append(", ");
        }

        // Remove the last comma and space
        if (messageBuilder.length() > 0) {
            messageBuilder.setLength(messageBuilder.length() - 2);
        }

        return messageBuilder.toString();
    }

    private String createBasicErrorResponseMessage(final Integer errorCode, final String message) {
        return "Error Code: " + errorCode + ", Message: " + message;
    }
}
