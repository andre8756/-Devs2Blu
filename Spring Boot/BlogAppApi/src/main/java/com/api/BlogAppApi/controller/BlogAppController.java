package com.api.BlogAppApi.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.api.BlogAppApi.service.BlogAppPostServiceComentarios;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.BlogAppApi.dtos.BlogAppRecordDto;
import com.api.BlogAppApi.dtos.BlogAppRecordPostComentarioDto;
import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.model.PostComentarioModel;
import com.api.BlogAppApi.service.BlogAppPostService;
import com.api.BlogAppApi.service.BlogAppServiceImplComentarios;

import jakarta.validation.Valid;

@RestController
public class BlogAppController {
    
    private BlogAppPostService blogAppPostService;

	@Autowired
    private BlogAppServiceImplComentarios blogAppServicePostComentarios;

    // Setter Injection
    @Autowired
    public void setBlogAppPostService(BlogAppPostService blogAppPostService) {
        this.blogAppPostService = blogAppPostService;
    }

    // Lista Todos os Posts
    @GetMapping(value = "/posts")
    public ResponseEntity<List<BlogAppPostModel>> getPosts() {
        List<BlogAppPostModel> posts = blogAppPostService.findAll();
        
        if (posts.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(posts);
        }
        return ResponseEntity.status(HttpStatus.OK).body(posts);
    }
    
    @GetMapping(value = "/posts/{id}")
    public ResponseEntity<Object> getPostDetails(@PathVariable("id") UUID id){
    	Optional<BlogAppPostModel> blogAppModelOptional = blogAppPostService.findById(id);
    	
    	if (blogAppModelOptional.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
    	}
    		return ResponseEntity.status(HttpStatus.OK).body(blogAppModelOptional.get());
    }
    
    @PostMapping(value = "/newpost")
    public ResponseEntity<Object> savePost(@RequestBody @Valid BlogAppRecordDto blogAppRecordDto){
    	
    	var postModel = new BlogAppPostModel();
    	BeanUtils.copyProperties(blogAppRecordDto, postModel);
    	postModel.setData(LocalDate.now(ZoneId.of("UTC")));
    	return ResponseEntity.status(HttpStatus.CREATED).body(blogAppPostService.save(postModel));
    }
    
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "id") UUID id) {
    	Optional<BlogAppPostModel> blogAppModelOptional = blogAppPostService.findById(id);
    	if(!blogAppModelOptional.isPresent()) {
    		return ResponseEntity.status(HttpStatus.OK).body("Blog deleted successfuly.");
    	}
    	blogAppPostService.delete(blogAppModelOptional.get());
    	return ResponseEntity.status(HttpStatus.OK).body("Blog deleted sucessfully.");
    }
    
    @PutMapping(value = "/posts/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable("id") UUID id, @RequestBody @Valid BlogAppRecordDto blogAppRecordDto){
    	Optional<BlogAppPostModel> blogAppModelOptional = blogAppPostService.findById(id);
    	
    	if(blogAppModelOptional.isEmpty()) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blog not found");
    	}
    	
    	BlogAppPostModel postModel = blogAppModelOptional.get();
    	BeanUtils.copyProperties(blogAppRecordDto, postModel);
    	postModel.setData(LocalDate.now(ZoneId.of("UTC")));
    	BlogAppPostModel updatedPost = blogAppPostService.save(postModel);
    	return ResponseEntity.status(HttpStatus.OK).body(updatedPost);
    	
    }

	//Comentários:

    @GetMapping(value="/posts/comentario/{id}")
    public ResponseEntity<Object> getComentarioDetails(@PathVariable("id") UUID id){
        Optional<PostComentarioModel> comentarioOptional = blogAppServicePostComentarios.findById(id);

        if(comentarioOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(comentarioOptional.get());
    }

	@PostMapping(value = "/posts/{id}/comentario")
	public ResponseEntity<Object> criarComentario(
			@PathVariable("id") UUID id,
			@RequestBody @Valid BlogAppRecordPostComentarioDto comentarioDto
	) {
		PostComentarioModel comentarioSalvo = blogAppServicePostComentarios.saveComentario(id, comentarioDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(comentarioSalvo);
	}

	@DeleteMapping("/posts/comentario/{id}")
	public ResponseEntity<Object> deteComentario(
			@PathVariable(value = "id") UUID id
	){
		Optional<PostComentarioModel> comentarioOptional = blogAppServicePostComentarios.findById(id);
		if(!comentarioOptional.isPresent()){
			return ResponseEntity.status(HttpStatus.OK).body("Comentario não encontrado");
		}

		blogAppServicePostComentarios.delete(comentarioOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Comentario deletado com sucesso");
	}

	@PutMapping(value = "/posts/comentario/{id}")
	public ResponseEntity<Object> updateComentario(
			@PathVariable("id") UUID id,
			@RequestBody @Valid BlogAppRecordPostComentarioDto comentarioDto
	){
		Optional<PostComentarioModel> comentarioOptional = blogAppServicePostComentarios.findById(id);

		if(comentarioOptional.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comentario not found");
		}

		PostComentarioModel comentarioModel = comentarioOptional.get();
		BeanUtils.copyProperties(comentarioDto, comentarioModel);
		comentarioModel.setData(LocalDate.now(ZoneId.of("UTC")));
		PostComentarioModel updateComentario = blogAppServicePostComentarios.save(comentarioModel);
		return ResponseEntity.status(HttpStatus.OK).body(updateComentario);
	}



}
