package com.example.leave_management_service.utils;

import com.example.leave_management_service.records.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserPrincipal getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return (UserPrincipal) principal;
        }
        // This would happen if the user is anonymous, but our filter stack prevents that
        return null;
    }

    public static Long getCurrentUserId() {
        UserPrincipal user = getCurrentUser();
        return (user != null) ? user.id() : null;
    }

    public static String getCurrentUserEmail() {
        UserPrincipal user = getCurrentUser();
        return (user != null) ? user.email() : null;
    }
}