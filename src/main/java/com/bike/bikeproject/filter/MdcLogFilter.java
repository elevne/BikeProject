package com.bike.bikeproject.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

/**
 * 매 요청마다 고유의 ID 를 부여해서 LOG 를 묶어서 볼 수 있도록 하는 역할 (MDC 사용)
 */
@Slf4j
@Component
public class MdcLogFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MDC.put("transactionId", UUID.randomUUID().toString());

        String url = (request instanceof HttpServletRequest) ? ((HttpServletRequest) request).getRequestURI() : null;
        log.info("REQUEST ON URL {}", url);

        chain.doFilter(request, response);
        MDC.clear();
    }

}
