package com.board.boardserver.mapper;

import com.board.boardserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    public int register (PostDTO postDTO);

    public List<PostDTO> selectMyPosts(String userId);

    public void updatePosts(PostDTO postDTO);
    public void deletePosts(int id);

}
