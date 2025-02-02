package com.board.boardserver.dto.request;

import com.board.boardserver.dto.CategoryDTO;
import com.board.boardserver.dto.PostDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {
    private int id;
    private String name;
    private String contents;
    private int views;
    private int categoryId;
    private String userId;
    private CategoryDTO.SortStatus sortStatus;
}