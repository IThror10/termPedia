package com.TermPedia.commands.result;

import com.TermPedia.dto.users.UserValidData;
import lombok.Data;

@Data
public class ValidateCommandResult {
    private final UserValidData data;
}
