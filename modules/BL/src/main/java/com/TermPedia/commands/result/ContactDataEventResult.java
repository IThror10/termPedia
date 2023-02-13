package com.TermPedia.commands.result;

import com.TermPedia.dto.users.UserPublicData;
import lombok.Data;

@Data
public class ContactDataEventResult {
    private final UserPublicData data;
}
