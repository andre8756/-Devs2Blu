package com.api.BlogAppApi.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="TB_POST")
public class BlogAppPostModel implements Serializable {
	private static final long serialVersionUID  = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private UUID id;
	
	@Column(nullable = false, length = 70)
	private String autor;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Column(nullable = false, length = 70)
	private String titulo;
	
	@Lob // para o db aceitar texto grande e ter uma boa performance
	@Column(columnDefinition="text")
	private String texto;


	@OneToMany(mappedBy = "postModel", fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Remove a tabela de junção
	private List<PostComentarioModel> postComentario = new ArrayList<>();
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	

	public List<PostComentarioModel> getPostComentario() {
		return postComentario;
	}

	public void setPostComentario(List<PostComentarioModel> postComentario) {
		this.postComentario = postComentario;
	}

	@Override
	public String toString() {
		return "BlogAppPostModel [id=" + id + ", autor=" + autor + ", data=" + data + ", titulo=" + titulo + ", texto="
				+ texto + "]";
	}

	
	
	
	
}
