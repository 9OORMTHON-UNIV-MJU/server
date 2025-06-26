package goormthonUniv.MJU.server.global.resolver;

import goormthonUniv.MJU.server.global.config.JwtTokenUtil;
import goormthonUniv.MJU.server.global.exception.ExceptionCode;
import goormthonUniv.MJU.server.global.resolver.annotation.Auth;
import goormthonUniv.MJU.server.global.resolver.exception.GlobalException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String TOKEN_HEADER_NAME = "Authorization";
    private static final String TOKEN_START_NAME = "Bearer ";
    private static final int TOKEN_BODY_DELIMITER = 7;

    private final JwtTokenUtil tokenProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Auth.class) && parameter.getParameterType() == Long.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String tokenHeader = request.getHeader(TOKEN_HEADER_NAME);
        validateToken(tokenHeader);
        String token = tokenHeader.substring(TOKEN_BODY_DELIMITER);
        return tokenProvider.getMemberId(token);
    }

    private void validateToken(String token) {
        if(token == null || !token.startsWith(TOKEN_START_NAME)) {
            throw new GlobalException(ExceptionCode.NOT_FOUND_TOKEN);
        }
    }
}
