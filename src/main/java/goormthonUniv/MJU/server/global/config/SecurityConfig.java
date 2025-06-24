package goormthonUniv.MJU.server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
		return web -> web.ignoring()
				.requestMatchers( "swagger-ui/**");
	}

    // JWT 보안 필터와 HTTP 보안 구성
    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				// CORS 비활성화
				.cors(cors -> cors.disable())
				// CSRF 공격 방어 비활성화
				.csrf(csrf -> csrf.disable())
				// 폼 로그인 비활성화
				.formLogin(formLogin -> formLogin.disable())
				// HTTP 기본 인증 비활성화
				.httpBasic(httpBasic -> httpBasic.disable())
				// 세션을 사용하지 않음 (STATELESS)
				.sessionManagement(sessionManagement ->
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				// JWT 인증 필터를 UsernamePasswordAuthenticationFilter 이전에 추가
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build(); // SecurityFilterChain 빌드 및 반환
	}
    
}
