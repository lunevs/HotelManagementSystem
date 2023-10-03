package com.ichtus.hotelmanagementsystem.model.dictionaries;

import lombok.Getter;
import org.springframework.stereotype.Component;

/**
 * Defines account role types
 * @author smlunev
 */
public enum AccountRole {

    USER,
    MODERATOR,
    ADMIN;

    @Component("AccountRole")
    @Getter
    static class SpringComponent {
        private final AccountRole USER = AccountRole.USER;
        private final AccountRole MODERATOR = AccountRole.MODERATOR;
        private final AccountRole ADMIN = AccountRole.ADMIN;
    }

}
