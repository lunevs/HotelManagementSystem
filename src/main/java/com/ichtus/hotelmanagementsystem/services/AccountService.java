package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dto.account.RegistrationRequest;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService  {

    private final AccountRepository accountRepository;
    private final RoleService roleService;

    public Optional<Account> findByName(String name) {
        return accountRepository.findByAccountName(name);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByName(username).orElseThrow(() -> new AccountNotFoundException(username));
        return new User(
                account.getAccountName(),
                account.getAccountPassword(),
                account.getRoles().stream()
                        .map(el -> new SimpleGrantedAuthority(el.getName()))
                        .toList()
        );
    }

    public Account createNewAccount(RegistrationRequest registrationRequest) {
        Account account = new Account()
                .setAccountName(registrationRequest.getUsername())
                .setAccountPassword(registrationRequest.getPassword())
                .setAccountEmail(registrationRequest.getEmail())
                .setRoles(List.of(roleService.getUserRole()));
        return accountRepository.save(account);
    }
}
