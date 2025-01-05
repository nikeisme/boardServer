package com.board.boardserver.servcie.impl;

import com.board.boardserver.dto.CategoryDTO;
import com.board.boardserver.mapper.CategoryMapper;
import com.board.boardserver.servcie.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categorytMapper;

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {

        if(accountId != null) {
            categorytMapper.register(categoryDTO);
        } else {
            log.error("register ERROR! {}", categoryDTO);
            throw new RuntimeException("register ERROR! 게시글 카테고리 등록 메서드를 확인해주세요" + categoryDTO);
        }

    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        if(categoryDTO!= null) {
            categorytMapper.updateCategory(categoryDTO);
        } else {
            log.error("update ERROR! {}", categoryDTO);
            throw new RuntimeException("update ERROR! 게시글 카테고리 수정 메서드를 확인해주세요" + categoryDTO);
        }
    }

    @Override
    public void delete(int categoryId) {
        if ( categoryId != 0) {
            categorytMapper.deleteCategory(categoryId);
        } else {
            log.error("delete ERROR! {}", categoryId);
            throw new RuntimeException("delete ERROR! 게시글 카테고리 삭제 메서드를 확인해주세요" + categoryId);
        }
    }
}
