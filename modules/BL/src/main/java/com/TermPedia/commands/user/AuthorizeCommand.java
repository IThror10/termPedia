package com.TermPedia.commands.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.BaseCommand;
import com.TermPedia.commands.result.AuthCommandResult;
import com.TermPedia.commands.CommandVisitor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

@Getter
@Setter
public class AuthorizeCommand extends BaseCommand {
    private AuthCommandResult result;

    public AuthorizeCommand(@NotNull String login, @NotNull String password) throws ActionsException {
        super(null);
        if (login == null || password == null)
            throw new FormatException("Login/Password not provided");
        else {
            this.result = null;
            this.data = new JsonBuilder(128)
                    .addStr("Login", login)
                    .addStr("Password", password)
                    .getData();
        }

    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitAuthorizeCommand(this);
    }
}