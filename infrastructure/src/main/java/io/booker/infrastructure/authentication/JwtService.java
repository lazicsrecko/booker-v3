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
import jakarta.transaction.Transactional;
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
    private final String issuer;
    private final String audience;
    private final Long accessTokenExpiration;
    private final Long refreshTokenExpiration;
    private final UserRepository userRepository;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.accessTokenExpiresIn}") Long accessTokenExpiration,
                      @Value("${jwt.refreshTokenExpiresIn}") Long refreshTokenExpiration,
                      @Value("${jwt.issuer}") String issuer,
                      @Value("${jwt.audiance}") String audience,
                      UserRepository userRepository) {
        this.secret = secret;
        this.issuer = issuer;
        this.audience = audience;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.userRepository = userRepository;
    }

    @Override
    public JwtTokenDto generateTokens(User user) {
        return new JwtTokenDto(generateAccessToken(user), generateRefreshToken(user));
    }

    @Transactional
    @Override
    public UserDetails validateToken(String token) {
        Boolean isValid = validateTokenInternal(token);

        if (!isValid) {
            return null;
//            throw new Exception("Expired token.");
        }

        String username = extractUsername(token);
        User user = this.userRepository.findByUsername(username);

        String roleClaim = extractClaim(token, claims -> claims.get(CustomClaimTypes.ROLE, String.class));
        Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", roleClaim.toUpperCase())));
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

    private Boolean validateTokenInternal(String token) {
        try {
            String issuer = extractClaim(token, Claims::getIssuer);
            Boolean isExpired = isTokenExpired(token);
            String username = extractUsername(token);
            this.userRepository.findByUsername(username);

            if (isExpired) {
                return false;
            }

            if (!issuer.contains(this.issuer)) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
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
        Date expirationDateTime = Date.from(LocalDateTime.now().plusMinutes(accessTokenExpiration).atZone(ZoneId.systemDefault()).toInstant());
        JwtBuilder jwtBuilder = Jwts.builder()
                .claims()
                .add(CustomClaimTypes.FIRST_NAME, user.getFirstName())
                .add(CustomClaimTypes.LAST_NAME, user.getLastName())
                .add(CustomClaimTypes.USERNAME, user.getUsername())
                .add(CustomClaimTypes.ROLE, user.getRole().getName())
                .add(CustomClaimTypes.USER_ID, user.getId())
                .add(CustomClaimTypes.TENANT_ID, user.getTenant().getId())
                .add(CustomClaimTypes.USER_TYPE, user.getUserType())
                .issuer(this.issuer)
                .issuedAt(currentDateTime)
                .expiration(expirationDateTime)
                .and().signWith(getSignKey());
        return jwtBuilder.compact();
    }

    private String generateRefreshToken(User user) {
        Date currentDateTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        Date expirationDateTime = Date.from(LocalDateTime.now().plusMinutes(refreshTokenExpiration).atZone(ZoneId.systemDefault()).toInstant());
        JwtBuilder jwtBuilder = Jwts.builder()
                .claims()
                .add(CustomClaimTypes.USER_ID, user.getId())
                .add(CustomClaimTypes.TENANT_ID, user.getTenant().getId())
                .issuer(this.issuer)
                .issuedAt(currentDateTime)
                .expiration(expirationDateTime)
                .and().signWith(getSignKey());
        return jwtBuilder.compact();
    }
}
