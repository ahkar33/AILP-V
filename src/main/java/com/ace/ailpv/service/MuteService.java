package com.ace.ailpv.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.Mute;
import com.ace.ailpv.repository.MuteRepository;

@Service
public class MuteService {

    @Autowired
    private MuteRepository muteRepository;

    public Mute getMuteByBatchIdAndUserId(Long batchId, String userId) {
        return muteRepository.findByMuteBatch_IdAndMuteUser_Id(batchId, userId);
    }

    public void addMute(Mute mute) {
        muteRepository.save(mute);
    }

}
