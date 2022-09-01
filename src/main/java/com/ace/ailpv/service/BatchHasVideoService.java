package com.ace.ailpv.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ailpv.entity.BatchHasVideo;
import com.ace.ailpv.repository.BatchHasVideoRepository;

@Service
public class BatchHasVideoService {

    @Autowired
    private BatchHasVideoRepository batchHasVideoRepository;

    public void addBatchHasVideo(BatchHasVideo batchHasVideo) {
        batchHasVideoRepository.save(batchHasVideo);
    }

    public void deleteBatchHasVideoById(Long id) {
        batchHasVideoRepository.deleteById(id);
    }

    public BatchHasVideo getBatchHasVideoyBatchIdAndVideoId(Long batchId, Long videoId) {
        return batchHasVideoRepository.findByBhvBatch_IdAndVideo_Id(batchId, videoId);
    }

    public List<BatchHasVideo> getAllBatchHasVideoByBatchId(Long id) {
        List<BatchHasVideo> unfilteredList = batchHasVideoRepository.findByBhvBatch_Id(id);
        Long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        List<BatchHasVideo> filteredList = unfilteredList.stream()
                .filter(batchHasVideo -> currentTime >= batchHasVideo.getSchedule().toEpochSecond(ZoneOffset.UTC))
                .collect(Collectors.toList());
        return filteredList;
    }

}
