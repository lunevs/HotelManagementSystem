package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.domain.RoomOrder;
import com.ichtus.hotelmanagementsystem.dto.RoomOrderDto;

import java.util.List;

public interface RoomOrderService {

    List<RoomOrder> getAllActiveOrders();

    RoomOrder saveNewOrder(RoomOrderDto roomOrderDTO);
}
