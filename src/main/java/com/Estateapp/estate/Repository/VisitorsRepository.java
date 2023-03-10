package com.Estateapp.estate.Repository;

import com.Estateapp.estate.Entity.Visitors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface VisitorsRepository extends JpaRepository<Visitors, Long> {

    @Query(value = "SELECT * FROM visitors WHERE visitor_code = :visitor_code", nativeQuery = true)
    Visitors findByVisitorCode(@Param("visitor_code") String visitor_code);


    @Query(value = "SELECT * FROM visitors WHERE whom_to_see = :whom_to_see", nativeQuery = true)
    List<Visitors> findAllVisitorsForAUser(@Param("whom_to_see") String resident);


}
