package goormthonUniv.MJU.server.global.config;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String key;
	private Key secretKey;

	// 만료 시간
    private final long accessTokenValidTime = 1000L * 60 * 60; // 1시간
	
	@PostConstruct
	protected void init() {
		this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(key));
	}
	
	// AccessToken 생성 (닉네임 + 역할 포함)
	public String generateAccessToken(Long memberId, String role) {
        return generateToken(memberId, role, accessTokenValidTime);
    }

    // 토큰 생성
    private String generateToken(Long memberId, String role, long expireTime) {
    	Claims claims = Jwts.claims().setSubject(String.valueOf(memberId));
        if (role != null && !role.isEmpty()) {
            claims.put("role", role);
        }

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expireTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
	
	// 토큰에서 memberId 추출
    public Long getMemberId(String token) {
        try {
            Claims claims = getAllClaims(token);
            return Long.valueOf(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }
	
	// 토큰에서 단일 권한(role) 추출
    public String getRole(String token) {
        return (String) getAllClaims(token).get("role");
    }
	
	// Claims 추출
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 만료 시간 검사
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
