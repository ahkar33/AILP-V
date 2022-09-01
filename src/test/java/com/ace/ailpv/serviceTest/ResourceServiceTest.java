package com.ace.ailpv.serviceTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.Resource;
import com.ace.ailpv.repository.ResourceRepository;
import com.ace.ailpv.service.ResourceService;

@SpringBootTest
public class ResourceServiceTest {
    @Mock
    ResourceRepository resourceRepository;
    @InjectMocks
    ResourceService resourceService;

    @Test
    public void isExistByResourceNameTest(){
        Resource resource=getOneResource();
        when(resourceRepository.existsByNameAndResourceCourse_id(resource.getName(),resource.getId())).thenReturn(true);
        resourceService.isExistByResourceNameAndCourseId(resource.getName(),resource.getId());
        verify(resourceRepository,times(1)).existsByNameAndResourceCourse_id(resource.getName(), resource.getId());
    }

    @Test
    public void getAllResourcesTest(){
        List<Resource>resource=getResourceList();
        when(resourceRepository.findAll()).thenReturn(resource);
        List<Resource>resourceList=resourceService.getAllResources();
        assertEquals(resource.size(), resourceList.size());
        verify(resourceRepository, times(1)).findAll();
    }

    @Test
    public void getResourceByCourseIdTest(){
        List<Resource>resource=getResourceList();
        when(resourceRepository.findByResourceCourse_id(1L)).thenReturn(resource);
        List<Resource>resourceList=resourceService.getResourceByCourseId(1L);
        assertEquals(resource.size(), resourceList.size());
        verify(resourceRepository,times(1)).findByResourceCourse_id(1L);
    }

    @Test
    public void getResourceByIdTest(){
        Resource resource=getOneResource();
        when(resourceRepository.findById(resource.getId())).thenReturn(Optional.of(resource));
        Resource selectResource=resourceService.getResourceById(resource.getId());
        assertEquals(resource.getName(), selectResource.getName());
        assertEquals(resource.getResourceCourse(), selectResource.getResourceCourse());
        verify(resourceRepository,times(1)).findById(resource.getId());
    }

    @Test
    public void addResourceTest(){
        Resource resource=getOneResource();
        resourceService.addResource(resource);
        verify(resourceRepository, times(1)).save(resource);

    }

    @Test
    public void deleteResourceByIdTest(){
        resourceService.deleteResourceById(1L);
        verify(resourceRepository, times(1)).deleteById(1L);

    }


    private Resource getOneResource(){
        Resource resource=new Resource();
        resource.setId(1L);
        resource.setName("ppt");
        resource.setResourceCourse(new Course());
        return resource;
    }

    private List<Resource>getResourceList(){
        List<Resource>resourseList=new ArrayList<>();
        Resource resource=new Resource();
        resource.setId(1L);
        resource.setName("ppt");
        resource.setResourceCourse(new Course());
        resourseList.add(resource);
        return resourseList;
    }
}
