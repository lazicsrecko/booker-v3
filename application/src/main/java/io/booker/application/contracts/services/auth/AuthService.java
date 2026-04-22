package io.booker.application.contracts.services.auth;

import io.booker.application.common.dtos.JwtTokenDto;
import io.booker.domain.business.models.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    JwtTokenDto generateTokens(User user);
    UserDetails validateToken(String token);
    String extractUsername(String token);
}
