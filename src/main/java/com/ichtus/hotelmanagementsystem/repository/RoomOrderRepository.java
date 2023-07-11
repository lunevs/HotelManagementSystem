package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.configuration.RoomOrderStatus;
import com.ichtus.hotelmanagementsystem.domain.RoomOrder;
import com.ichtus.hotelmanagementsystem.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoomOrderRepository extends CrudRepository<RoomOrder, Long> {

    List<RoomOrder> findAllByUser(User user);


    List<RoomOrder> getAllByStatus(RoomOrderStatus status);
}
