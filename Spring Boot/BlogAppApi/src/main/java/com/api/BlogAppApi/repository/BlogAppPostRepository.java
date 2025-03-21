package com.api.BlogAppApi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.BlogAppApi.model.BlogAppPostModel;

public interface BlogAppPostRepository extends JpaRepository<BlogAppPostModel, UUID> {
  //todos os métodos estão na JPA, que estão sendo herdados pela extensão
}
