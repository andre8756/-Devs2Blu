package com.api.BlogAppApi.service;


import com.api.BlogAppApi.model.PostComentarioModel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BlogAppPostServiceComentarios {
	
	//Trata comentários dos posts
	List<PostComentarioModel> findAll();
	Optional<PostComentarioModel> findById(UUID id);
	PostComentarioModel save(PostComentarioModel postComentarioModel);
	void delete(PostComentarioModel postComentarioModel);
}
