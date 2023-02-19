package com.Estateapp.estate.Repository;

import com.Estateapp.estate.Entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
}
