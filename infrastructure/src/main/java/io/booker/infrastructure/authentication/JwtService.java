package io.booker.infrastructure.authentication;

import io.booker.application.common.constants.CustomClaimTypes;
import io.booker.application.common.dtos.JwtTokenDto;
import io.booker.application.contracts.repositories.UserRepository;
import io.booker.application.contracts.services.auth.AuthService;
import io.booker.domain.business.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService implements AuthService {
    private final String secret;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final UserRepository userRepository;

    public JwtService(@Value("${app.secret}") String secret,
                      @Value("${app.accessTokenExpiresIn}") Long accessTokenExpiration,
                      @Value("${app.refreshTokenExpiresIn}") Long refreshTokenExpiration,
                      UserRepository userRepository) {
        this.secret = secret;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.userRepository = userRepository;
    }

    @Override
    public JwtTokenDto generateTokens(User user) {
        return new JwtTokenDto(generateAccessToken(user), generateRefreshToken(user));
    }

    @Override
    public UserDetails validateToken(String token) {
        String username = extractUsername(token);
        String issuer = extractClaim(token, Claims::getIssuer);
        Boolean isExpired = isTokenExpired(token);
        User user = this.userRepository.findByUsername(username);

        if (!issuer.contains("booker-rest-api") && isExpired) {
            return null;
//            throw new Exception("Expired token.");
        }
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + extractClaim(token, claims -> claims.get(CustomClaimTypes.ROLE, Long.class))));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        return userDetails;
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.get(CustomClaimTypes.USERNAME, String.class));
    }

    private SecretKey getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
    }

    private <T>T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private String generateAccessToken(User user) {
        Date currentDateTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date expiratonDateTime = Date.from(LocalDateTime.now().plusMinutes(accessTokenExpiration).atZone(ZoneId.systemDefault()).toInstant());
        JwtBuilder jwtBuilder = Jwts.builder()
                .claims()
                .add(CustomClaimTypes.FIRST_NAME, user.getFirstName())
                .add(CustomClaimTypes.LAST_NAME, user.getLastName())
                .add(CustomClaimTypes.USERNAME, user.getUsername())
                .add(CustomClaimTypes.ROLE, user.getRoleId())
                .add(CustomClaimTypes.USER_ID, user.getId())
                .add(CustomClaimTypes.TENANT_ID, user.getTenantId())
                .add(CustomClaimTypes.USER_TYPE, user.getUserType())
                .issuer("booker-rest-api")
                .issuedAt(currentDateTime)
                .expiration(expiratonDateTime)
                .and().signWith(getSignKey());
        return jwtBuilder.compact();
    }

    private String generateRefreshToken(User user) {
        Date currentDateTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date expiratonDateTime = Date.from(LocalDateTime.now().plusMinutes(refreshTokenExpiration).atZone(ZoneId.systemDefault()).toInstant());
        JwtBuilder jwtBuilder = Jwts.builder()
                .claims()
                .add(CustomClaimTypes.USER_ID, user.getId())
                .add(CustomClaimTypes.TENANT_ID, user.getTenantId())
                .issuer("booker-rest-api")
                .issuedAt(currentDateTime)
                .expiration(expiratonDateTime)
                .and().signWith(getSignKey());
        return jwtBuilder.compact();
    }
}
