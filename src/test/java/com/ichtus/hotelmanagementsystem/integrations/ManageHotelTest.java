package com.ichtus.hotelmanagementsystem.integrations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.RequestHotelChange;
import com.ichtus.hotelmanagementsystem.model.dto.hotels.ResponseHotelData;
import com.ichtus.hotelmanagementsystem.model.entities.Hotel;
import com.ichtus.hotelmanagementsystem.repository.HotelRepository;
import com.ichtus.hotelmanagementsystem.services.HotelService;
import com.ichtus.hotelmanagementsystem.utils.anotations.WithMockAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureTestEntityManager
@AutoConfigureMockMvc
@Transactional
public class ManageHotelTest {

    @Value("${custom.rest.basePath}/hotels")
    String basePath;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService hotelService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RequestHotelChange mockHotel = new RequestHotelChange()
            .setHotelName("test hotel")
            .setHotelCity("London")
            .setHotelDescription("some hotel description some hotel description");

    @Test
    @WithMockAdmin
    void willCreateNewHotel() throws Exception {

        mockMvc.perform(post(basePath)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockHotel))
                )
                .andExpect(jsonPath("$.id").exists());

        List<Hotel> hotels = hotelRepository.findAll();

        assertThat(hotels.size()).isGreaterThanOrEqualTo(1);
        assertThat(hotels.stream().filter(el -> el.getHotelName().equals("test hotel")).toList()).hasSize(1);
    }

    @Test
    @WithMockAdmin
    void willChangeHotelInfo() throws Exception {

        Long hotelId = 1L;
        ResponseHotelData hotelBeforeUpdate = hotelService.getHotelInfo(hotelId);

        mockMvc.perform(put(basePath + "/" + hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockHotel)))
                .andExpect(jsonPath("$.id").exists());

        ResponseHotelData hotelAfterUpdate = hotelService.getHotelInfo(hotelId);

        assertThat(hotelBeforeUpdate.getHotelName()).isNotEqualTo(hotelAfterUpdate.getHotelName());
        assertThat(hotelBeforeUpdate.getHotelCity()).isNotEqualTo(hotelAfterUpdate.getHotelCity());
        assertThat(hotelBeforeUpdate.getHotelDescription()).isNotEqualTo(hotelAfterUpdate.getHotelDescription());

        assertThat(hotelAfterUpdate.getHotelDescription()).isEqualTo(mockHotel.getHotelDescription());
        assertThat(hotelAfterUpdate.getHotelName()).isEqualTo(mockHotel.getHotelName());
        assertThat(hotelAfterUpdate.getHotelCity()).isEqualTo(mockHotel.getHotelCity());
    }

    @Test
    @WithMockAdmin
    void willDeleteHotel() throws Exception {

        long hotelId = 1L;

        mockMvc.perform(delete(basePath + "/" + hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockHotel)))
                .andExpect(status().isOk());

        List<ResponseHotelData> hotelAfterUpdate = hotelService.getHotelsList();

        assertThat(hotelAfterUpdate.stream().filter(el -> el.getId() == hotelId).toList()).isEmpty();
    }

}
