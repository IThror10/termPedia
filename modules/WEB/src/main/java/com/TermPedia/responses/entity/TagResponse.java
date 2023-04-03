package com.TermPedia.responses.entity;

import com.TermPedia.dto.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

import java.util.List;

public record TagResponse(
    @NotBlank List<Tag> tags
) {}
