package com.ichtus.hotelmanagementsystem.model.dto.account;

import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Location;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Set;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDetailResponse {

    private long id;
    private String accountName;
    private String accountEmail;
    private Set<Location> locations;
    private Set<Booking> bookings;
    private Role role;
    private boolean deleted;

    public static AccountDetailResponse of(Account account) {
        return new AccountDetailResponse(
                account.getId(),
                account.getAccountName(),
                account.getAccountEmail(),
                account.getLocations(),
                account.getBookings(),
                account.getRole(),
                account.isDeleted()
        );
    }

}
