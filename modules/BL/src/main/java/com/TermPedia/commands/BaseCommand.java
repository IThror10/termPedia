package com.TermPedia.commands;

import com.TermPedia.dto.exceptions.ActionsException;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

@Getter
public abstract class BaseCommand {
    protected String data;
    private final LocalDateTime dateTime;
    private final Integer uid;
    protected BaseCommand(Integer uid) {
        this.uid = uid;
        this.dateTime = LocalDateTime.now();
    }
    public abstract void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException;
}
