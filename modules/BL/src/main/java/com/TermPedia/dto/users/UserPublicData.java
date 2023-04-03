package com.TermPedia.dto.users;

import lombok.Data;
import java.util.List;

@Data
public class UserPublicData {
    private final String login;
    private final String email;
    private final List<String> phones;
    private final List<String> posts;
}