package com.ichtus.hotelmanagementsystem.model.dto.account;

import com.ichtus.hotelmanagementsystem.model.entities.Account;
import com.ichtus.hotelmanagementsystem.model.entities.Booking;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.model.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAccountData {

    private long id;
    private String accountName;
    private String accountEmail;
    private List<Hotel> hotels;
    private List<Booking> bookings;
    private Role role;

    public static ResponseAccountData of(Account account) {
        return new ResponseAccountData()
                    .setId(account.getId())
                    .setAccountName(account.getAccountName())
                    .setAccountEmail(account.getAccountEmail())
                    .setHotels(account.getHotels())
                    .setBookings(account.getBookings())
                    .setRole(account.getRole());
    }
}
