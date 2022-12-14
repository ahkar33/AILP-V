package com.ace.ailpv.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ace.ailpv.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

    boolean existsById(String id);

    boolean existsByIdAndPassword(String id, String password);

    List<User> findByBatchList_id(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.name=?1 WHERE u.id=?2", nativeQuery=true)
    void updateNameById(String name, String uid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.profile_pic=?1 WHERE u.id=?2", nativeQuery=true)
    void updatePictureById(String pictureName, String uid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.password=?1 WHERE u.id=?2", nativeQuery=true)
    void updatePasswordById(String newPassword, String uid);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.enabled=?1 WHERE u.id=?2", nativeQuery=true)
    void toggleAccountStatus(boolean status, String uid);

    @Query(value = "select user.read_messages_count from user where user.id = ?1", nativeQuery = true)
    Long findUserReadMessagesCountById(String id);

    @Query(value = "select count(id) from user where role=?1",nativeQuery = true)
    int userCountByUserRole(String userRole);

    int countByBatchList_batchCourse_IdAndRole(Long id, String role);

    int countByBatchList_IdAndRole(Long id, String role);

}
