package com.example.hexagon.infrastructure.adapter.in.web;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTodoRequest {
    @NotBlank(message = "Title is mandatory")
    private String title;
}
