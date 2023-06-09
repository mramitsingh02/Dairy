package com.generic.khatabook.repository;

import com.generic.khatabook.entity.WorkStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkStationRepository extends JpaRepository<WorkStation, Long> {
}
