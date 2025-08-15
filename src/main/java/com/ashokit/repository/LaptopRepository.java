package com.ashokit.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ashokit.model.Laptop;


@Repository
public interface LaptopRepository extends JpaRepository<Laptop, Long> {
	@Query("SELECT l FROM Laptop l WHERE l.model = :model AND l.available = true")
	List<Laptop> findAvailableByModel(@Param("model") String model);

}
