package com.ichtus.hotelmanagementsystem.repository;

import com.ichtus.hotelmanagementsystem.model.dao.Location;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LocationRepository extends JpaRepository<Location, Long> {
}
