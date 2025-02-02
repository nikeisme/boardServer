package com.board.boardserver.servcie;

import com.board.boardserver.dto.PostDTO;
import com.board.boardserver.dto.request.PostSearchRequest;
import java.util.List;

public interface PostSearchService {
    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);
}
