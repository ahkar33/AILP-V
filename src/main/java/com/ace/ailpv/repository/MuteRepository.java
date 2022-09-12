package com.ace.ailpv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ailpv.entity.Mute;

public interface MuteRepository extends JpaRepository<Mute, Long> {

    Mute findByMuteBatch_IdAndMuteUser_Id(Long batchId, String userId);

}
