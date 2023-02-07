package com.TermPedia.dto.users;

import java.util.Collection;
import java.util.List;

public record UserPublicData(Integer userID, String login, String email,
                             List<String> phones, List<String> posts, Collection<String> roles) {
}
