package com.cookietrip.domain.auth.handler;

import com.cookietrip.global.exception.ExceptionResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cookietrip.domain.auth.exception.AuthExceptionCode.INVALID_MEMBER_ROLE;

public class TokenAccessDeniedHandler
        implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        setResponse(response);
    }

    // 한글 출력을 위해 getWriter 사용
    private void setResponse(
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().print(ExceptionResponse.of(INVALID_MEMBER_ROLE).convertJson());
    }
}