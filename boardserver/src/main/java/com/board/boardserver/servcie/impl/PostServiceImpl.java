package com.board.boardserver.servcie.impl;

import com.board.boardserver.dto.CommentDTO;
import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.TagDTO;
import com.board.boardserver.dto.UserDTO;
import com.board.boardserver.mapper.CommentMapper;
import com.board.boardserver.mapper.PostMapper;
import com.board.boardserver.mapper.TagMapper;
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
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void register(String id, PostDTO postDTO) {

        UserDTO memberInfo = userProfileMapper.getUserProfile(id); // 정상계정여부조회
        postDTO.setUserId(memberInfo.getId());

        postDTO.setCreateTime(new Date());

        if(memberInfo != null){
            postMapper.register(postDTO);
            Integer postId = postDTO.getId();
            for (int i=0; i<postDTO.getTagDTOList().size(); i++){
                TagDTO tagDTO = postDTO.getTagDTOList().get(i);
                tagMapper.register(tagDTO);
                Integer tagId = tagDTO.getId();
                tagMapper.createPostTag(tagId,postId);
            }
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

        if (postDTO !=null && postDTO.getId() !=0) {
            postMapper.updatePosts(postDTO);

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

    @Override
    public void registerComment(CommentDTO commentDTO) {
        if(commentDTO.getPostId() !=0){
            commentMapper.register(commentDTO);
        } else {
            log.error("register comment {}", commentDTO);
            throw new RuntimeException("registerComment" +  commentDTO);
        }

    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if (commentDTO != null){
            commentMapper.updateComments(commentDTO);
        } else {
            log.error("update comment {}", commentDTO);
            throw new RuntimeException("updateComment" +  commentDTO);
        }
    }

    @Override
    public void deletePostComment(int userId, int commentId) {
        if (userId != 0 && commentId != 0) {
            commentMapper.deletePostComment(commentId);
        } else {
            log.error("deletePostComment ERROR! {}", commentId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요\n" + "Params : " + commentId);
        }
    }


    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO != null){
            tagMapper.register(tagDTO);
        }else {
            log.error("registerTag error {}" + tagDTO);
            throw new RuntimeException("registerTag" + tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO != null){
            tagMapper.updateTag(tagDTO);
        }else {
            log.error("updateTag error {}" + tagDTO);
            throw new RuntimeException("updateTag" + tagDTO);
        }
    }

    @Override
    public void deletePostTag(String userId, int tagId) {
        if(userId != null && tagId !=0 ){
            tagMapper.deletePostTag(tagId);
        }else
            log.error("deletePostTag error {}" + tagId);
            throw new RuntimeException("deletePostTag" + tagId);
        }
}
