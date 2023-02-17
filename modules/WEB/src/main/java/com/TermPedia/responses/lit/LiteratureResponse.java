package com.TermPedia.responses.lit;

import com.TermPedia.dto.literature.Literature;
import lombok.Data;

import java.util.List;

@Data
public class LiteratureResponse {
    public final List<Literature> lit;
}
