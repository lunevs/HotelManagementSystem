package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.model.entities.Role;
import com.ichtus.hotelmanagementsystem.repository.AccountRepository;
import com.ichtus.hotelmanagementsystem.exceptions.AccountNotFoundException;
import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountChange;
import com.ichtus.hotelmanagementsystem.model.dto.account.ResponseAccountData;
import com.ichtus.hotelmanagementsystem.model.entities.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    public AccountService accountService;

    @Mock
    public AccountRepository accountRepository;

    @Mock
    public RoleService roleService;

    private Account account;
    private Role role;

    @BeforeEach
    public void setup() {
        account = new Account()
                .setId(1L)
                .setAccountName("test_user")
                .setAccountPassword("123456")
                .setAccountEmail("user@user.com");
        role = new Role()
                .setId(1)
                .setName("ROLE_USER");
    }


    @Test
    void whenLoadUserByUsername_UsernameIsInvalid_thenThrowsException() {
        assertThrows(AccountNotFoundException.class, () -> accountService.loadUserByUsername(null));
    }

    @Test
    void whenCreateNewAccount_InputIsCorrect_thenOk() {

        given(accountRepository.findByAccountName(any())).willReturn(Optional.empty());
        given(roleService.getUserRole()).willReturn(role);
        given(accountRepository.save(any(Account.class))).willReturn(account);

        RequestAccountChange request = new RequestAccountChange()
                .setAccountName(account.getAccountName())
                .setAccountPassword(account.getAccountPassword())
                .setAccountEmail(account.getAccountEmail());
        ResponseAccountData savedAccount = accountService.createNewAccount(request);

        then(savedAccount).should().getAccountName().equals(account.getAccountName());

        assertThat(savedAccount).isNotNull();
        assertThat(savedAccount.getAccountName()).isEqualTo(account.getAccountName());
    }

    @Test
    void whenFindAll_thenOk() {
        given(accountRepository.findAllByDeleted(false)).willReturn(List.of(account));
        List<ResponseAccountData> accounts = accountService.findAll();
        assertThat(accounts.size()).isEqualTo(1);
    }

    @Test
    void whenFindAccountByName_thenOk() {
        given(accountRepository.findByAccountName(any(String.class))).willReturn(Optional.of(account));
        Account findAccount = accountService.findAccountByName(account.getAccountName());
        assertThat(findAccount.getAccountName()).isEqualTo(account.getAccountName());
    }

    @Test
    void whenFindAccountById_thenOk() {
        given(accountRepository.findById(any(Long.class))).willReturn(Optional.of(account));
        Account findAccount = accountService.findAccountById(account.getId());
        assertThat(findAccount.getAccountName()).isEqualTo(account.getAccountName());
    }

    @Test
    void whenUpdateAccountInfo_thenChangeInfo() {

        Account newAccount = new Account()
                .setAccountName("new_account_name")
                .setAccountPassword(account.getAccountPassword())
                .setAccountEmail(account.getAccountEmail());

        given(accountRepository.findById(any(Long.class))).willReturn(Optional.of(account));
        given(accountRepository.save(any(Account.class))).willReturn(newAccount);

        RequestAccountChange dto = new RequestAccountChange()
                .setAccountName(newAccount.getAccountName())
                .setAccountPassword(newAccount.getAccountPassword())
                .setAccountEmail(newAccount.getAccountEmail());

        ResponseAccountData response = accountService.accountUpdateInfo(account.getId(), dto);

        assertThat(response.getAccountEmail()).isEqualTo(newAccount.getAccountEmail());
        assertThat(response.getAccountName()).isEqualTo(newAccount.getAccountName());
    }

    @Test
    void whenUpdateAccountRole_thenChangeRole() {
        Role newRole = new Role().setName("ROLE_ADMIN");

        Account newAccount = new Account()
                .setAccountName("new_account_name")
                .setAccountPassword(account.getAccountPassword())
                .setAccountEmail(account.getAccountEmail())
                .setRole(newRole);

        given(accountRepository.findById(any(Long.class))).willReturn(Optional.of(account));
        given(accountRepository.save(any(Account.class))).willReturn(newAccount);

        ResponseAccountData response = accountService.accountUpdateRole(account.getId(), newRole.getName());

        assertThat(response.getRole().getName()).isEqualTo(newAccount.getRole().getName());
    }


}
