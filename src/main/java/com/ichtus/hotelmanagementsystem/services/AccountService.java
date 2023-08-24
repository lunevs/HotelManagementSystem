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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
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
        if (!updateRequest.getUsername().isBlank()) {
            accountToUpdate.setAccountName(updateRequest.getUsername());
        }
        if (!updateRequest.getPassword().isBlank()) {
            accountToUpdate.setAccountPassword(updateRequest.getPassword());
        }
        if (!updateRequest.getEmail().isBlank()) {
            accountToUpdate.setAccountEmail(updateRequest.getEmail());
        }
        return AccountDetailResponse.of(accountRepository.save(accountToUpdate));
    }

    public AccountDetailResponse accountUpdateRoles(Long id, RoleUpdateActionType roleAction, AccountRole roleValue) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        Collection<Role> rolesList = accountToUpdate.getRoles();

        if (roleAction != RoleUpdateActionType.ADD && roleAction != RoleUpdateActionType.REMOVE) {
            throw new RoleNotFoundException(roleValue.name());
        } else {
            if (roleAction == RoleUpdateActionType.ADD) {
                rolesList.add(roleService.getRoleByName(roleValue.name()));
            } else {
                accountToUpdate.setRoles(
                        rolesList.stream()
                                .filter(el -> !el.getName().equals(roleValue.name()))
                                .toList()
                );
            }
        }

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
                account.getRoles().stream()
                        .map(el -> new SimpleGrantedAuthority(el.getName()))
                        .toList()
        );
    }

    public RegistrationResponse createNewAccount(RegistrationRequest registrationRequest) {
        if (findByName(registrationRequest.getUsername()).isPresent()) {
            throw new AccountAlreadyExists(registrationRequest.getUsername());
        }
        Account savedAccount = accountRepository.save(
                new Account()
                        .setAccountName(registrationRequest.getUsername())
                        .setAccountPassword(registrationRequest.getPassword())
                        .setAccountEmail(registrationRequest.getEmail())
                        .setRoles(List.of(roleService.getUserRole()))
        );
        return new RegistrationResponse(
                savedAccount.getId(),
                savedAccount.getAccountName(),
                savedAccount.getAccountEmail()
        );
    }

    public void deleteAccount(Long id) {
        Account accountToUpdate = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        accountToUpdate.setDeleted(true);
        accountRepository.save(accountToUpdate);
    }
}
