package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.AccountAlreadyExists;
import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.exceptions.RoleNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dictionaries.AccountRole;
import com.ichtus.hotelmanagementsystem.model.dictionaries.RoleUpdateActionType;
import com.ichtus.hotelmanagementsystem.model.dto.account.*;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements UserDetailsService  {

    private final AccountRepository accountRepository;
    private final RoleService roleService;


    public List<AccountShortResponse> findAll() {
        return accountRepository.findAllByDeleted(false).stream().map(AccountShortResponse::of).toList();
    }

    public Optional<Account> findByName(String name) {
        return accountRepository.findByAccountName(name);
    }

    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    public AccountDetailResponse accountUpdateInfo(Long id, AccountUpdateRequest updateRequest) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        if (updateRequest.getAccountName() != null && !updateRequest.getAccountName().isBlank()) {
            accountToUpdate.setAccountName(updateRequest.getAccountName());
        }
        if (updateRequest.getAccountPassword() != null && !updateRequest.getAccountPassword().isBlank()) {
            accountToUpdate.setAccountPassword(updateRequest.getAccountPassword());
        }
        if (updateRequest.getAccountEmail() != null && !updateRequest.getAccountEmail().isBlank()) {
            accountToUpdate.setAccountEmail(updateRequest.getAccountEmail());
        }
        return AccountDetailResponse.of(accountRepository.save(accountToUpdate));
    }

    public AccountDetailResponse accountUpdateRoles(Long id, String roleName) {
        log.info("accountUpdateRoles: " + id);
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        log.info(accountToUpdate.getAccountName());
        log.info(roleName);
        accountToUpdate.setRole(roleService.getRoleByName(roleName));
        log.info(accountToUpdate.toString());
        Account savedAccount = accountRepository.save(accountToUpdate);
        return AccountDetailResponse.of(savedAccount);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByName(username).orElseThrow(() -> new AccountNotFoundException(username));
        return new User(
                account.getAccountName(),
                account.getAccountPassword(),
                Stream.of(account.getRole())
                        .map(el -> new SimpleGrantedAuthority(el.getName()))
                        .toList()
        );
    }

    public RegistrationResponse createNewAccount(RegistrationRequest registrationRequest) {
        if (findByName(registrationRequest.getAccountName()).isPresent()) {
            throw new AccountAlreadyExists(registrationRequest.getAccountName());
        }
        Account savedAccount = accountRepository.save(
                new Account()
                        .setAccountName(registrationRequest.getAccountName())
                        .setAccountPassword(
                                "{bcrypt}" +
                                new BCryptPasswordEncoder().encode(registrationRequest.getAccountPassword())
                        )
                        .setAccountEmail(registrationRequest.getAccountEmail())
                        .setRole(roleService.getUserRole())
        );
        return new RegistrationResponse(
                savedAccount.getId(),
                savedAccount.getAccountName(),
                savedAccount.getAccountEmail(),
                savedAccount.getRole()
        );
    }

    public void deleteAccount(Long id) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        accountToUpdate.setDeleted(true);
        accountRepository.save(accountToUpdate);
    }
}
