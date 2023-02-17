package com.TermPedia.dto.users;

import lombok.Data;

@Data
public class UserRating {
    private final Integer termId;
    private final Object identifier;
    private final Integer userRating;
}
