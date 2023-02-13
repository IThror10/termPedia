package com.TermPedia.commands.user;

import com.TermPedia.commands.result.EventResult;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.BaseCommand;
import com.TermPedia.commands.CommandVisitor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class LogoutCommand extends BaseCommand {
    private EventResult status;

    public LogoutCommand(Integer userId) {
        super(userId);
    }
    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitLogoutCommand(this);
    }
}
