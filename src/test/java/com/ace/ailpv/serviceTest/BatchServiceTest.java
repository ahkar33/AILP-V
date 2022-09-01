package com.ace.ailpv.serviceTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;
import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.repository.BatchRepository;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.UserService;

@SpringBootTest
public class BatchServiceTest {

    @Mock
    BatchRepository batchRepository;
    @InjectMocks
    BatchService batchService;
    @Mock
    UserService userService;

    @Test
    public void addBatchTest(){
        Batch batch=getOneBatch();
        batchService.addBatch(batch);
       verify(batchRepository, times(1)).save(batch);
    }

    @Test
    public void deleteBatchByIdTest(){
        Batch batch=getOneBatch();
        List<User>userList=getUserList();
        when(userService.findUserByBatchId(batch.getId())).thenReturn(userList);
        batchService.deleteBatchById(batch.getId());
        verify(batchRepository, times(1)).deleteById(batch.getId());
    }

    @Test
    public void getBatchByIdTest(){
        Batch batch=getOneBatch();
       when(batchRepository.findById(batch.getId())).thenReturn(Optional.of(batch));
       Batch selectBach=batchService.getBatchById(batch.getId());
       assertEquals(batch.getName(), selectBach.getName());
       assertEquals(batch.getStartDate(), selectBach.getStartDate());
       assertEquals(batch.getEndDate(), selectBach.getEndDate());
       assertEquals(batch.getBatchCourse(), selectBach.getBatchCourse());
       assertEquals(batch.getUserList(), selectBach.getUserList());
       verify(batchRepository, times(1)).findById(batch.getId());

    }

    @Test
    public void getBatchByUserIdTest(){
     Batch batch= batchService.getBatchByUserId("id");
     assertEquals(batch,null);

    }
    
    @Test
    public void getAllBatchesTest(){
        List<Batch>batch= getBatchList();
        when(batchRepository.findAll()).thenReturn(batch);
        List<Batch>batchList=batchService.getAllBatches();
        assertEquals(batch.size(), batchList.size());
        verify(batchRepository, times(1)).findAll();
    }

    @Test
    public void getBatchesByCourseIdTest(){
        List<Batch>batch= getBatchList();
        when(batchRepository.findByBatchCourse_Id(1L)).thenReturn(batch);
        List<Batch>batchList=batchService.getBatchesByCourseId(1L);
        assertEquals(batch.size(), batchList.size());
        verify(batchRepository, times(1)).findByBatchCourse_Id(1L);
    }
    
    private Course getBatchCourse(){
        Course batchcCourse=new Course();
        batchcCourse.setId(1L);
        batchcCourse.setName("java");
        batchcCourse.setFee(100.00);
        batchcCourse.setDescription("asddf");
        batchcCourse.setVideos(new MultipartFile[] {});
        batchcCourse.setResources(new MultipartFile[] {});
        return batchcCourse;
    }

    private List<User>getUserList(){
        User user= new  User();
        List<User>userList=new ArrayList<>();
        user.setId("usr01");
        user.setName("zzs");
        user.setPassword("zzs");
        user.setRole("admin");
        user.setBatchList(getBatchList());
        userList.add(user);
        return userList;

    }
    private Batch getOneBatch(){
        Batch batch=new Batch();
        batch.setId(1L);
        batch.setName("Batch01");
        batch.setStartDate(LocalDate.of(2022, 10, 10));
        batch.setEndDate(LocalDate.of(2022, 10, 10));
        batch.setBatchCourse(getBatchCourse());
        batch.setUserList(getUserList());
        return batch;   
    }   
    private List<Batch>getBatchList(){
            Batch batch=new Batch();
            List<Batch>batchList=new ArrayList<>();
            batch.setId(1L);
            batch.setName("Batch01");
            batch.setStartDate(LocalDate.of(2022, 10, 10));
            batch.setEndDate(LocalDate.of(2022, 10, 10));
            batch.setBatchCourse(getBatchCourse());
            Batch batch1=new Batch();
            batch1.setId(1L);
            batch1.setName("Batch01");
            batch1.setStartDate(LocalDate.of(2022, 10, 10));
            batch1.setEndDate(LocalDate.of(2022, 10, 10));
            batch1.setBatchCourse(getBatchCourse());
            batchList.add(batch);
            batchList.add(batch1);
            return batchList;

    }

    
}

