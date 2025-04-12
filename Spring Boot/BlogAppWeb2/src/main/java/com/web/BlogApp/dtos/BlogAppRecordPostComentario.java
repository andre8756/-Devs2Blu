package com.web.BlogApp.dtos;

import jakarta.validation.constraints.NotBlank;

public record BlogAppRecordPostComentario(@NotBlank String comentario) {
}
