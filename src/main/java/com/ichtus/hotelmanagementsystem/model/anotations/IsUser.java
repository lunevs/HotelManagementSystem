package com.ichtus.hotelmanagementsystem.model.anotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole(@AccountRole.USER, @AccountRole.MODERATOR, @AccountRole.ADMIN)")
public @interface IsUser {
}
