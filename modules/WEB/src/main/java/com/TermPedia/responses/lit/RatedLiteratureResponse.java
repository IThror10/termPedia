package com.TermPedia.responses.lit;

import com.TermPedia.dto.literature.RatedLiterature;
import lombok.Data;

import java.util.List;

@Data
public class RatedLiteratureResponse {
    public final List<RatedLiterature> lit;
}
