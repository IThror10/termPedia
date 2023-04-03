package com.TermPedia.responses.lit;

import com.TermPedia.dto.literature.TagLiterature;
import lombok.Data;

import java.util.List;

@Data
public class TagLiteratureResponse {
    public final List<TagLiterature> lit;
}
