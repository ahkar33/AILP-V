package com.ace.ailpv.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ace.ailpv.entity.Batch;
import com.ace.ailpv.entity.Comment;
import com.ace.ailpv.entity.Reply;
import com.ace.ailpv.entity.User;
import com.ace.ailpv.entity.Video;
import com.ace.ailpv.service.BatchService;
import com.ace.ailpv.service.CommentService;
import com.ace.ailpv.service.ReplyService;
import com.ace.ailpv.service.UserService;
import com.ace.ailpv.service.VideoService;

@RestController
@RequestMapping("/api/comment")
public class CommentApi {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ReplyService replyService;

    @PostMapping("/sendComment")
    public void sendComment(@RequestBody Comment comment) {
        User user = userService.getUserById(comment.getUserId());
        Batch batch = batchService.getBatchById(comment.getBatchId());
        Video video = videoService.getVideoById(comment.getVideoId());
        Comment reqComment = new Comment();
        reqComment.setCommentText(comment.getCommentText());
        reqComment.setCommentBatch(batch);
        reqComment.setCommentUser(user);
        reqComment.setCommentVideo(video);
        reqComment.setDateTime(comment.getDateTime());
        commentService.addComment(reqComment);
    }

    @GetMapping("/getCommentListByBatchIdAndVideoId/{batchId}/{videoId}")
    public List<Comment> getCommentList(@PathVariable("batchId") Long batchId, @PathVariable("videoId") Long videoId) {
        return commentService.getCommentListByBatchIdAndVideoId(batchId, videoId);
    }

    @PostMapping("/sendReply")
    public void sendReply(@RequestBody Reply reply) {
        User user = userService.getUserById(reply.getReplyUserId());
        Comment comment = commentService.getCommentById(reply.getReplyCommentId());
        Reply reqReply = new Reply();
        reqReply.setReplyText(reply.getReplyText());
        reqReply.setReplyComment(comment);
        reqReply.setReplyUser(user);
        reqReply.setDateTime(reply.getDateTime());
        replyService.addReply(reqReply);
    }

    @GetMapping("/deleteCommentById/{commentId}")
    public void deleteCommentById(@PathVariable("commentId") Long commentId) {
        commentService.deleteCommentById(commentId); 
    }

    @GetMapping("/deleteReplyById/{replyId}")
    public void deleteReplytById(@PathVariable("replyId") Long replyId) {
        replyService.deleteReplyById(replyId);
    }

}
