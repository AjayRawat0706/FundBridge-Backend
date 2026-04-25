package com.fundbridge.startup.repository;

import com.fundbridge.startup.model.Startup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Repository
public interface StartupRepository extends JpaRepository<Startup, UUID> {
    List<Startup> findByOwnerId(UUID ownerId);

    @Query("""
    SELECT s FROM startups s
    JOIN s.funding f
    JOIN f.fundingStage fs
    WHERE (:industryIds IS NULL OR s.industryId IN :industryIds)
    AND (:stageIds IS NULL OR fs.id IN :stageIds)
    AND (:min IS NULL OR f.amountRequired >= :min)
    AND (:max IS NULL OR f.amountRequired <= :max)
    """)
    List<Startup> filterStartups(
            @Param("industryIds") List<Integer> industryIds,
            @Param("stageIds") List<Integer> stageIds,
            @Param("min") BigDecimal min,
            @Param("max") BigDecimal max
    );
}
