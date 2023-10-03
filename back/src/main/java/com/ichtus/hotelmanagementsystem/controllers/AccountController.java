package com.ichtus.hotelmanagementsystem.controllers;


import com.ichtus.hotelmanagementsystem.model.anotations.IsAdministrator;
import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountChange;
import com.ichtus.hotelmanagementsystem.model.dto.account.ResponseAccountData;
import com.ichtus.hotelmanagementsystem.model.dto.account.RequestAccountRoleChange;
import com.ichtus.hotelmanagementsystem.services.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller allows to manage accounts
 *
 * @author smlunev
 */
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    /**
     * Endpoint to create new account
     * @param requestAccountChange request with new account parameters
     * @return ResponseAccountData
     */
    @PostMapping
    @IsAdministrator
    public ResponseEntity<ResponseAccountData> createNewUserAccount(@Valid @RequestBody RequestAccountChange requestAccountChange) {
        return ResponseEntity.ok(accountService.createNewAccount(requestAccountChange));
    }

    /**
     * Endpoint to get all non deleted accounts list
     * @return list of ResponseAccountData
     */
    @GetMapping
    @IsAdministrator
    public ResponseEntity<List<ResponseAccountData>> getAllAccounts() {
        return ResponseEntity.ok(accountService.findAll());
    }

    /**
     * Endpoint to get one Account details
     * @param id account id
     * @return ResponseAccountData
     */
    @GetMapping("/{id}")
    @IsAdministrator
    public ResponseEntity<ResponseAccountData> getAccountDetails(@PathVariable Long id) {
        return ResponseEntity.ok(ResponseAccountData.of(accountService.findAccountById(id)));
    }

    /**
     * Endpoint to update common Account information
     * @param id updated account id
     * @param accountUpdateRequest new account parameters
     * @return ResponseAccountData
     */
    @PutMapping("/{id}/info")
    @IsAdministrator
    public ResponseEntity<ResponseAccountData> updateAccountInfo(@PathVariable Long id, @Valid @RequestBody RequestAccountChange accountUpdateRequest) {
        return ResponseEntity.ok(accountService.accountUpdateInfo(id, accountUpdateRequest));
    }

    /**
     * Endpoint to change Account Role
     * @param id updated Account id
     * @param roleChange dto of new Account role
     * @return ResponseAccountData
     */
    @PutMapping("/{id}/role")
    @IsAdministrator
    public ResponseEntity<ResponseAccountData> updateAccountRole(@PathVariable Long id, @Valid @RequestBody RequestAccountRoleChange roleChange) {
        return ResponseEntity.ok(accountService.accountUpdateRole(id, roleChange.getRole().name()));
    }

    /**
     * Endpoint to delete Account.
     * @param id deleted Account id
     * @return empty response with status 200
     */
    @DeleteMapping("/{id}")
    @IsAdministrator
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
