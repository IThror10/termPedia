package com.TermPedia.queries.results.user;

import com.TermPedia.dto.users.UserRating;
import lombok.Data;

import java.util.List;

@Data
public class UserRatingResult {
    private final List<UserRating> result;
}
