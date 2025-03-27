package com.board.boardserver.controller;

import com.board.boardserver.aop.LoginCheck;
import com.board.boardserver.dto.CommentDTO;
import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.TagDTO;
import com.board.boardserver.dto.UserDTO;
import com.board.boardserver.dto.response.CommonResponse;
import com.board.boardserver.servcie.impl.PostServiceImpl;
import com.board.boardserver.servcie.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping ("/posts")
@Log4j2
public class PostController {

    private final UserServiceImpl userService;
    private final PostServiceImpl postService;

    public PostController(UserServiceImpl userService, PostServiceImpl postsService){
        this.userService = userService;
        this.postService = postsService;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId, @RequestBody PostDTO postDTO) {
        postService.register(accountId,postDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","registerPost",postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId){
        UserDTO memberInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyPosts(memberInfo.getUserId());
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK,"SUCCESS","myPostInfo",postDTOList);
        return ResponseEntity.ok(commonResponse);

    }
    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePosts(String accountId,
                                                                    @PathVariable(name="postId") int postId,
                                                                    @RequestBody PostRequest postRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updatePosts(postDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK,"SUCCESS","updatePosts",postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{id}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> deletePosts (String userId,
                                                                     @PathVariable(name = "id") int id
                                                                     ) {
        UserDTO memberInfo = userService.getUserInfo(userId);
        postService.deletePosts(memberInfo.getUserId(),id);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePosts","게시글삭제완료");
        return ResponseEntity.ok(commonResponse);
    }

    // -- comment

    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> registerPostComment(String accountId, @RequestBody CommentDTO commentDTO) {
        postService.registerComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> updatePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId,
                                                                        @RequestBody CommentDTO commentDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.updateComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deletePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId,
                                                                        @RequestBody CommentDTO commentDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.deletePostComment(memberInfo.getUserId(), commentId);
        System.out.println("*********************************");
        System.out.println("DeleteController 실행");
        System.out.println("commentId" + commentId);
        System.out.println("*********************************");
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }


    // -- Tag

    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPostTag(String accountId, @RequestBody TagDTO tagDTO) {
        postService.registerTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse(HttpStatus.OK,"SUCCESS","registerPostTag",tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("tags/{tagId}")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>>updatePostTag(String accountId,
                                                                       @PathVariable(name = "tagId") int tagId,
                                                                       @RequestBody TagDTO tagDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if (memberInfo != null)
           // postService.deletePostTag(memberInfo.getUserId(), tagId);
            postService.updateTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK,"SUCCESS","deletePostTag",tagDTO);
        return ResponseEntity.ok(commonResponse);
    }



    // -- Response객체

    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTO;
    }

    // -- Request 객체
    @Getter
    @Setter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private String userId;
        private int fileId;
        private Date updateTime;
    }

}
