package com.api.BlogAppApi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.BlogAppApi.model.BlogAppPostModel;
import com.api.BlogAppApi.repository.BlogAppPostRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class BlogAppServiceImpl implements BlogAppPostService{
	
	@Autowired
	BlogAppPostRepository blogAppPostRepository;

	@Override
	public List<BlogAppPostModel> findAll() {
		// TODO Auto-generated method stub
		return blogAppPostRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<BlogAppPostModel> findById(UUID id) {
		return blogAppPostRepository.findById(id);
	}

	@Override
	@Transactional // garante que se ocorrer algo de erra no processo, a mesma será desfeita
	public BlogAppPostModel save(BlogAppPostModel post) {
		// TODO Auto-generated method stub
		return blogAppPostRepository.save(post);
	}

	@Override
	@Transactional // garante que se ocorrer algo de erra no processo, a mesma será desfeita
	public void delete(BlogAppPostModel post) {
		// TODO Auto-generated method stub
		blogAppPostRepository.delete(post);
	}
	
	
}
