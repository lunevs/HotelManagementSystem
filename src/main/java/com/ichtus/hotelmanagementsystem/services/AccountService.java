package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.account.*;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.repository.AccountRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@Validated
public class AccountService implements UserDetailsService  {

    private final AccountRepository accountRepository;
    private final RoleService roleService;


    public List<ResponseAccountData> findAll() {
        return accountRepository.findAllByDeleted(false).stream().map(ResponseAccountData::of).toList();
    }

    public Account findAccountByName(String name) {
        return accountRepository
                .findByAccountName(name)
                .orElseThrow(
                        () -> new org.hibernate.ObjectNotFoundException((Object) name, Account.class.getName())
                );
    }

    public Account findAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(id, Account.class.getName())
                );
    }

    public ResponseAccountData accountUpdateInfo(Long id, RequestAccountChange updateRequest) {
        Account accountToUpdate = findAccountById(id)
                .setAccountName(updateRequest.getAccountName())
                .setAccountPassword(updateRequest.getAccountPassword())
                .setAccountEmail(updateRequest.getAccountEmail());
        return ResponseAccountData.of(accountRepository.save(accountToUpdate));
    }

    public ResponseAccountData accountUpdateRole(Long id, String roleName) {
        Account accountToUpdate = findAccountById(id)
                .setRole(roleService.getRoleByName(roleName));
        return ResponseAccountData.of(accountRepository.save(accountToUpdate));
    }

    @Override
    @Generated
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findAccountByName(username);
        return new User(
                account.getAccountName(),
                account.getAccountPassword(),
                account.getRole() == null
                        ? Stream.of(roleService.getUserRole())
                            .map(el -> new SimpleGrantedAuthority(el.getName()))
                            .toList()
                        : Stream.of(account.getRole())
                            .map(el -> new SimpleGrantedAuthority(el.getName()))
                            .toList()
        );
    }

    public ResponseAccountData createNewAccount(@Valid RequestAccountChange requestAccountChange) {
        if (accountRepository.findByAccountName(requestAccountChange.getAccountName()).isPresent()) {
            throw new EntityExistsException(requestAccountChange.getAccountName());
        }
        Account newAccount = new Account()
                .setAccountName(requestAccountChange.getAccountName())
                .setAccountPassword(
                        "{bcrypt}" + new BCryptPasswordEncoder().encode(requestAccountChange.getAccountPassword())
                )
                .setAccountEmail(requestAccountChange.getAccountEmail())
                .setRole(roleService.getUserRole());
        return ResponseAccountData.of(accountRepository.save(newAccount));
    }

    public void deleteAccount(Long id) {
        Account accountToUpdate = findAccountById(id).setDeleted(true);
        accountRepository.save(accountToUpdate);
    }
}
