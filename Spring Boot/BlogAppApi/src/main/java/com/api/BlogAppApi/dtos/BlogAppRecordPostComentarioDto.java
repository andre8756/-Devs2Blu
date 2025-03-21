package com.api.BlogAppApi.dtos;

import jakarta.validation.constraints.NotBlank;

public record BlogAppRecordPostComentarioDto(@NotBlank String comentario) {

}
