package com.ichtus.hotelmanagementsystem.services;

import com.ichtus.hotelmanagementsystem.configuration.RoomOrderStatus;
import com.ichtus.hotelmanagementsystem.domain.RoomOrder;
import com.ichtus.hotelmanagementsystem.domain.Room;
import com.ichtus.hotelmanagementsystem.domain.User;
import com.ichtus.hotelmanagementsystem.dto.RoomOrderDto;
import com.ichtus.hotelmanagementsystem.repository.RoomOrderRepository;
import com.ichtus.hotelmanagementsystem.repository.RoomRepository;
import com.ichtus.hotelmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomOrderServiceImpl implements RoomOrderService {

    private final RoomOrderRepository orderRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public List<RoomOrder> getAllActiveOrders() {
        return orderRepository.getAllByStatus(RoomOrderStatus.ACTIVE);
    }

    @Override
    public RoomOrder saveNewOrder(RoomOrderDto roomOrderDTO) {
        User user = userRepository.findByName(roomOrderDTO.getUserName()).orElseThrow();
        Room room = roomRepository.findByTitle(roomOrderDTO.getRoomTitle()).orElseThrow();
        RoomOrder roomOrder = new RoomOrder(
                null,
                room,
                user,
                RoomOrderStatus.ACTIVE,
                roomOrderDTO.getStartDate(),
                roomOrderDTO.getEndDate()
        );
        return orderRepository.save(roomOrder);
    }


}
