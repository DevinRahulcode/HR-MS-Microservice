package com.example.leave_management_service.records;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

// A "record" is a modern, compact class
public record UserPrincipal(
        Long id,
        String email,
        Collection<? extends GrantedAuthority> authorities
) {
}
