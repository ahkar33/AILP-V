package com.ace.ailpv.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsById(String id);

    boolean existsByIdAndPassword(String id, String password);

    List<User> findByBatchList_id(Long id);

}
