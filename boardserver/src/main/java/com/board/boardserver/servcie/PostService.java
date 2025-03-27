package com.board.boardserver.servcie;

import com.board.boardserver.dto.CommentDTO;
import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO); // 게시글 등록
    List<PostDTO> getMyPosts(String accountId); // 내가 작성한 게시글 조회
    void updatePosts(PostDTO postDTO);  // 게시글 수정
    void deletePosts(String userId, int PostId); // 게시글 삭제

    // -- commnent

    void registerComment(CommentDTO commentDTO);

    void updateComment (CommentDTO commentDTO);

    void deletePostComment(String userId, int commentId);

    // -- Tag

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deletePostTag(String userId,int tagId);

}
