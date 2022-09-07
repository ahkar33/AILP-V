package com.ace.ailpv.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.BatchHasVideo;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.BatchHasVideoRepository;
import com.ace.ailpv.service.BatchHasVideoService;

@SpringBootTest
public class BatchHasVideoServiceTest {

    @Mock
    BatchHasVideoRepository batchHasVideoRepository;

    @InjectMocks
    BatchHasVideoService batchHasVideoService;

    @Test
    public void addBatchHasVideoTest(){
        BatchHasVideo bhv=getBatchHasVideo();
        batchHasVideoService.addBatchHasVideo(bhv);
        verify(batchHasVideoRepository,times(1)).save(bhv);
    }

    @Test
    public void deleteBatchHasVideoById(){
        BatchHasVideo bhv=getBatchHasVideo();
        batchHasVideoService.deleteBatchHasVideoById(bhv.getId());
        verify(batchHasVideoRepository,times(1)).deleteById(bhv.getId());
    }

    @Test
    public void getBatchHasVideoyBatchIdAndVideoIdTest(){
        Batch batch=getOneBatch();
        Video video=getOneVideo();
        BatchHasVideo bhv=getBatchHasVideo();
        when(batchHasVideoRepository.findByBhvBatch_IdAndVideo_Id(batch.getId(), video.getId())).thenReturn(bhv);
        BatchHasVideo bhvActual=batchHasVideoService.getBatchHasVideoyBatchIdAndVideoId(batch.getId(), video.getId());
        assertEquals(bhv.getId(), bhvActual.getId());
        assertEquals(bhv.getBhvBatch(), bhvActual.getBhvBatch());
        assertEquals(bhv.getSchedule(), bhvActual.getSchedule());
        verify(batchHasVideoRepository,times(1)).findByBhvBatch_IdAndVideo_Id(batch.getId(), video.getId());
    }

    @Test
    public void getAllBatchHasVideoByBatchIdTest(){
        List<BatchHasVideo> bhvList=getBhvList();
        when(batchHasVideoRepository.findByBhvBatch_Id(1L)).thenReturn(bhvList);
        List<BatchHasVideo>bhvListActual=batchHasVideoService.getAllBatchHasVideoByBatchId(1L);
        assertEquals(bhvList.size(), bhvListActual.size());
        verify(batchHasVideoRepository,times(1)).findByBhvBatch_Id(1L);

    }


    private BatchHasVideo getBatchHasVideo(){
        BatchHasVideo bhv = new BatchHasVideo();
        bhv.setId(1L);
        bhv.setVideo(new Video());
        bhv.setBhvBatch(new Batch());
        bhv.setSchedule(LocalDateTime.now());
        return bhv;
    }

    private List<BatchHasVideo>getBhvList(){
        List<BatchHasVideo>bhvList=new ArrayList<>();
        BatchHasVideo bhv = new BatchHasVideo();
        bhv.setId(1L);
        bhv.setVideo(new Video());
        bhv.setBhvBatch(new Batch());
        bhv.setSchedule(LocalDateTime.now());
        bhvList.add(bhv);
        return bhvList;
    }

    private Batch getOneBatch(){
        Batch batch=new Batch();
        batch.setId(1L);
        batch.setName("Batch01");
        batch.setStartDate(LocalDate.of(2022, 10, 10));
        batch.setEndDate(LocalDate.of(2022, 10, 10));
        batch.setBatchCourse(new Course());
        batch.setUserList(new ArrayList<>());
        return batch;
    } 

    private Video getOneVideo(){
        Course videoCourse = new Course();
        videoCourse.setId(3L);

        Video video = new Video();
        video.setId(1L);
        video.setName("blah blah");
        video.setVideoCourse(videoCourse);
         
        return video;
    }
    
}
