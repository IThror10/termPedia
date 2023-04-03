package com.TermPedia.commands.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.commands.BaseCommand;
import com.TermPedia.commands.result.ValidateCommandResult;
import com.TermPedia.commands.CommandVisitor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ValidateCommand extends BaseCommand {
    private ValidateCommandResult result;
    public final String secret;
    public final String login;

    public ValidateCommand(String login, String secret) {
        super(null);
        this.secret = secret;
        this.login = login;
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitValidateCommand(this);
    }
}
