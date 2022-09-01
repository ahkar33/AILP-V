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

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Comment;
import com.ace.ailpv.entity.Course;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.repository.CommentRepository;
import com.ace.ailpv.service.CommentService;

@SpringBootTest
public class CommentServiceTest {
    @Mock
    CommentRepository commentRepository;
    @InjectMocks
    CommentService commentService;

    @Test
    public void addCommentTest(){
        Comment comment=getComment();
        commentService.addComment(comment);
        verify(commentRepository,times(1)).save(comment);
    }

    @Test
    public void deleteCommentById(){
        Comment comment=getComment();
        commentService.deleteCommentById(comment.getId());
        verify(commentRepository,times(1)).deleteById(comment.getId());
    }

    @Test
    public void getCommentListByBatchIdAndVideoIdTest(){
        List<Comment>commentList=getCommentList();
        Batch batch=getBatch();
        Video video=getVideo();
        when(commentRepository.findByCommentBatch_IdAndCommentVideoIdOrderByIdDesc(batch.getId(), video.getId())).thenReturn(commentList);
        List<Comment>commentListActual=commentService.getCommentListByBatchIdAndVideoId(batch.getId(), video.getId());
        assertEquals(commentList.size(), commentListActual.size());
        verify(commentRepository,times(1)).findByCommentBatch_IdAndCommentVideoIdOrderByIdDesc(batch.getId(), video.getId());

    }

    @Test
    public void getCommentByIdTest(){
        Comment comment=getComment();
        when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        Comment commentActual=commentService.getCommentById(comment.getId());
        assertEquals(comment.getCommentText(), commentActual.getCommentText());
        assertEquals(comment.getCommentVideo(), commentActual.getCommentVideo());
        assertEquals(comment.getCommentUser(), commentActual.getCommentUser());
        assertEquals(comment.getCommentBatch(), commentActual.getCommentBatch());
        verify(commentRepository,times(1)).findById(comment.getId());
    }

    private Comment getComment(){
        Comment comment=new Comment();
        comment.setId(1L);
        comment.setCommentText("commentText");
        comment.setCommentVideo(new Video());
        comment.setCommentUser(new User());
        comment.setCommentBatch(new Batch());
        comment.setDateTime("10-10-2011");
        return comment;
    }

    private List<Comment>getCommentList(){
        List<Comment>commentList=new ArrayList<>();
        Comment comment=new Comment();
        comment.setId(1L);
        comment.setCommentText("commentText");
        comment.setCommentVideo(new Video());
        comment.setCommentUser(new User());
        comment.setCommentBatch(new Batch());
        comment.setDateTime("10-10-2011");
        commentList.add(comment);
        return commentList;
    }

    private Batch getBatch(){
        Batch batch=new Batch();
        batch.setId(1L);
        batch.setName("Batch01");
        batch.setStartDate(LocalDate.of(2022, 10, 10));
        batch.setEndDate(LocalDate.of(2022, 10, 10));
        batch.setBatchCourse(new Course());
        batch.setUserList(new ArrayList<>());
        return batch;   
    }  

    private Video getVideo(){
        Course videoCourse = new Course();
        videoCourse.setId(3L);

        Video video = new Video();
        video.setId(1L);
        video.setName("blah blah");
        video.setVideoCourse(videoCourse);
         
        return video;
    }

    
}
