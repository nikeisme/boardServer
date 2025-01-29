package com.board.boardserver.servcie.impl;

import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.UserDTO;
import com.board.boardserver.mapper.PostMapper;
import com.board.boardserver.mapper.UserProfileMapper;
import com.board.boardserver.servcie.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Log4j2
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper; // 필드 주입

    @Autowired
    private UserProfileMapper userProfileMapper; // 게시글 작성자 정보 위해

    @Override
    public void register(String id, PostDTO postDTO) {

        UserDTO memberInfo = userProfileMapper.getUserProfile(id); // 정상계정여부조회
        postDTO.setUserId(memberInfo.getUserId());

        System.out.println("****memberInfo***");
        System.out.println(memberInfo);

        postDTO.setCreateTime(new Date());

        if(memberInfo != null){
            postMapper.register(postDTO);
        } else {
            log.error("register ERROR {}", postDTO);
            throw new RuntimeException(" * ERROR * 게시글 등록 메서드를 다시 확인해주세요." + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(String accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyPosts(accountId);
        return postDTOList;
    }

    @Override
    public void updatePosts(PostDTO postDTO) {
        System.out.println("*****getId*****");
        System.out.println(postDTO.getId());
        System.out.println(postDTO.getUserId());
        if (postDTO !=null && postDTO.getId() !=0) {
            postMapper.updatePosts(postDTO);

            System.out.println("*****postDTO*****");
            System.out.println(postDTO);

        } else {
            log.error("update ERROR {}", postDTO);
            throw new RuntimeException("* ERROR * 게시글 수정 메서드를 다시 확인해주세요." + postDTO);
        }
    }

    @Override
    public void deletePosts(String userId, int postId) {
        if (userId != null && postId !=0) {
            postMapper.deletePosts(postId);
        } else {
            log.error("delete ERROR {}", postId);
            throw new RuntimeException("* ERROR * 게시글 삭제 메서드를 다시 확인해주세요." + postId);
        }
    }
}
