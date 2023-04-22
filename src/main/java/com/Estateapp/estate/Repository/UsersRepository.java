package com.Estateapp.estate.Repository;

import com.Estateapp.estate.Entity.Users;
import com.Estateapp.estate.Entity.Visitors;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    List<Users> findAll(Sort sort);

    Users findByEmail(String email);

    Users findByName(String name);



    @Query(value = "SELECT COUNT(*) FROM users WHERE name LIKE %:first_name% AND SUBSTRING_INDEX(name, ' ', 1) = :second_name", nativeQuery = true)
    int checkFamilyMembers(@Param("first_name") String surname, @Param("second_name") String surnames);

}
