package com.web.BlogApp.repository;

import java.util.UUID;

import com.web.BlogApp.model.PostComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostComentarioRepository extends JpaRepository<PostComentarioModel, UUID>{

}