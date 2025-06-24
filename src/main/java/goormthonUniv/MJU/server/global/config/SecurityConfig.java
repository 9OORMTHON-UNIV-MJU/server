package goormthonUniv.MJU.server.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

    // JWT 보안 필터와 HTTP 보안 구성
    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().disable()
	        .csrf().disable()
	        .formLogin().disable()
	        .httpBasic().disable()
	        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안함
	        .and()
	        .headers().frameOptions().disable()
	        .and()
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/login", "/api/register").permitAll() // 로그인/회원가입 허용
	            .anyRequest().authenticated() // 나머지는 인증 필요
	        )
	        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
    
}
