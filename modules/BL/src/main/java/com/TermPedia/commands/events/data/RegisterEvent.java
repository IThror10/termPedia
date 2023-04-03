package com.TermPedia.commands.events.data;

import com.TermPedia.commands.events.DataEvent;
import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.events.EventType;
import com.TermPedia.commands.CommandVisitor;
import org.jetbrains.annotations.NotNull;
import com.TermPedia.dto.JsonBuilder;

public class RegisterEvent extends DataEvent {
    public RegisterEvent(
            @NotNull String login,
            @NotNull String password,
            @NotNull String email
    ) throws FormatException {
        super(null, EventType.registration);
        if (login == null || password == null || email == null)
            throw new FormatException("Login/Password/Email not provided");
        if (login.length() < 4)
            throw new FormatException("Login is too short");
        else if (password.length() < 8)
            throw new FormatException("Password is too short");
        else {
            this.data = new JsonBuilder(128)
                    .addStr("Login", login)
                    .addStr("Password", password)
                    .addStr("Email", email)
                    .getData();
        }
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitRegisterEvent(this);
    }
}
