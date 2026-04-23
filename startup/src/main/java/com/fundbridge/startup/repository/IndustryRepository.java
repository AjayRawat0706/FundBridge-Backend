package com.fundbridge.startup.repository;

import com.fundbridge.startup.model.Industry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndustryRepository extends JpaRepository<Industry,Integer> {
}
