package com.ichtus.hotelmanagementsystem.model.anotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for Spring security. Defines Moderator flag
 * @author smlunev
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAnyRole(@AccountRole.MODERATOR, @AccountRole.ADMIN)")
public @interface IsModerator {
}
