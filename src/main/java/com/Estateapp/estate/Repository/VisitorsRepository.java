package com.Estateapp.estate.Repository;

import com.Estateapp.estate.Entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {

    @Query(value = "SELECT * FROM visitors WHERE visitor_code = :visitor_code", nativeQuery = true)
    Visitors findByVisitorCode(@Param("visitor_code") String visitor_code);

//    @Modifying
//    @Query(value = "update visitors set entry_status = :entry_status WHERE visitor_code = :visitor_code", nativeQuery = true)
//    @Transactional
//    void checkVisitors(@Param("entry_status") String entryStatus, @Param("visitor_code") String visitorCode);


}
