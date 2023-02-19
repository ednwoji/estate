package com.Estateapp.estate.Repository;

import com.Estateapp.estate.Entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {
//    Visitors findByVisitor_code(String visitorid);


    @Query(value = "SELECT * FROM visitors WHERE visitor_code = :visitor_code", nativeQuery = true)
    Visitors findByVisitorCode(@Param("visitor_code") String visitor_code);
}
