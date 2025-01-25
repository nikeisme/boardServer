package com.board.boardserver.servcie;

import com.board.boardserver.dto.PostDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO); // 게시글 등록
    List<PostDTO> getMyPosts(int accountId); // 내가 작성한 게시글 조회
    void updatePosts(PostDTO postDTO);  // 게시글 수정
    void deletePosts(int userId,int postId); // 게시글 삭제

}
