package com.web.BlogApp.service;

import com.web.BlogApp.model.PostComentarioModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BloggAppPostServiceComentarios {
    //Trata comentários dos posts
    List<PostComentarioModel> findAll();
    Optional<PostComentarioModel> findById(UUID id);
    PostComentarioModel save(PostComentarioModel postComentarioModel);
    void delete(PostComentarioModel postComentarioModel);
}
