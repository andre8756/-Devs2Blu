package com.api.BlogAppApi.service;

import com.api.BlogAppApi.dtos.BlogAppRecordPostComentarioDto;
import com.api.BlogAppApi.model.BlogAppPostModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.api.BlogAppApi.model.PostComentarioModel;
import com.api.BlogAppApi.repository.PostComentarioRepository;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BlogAppServiceImplComentarios implements BlogAppPostServiceComentarios {
	
	@Autowired
	PostComentarioRepository postComentarioRepository;

	@Autowired
	BlogAppServiceImpl postService;

	@Override
	public List<PostComentarioModel> findAll() {
		return postComentarioRepository.findAll();
	}

	@Override
	public Optional<PostComentarioModel> findById(UUID id) {
		return postComentarioRepository.findById(id);
	}

	@Override
	public PostComentarioModel save(PostComentarioModel postComentarioModel) {
		return postComentarioRepository.save(postComentarioModel);
	}

	public PostComentarioModel saveComentario(UUID postId, BlogAppRecordPostComentarioDto comentarioDto) {
		BlogAppPostModel post = postService.findById(postId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post não encontrado!"));

		PostComentarioModel comentario = new PostComentarioModel();
		comentario.setComentario(comentarioDto.comentario());
		comentario.setData(LocalDate.now(ZoneId.of("UTC")));
		comentario.setPostModel(post);

		//Adicionando o comentário à lista de postComentario
		post.getPostComentario().add(comentario);

		return postComentarioRepository.save(comentario);
	}

	@Override
	public void delete(PostComentarioModel postComentarioModel) {
		postComentarioRepository.delete(postComentarioModel);
	}
}
