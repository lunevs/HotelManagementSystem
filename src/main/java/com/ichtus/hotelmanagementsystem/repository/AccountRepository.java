package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.dao.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Long> {
}
