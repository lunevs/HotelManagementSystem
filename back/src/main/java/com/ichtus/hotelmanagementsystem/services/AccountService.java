package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.dto.account.*;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.repository.AccountRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

/**
 * Defines services to interact with application accounts
 * @author smlunev
 */
@Service
@RequiredArgsConstructor
public class AccountService implements UserDetailsService  {

    private final AccountRepository accountRepository;
    private final RoleService roleService;

    /**
     * Find all non deleted accounts
     * @return list ResponseAccountData with accounts data
     */
    public List<ResponseAccountData> findAll() {
        return accountRepository.findAllByDeleted(false).stream().map(ResponseAccountData::of).toList();
    }

    /**
     * Check for existing and find user account by it's name
     * @param name searched account name string
     * @return account data
     */
    public Account findAccountByName(String name) {
        return accountRepository
                .findByAccountName(name)
                .orElseThrow(
                        () -> new ObjectNotFoundException((Object) name, Account.class.getName())
                );
    }

    /**
     * Check for existing and find user account by it's id
     * @param id searched account id
     * @return account data
     */
    public Account findAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(
                        () -> new ObjectNotFoundException(id, Account.class.getName())
                );
    }

    /**
     * Update account common information
     * @param id updated account id
     * @param updateRequest dto with new account information
     * @return dto ResponseAccountData with updated account
     */
    public ResponseAccountData accountUpdateInfo(Long id, RequestAccountChange updateRequest) {
        Account accountToUpdate = findAccountById(id)
                .setAccountName(updateRequest.getAccountName())
                .setAccountPassword(updateRequest.getAccountPassword())
                .setAccountEmail(updateRequest.getAccountEmail());
        return ResponseAccountData.of(accountRepository.save(accountToUpdate));
    }

    /**
     * Change account role
     * @param id updated account id
     * @param roleName new role name
     * @return dto ResponseAccountData with updated account
     */
    public ResponseAccountData accountUpdateRole(Long id, String roleName) {
        Account accountToUpdate = findAccountById(id)
                .setRole(roleService.getRoleByName(roleName));
        return ResponseAccountData.of(accountRepository.save(accountToUpdate));
    }

    /**
     * Construct spring security object UserDetails by account name
     * @param username searched account name
     * @return object UserDetails for searched account
     * @throws UsernameNotFoundException if not found account with such name
     */
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

    /**
     * Creates new application user account with default user role
     * @param requestAccountChange dto with new account information
     * @return dto ResponseAccountData with created account data
     */
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

    /**
     * Marks account as deleted
     * @param id deleted account id
     */
    public void deleteAccount(Long id) {
        Account accountToUpdate = findAccountById(id).setDeleted(true);
        accountRepository.save(accountToUpdate);
    }
}
