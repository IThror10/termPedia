package com.TermPedia.commands.result;

import com.TermPedia.dto.users.UserPrivateData;
import lombok.Data;

@Data
public class AuthCommandResult {
    private final UserPrivateData data;
}
