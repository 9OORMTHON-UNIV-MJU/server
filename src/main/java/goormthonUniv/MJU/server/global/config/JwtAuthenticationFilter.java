package goormthonUniv.MJU.server.global.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
						            HttpServletResponse response,
						            FilterChain filterChain)
		throws ServletException, IOException {

		// Authorization 헤더에서 토큰 추출
		String token = resolveToken(request);
		
		// 유효성 검사
        if (token != null && jwtTokenProvider.getNickname(token) != null && jwtTokenProvider.getRole(token) != null) {
            if (jwtTokenProvider.validateToken(token)) {

                // 사용자 정보 추출
                String nickname = jwtTokenProvider.getNickname(token);
                String role = jwtTokenProvider.getRole(token); // "STUDENT" 또는 "EXPERT"

                // 인증 객체 생성
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                nickname,
                                null,
                                List.of(new SimpleGrantedAuthority(role))
                        );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext 에 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        
        filterChain.doFilter(request, response);
		
	}
	
	// 헤더에서 토큰 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 제거
        }
        return null;
    }
	
}
