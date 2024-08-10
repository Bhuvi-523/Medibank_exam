package com.audition.configuration;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class ResponseHeaderInjector {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // Get the current active span
        Span currentSpan = Span.current();
        SpanContext spanContext = currentSpan.getSpanContext();

        // Inject the trace ID and span ID into the response headers
        response.addHeader("trace-id", spanContext.getTraceId());
        response.addHeader("span-id", spanContext.getSpanId());
    }
}
