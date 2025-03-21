package com.api.BlogAppApi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.BlogAppApi.model.PostComentarioModel;

public interface PostComentarioRepository extends JpaRepository<PostComentarioModel, UUID>{

}
