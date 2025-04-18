package com.api.BlogAppApi.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.repository.BlogAppPostRepository;

@Component
public class DataUtils {
	
	@Autowired
	BlogAppPostRepository blogRepository;
	
	//@PostConstruct
	public void savePost() {
		List<BlogAppPostModel> postList = new ArrayList<>();
		
		BlogAppPostModel post1 = new BlogAppPostModel();
		post1.setAutor("Pokemom");
		post1.setData(LocalDate.now());
		post1.setTexto("Lorem Ipsum is simply dummy text of the printing and typesetting industry."
        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		post1.setTitulo("Docker");
		
		BlogAppPostModel post2 = new BlogAppPostModel();
		post2.setAutor("Bem10");
		post2.setData(LocalDate.now());
		post2.setTexto("Bem 10 is simply dummy text of the printing and typesetting industry."
        		+ " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, "
        		+ "when an unknown printer took a galley of type and scrambled it to make a type specimen book. "
        		+ "It has survived not only five centuries, but also the leap into electronic typesetting, "
        		+ "remaining essentially unchanged. It was popularised in the 1960s with the release of"
        		+ " Letraset sheets containing Lorem Ipsum passages, and more recently with desktop "
        		+ "publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
		post2.setTitulo("Docker");
		
		postList.add(post1);
		postList.add(post2);
		
		for(BlogAppPostModel post: postList) {
			BlogAppPostModel postSaved = blogRepository.save(post);
			System.out.println(postSaved.getId());
			
		}
	}
}
