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
import com.ace.ailpv.entity.BatchHasResource;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.repository.BatchHasResourceRepository;
import com.ace.ailpv.service.BatchHasResourceService;

@SpringBootTest
public class BatchHasResourceServiceTest {
    
    @Mock
    BatchHasResourceRepository batchHasResourceRepository;

    @InjectMocks
    BatchHasResourceService batchHasResourceService;

    @Test
    public void addBatchHasResourceTest(){
        BatchHasResource bhs =getBatchHasResource();
        batchHasResourceService.addBatchHasResource(bhs);
        verify(batchHasResourceRepository,times(1)).save(bhs);

    }

    @Test
    public void deleteBatchHasResourceByIdTest(){
        BatchHasResource bhs =getBatchHasResource();
        batchHasResourceService.deleteBatchHasResourceById(bhs.getId());
        verify(batchHasResourceRepository,times(1)).deleteById(bhs.getId());
    }

    @Test
    public void getBatchHasResourceByBatchIdAndResourceIdTest(){
        BatchHasResource bhs=getBatchHasResource();
        Batch batch=getOneBatch();
        Resource resource=getOneResource();
        when(batchHasResourceRepository.findByBatchIdAndResourceId(batch.getId(), resource.getId()));
        BatchHasResource bhsActual =batchHasResourceService.getBatchHasResourceByBatchIdAndResourceId(batch.getId(), resource.getId());
        assertEquals(bhs.getResource(), bhsActual.getResource());
        assertEquals(bhs.getBatch(), bhsActual.getBatch());
        assertEquals(bhs.getSchedule(), bhsActual.getSchedule());
        verify(batchHasResourceRepository,times(1)).findByBatchIdAndResourceId(batch.getId(), resource.getId());
    }

    @Test
    public void getAllBatchHasResourcesByBatchIdTest(){
        List<BatchHasResource>bhsList=getBatchHasResourceList();
        when(batchHasResourceRepository.findByBatch_Id(1L)).thenReturn(bhsList);
        verify(batchHasResourceRepository,times(1)).findByBatch_Id(1L);

    }

    private BatchHasResource getBatchHasResource(){
        BatchHasResource bhs=new BatchHasResource();
        bhs.setId(1L);
        bhs.setResource(new Resource());
        bhs.setBatch(new Batch());
        bhs.setSchedule(LocalDateTime.now());
        return bhs;
    }
    
    private List<BatchHasResource>getBatchHasResourceList(){
        List<BatchHasResource>bhsList=new ArrayList<>();
        BatchHasResource bhs=new BatchHasResource();
        bhs.setId(1L);
        bhs.setResource(new Resource());
        bhs.setBatch(new Batch());
        bhs.setSchedule(LocalDateTime.now());
        bhsList.add(bhs);
        return bhsList;
        
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

    private Resource getOneResource(){
        Resource resource=new Resource();
        resource.setId(1L);
        resource.setName("ppt");
        resource.setResourceCourse(new Course());
        return resource;
    }

}
