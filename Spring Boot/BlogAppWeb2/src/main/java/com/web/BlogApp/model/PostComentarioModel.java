package com.web.BlogApp.model;

import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name="TB_POSTCOMENTARIO")
public class PostComentarioModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private LocalDate data;

    @Lob
    @Column(columnDefinition="text")
    private String comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_model_id") // Define o nome da coluna de chave estrangeira
    @JsonIgnore // Evita loop infinito no JSON
    private PostModel postModel;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public PostModel getPostModel() {
        return postModel;
    }

    public void setPostModel(PostModel postModel) {
        this.postModel = postModel;
    }

    @Override
    public String toString() {
        return "PostComentarioModel [id=" + id + ", data=" + data + ", comentario=" + comentario + ", postModel="
                + postModel + "]";
    }

}