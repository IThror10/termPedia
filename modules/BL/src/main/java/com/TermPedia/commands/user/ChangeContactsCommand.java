package com.TermPedia.commands.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.commands.BaseCommand;
import com.TermPedia.commands.result.ContactDataEventResult;
import com.TermPedia.commands.CommandVisitor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
public class ChangeContactsCommand extends BaseCommand {
    private ContactDataEventResult result;
    public final String column;
    public final String operation;
    public final String value;

    public ChangeContactsCommand(
            @NotNull Integer userId, @NotNull String column,
            @NotNull String operation, @NotNull String value
    ) throws FormatException {
        super(userId);

        if (value == null || column == null || operation == null)
            throw new FormatException("Wrong input data provided");
        else if (value.length() < 3)
            throw new FormatException("Adding value info is too short");
        else {
            this.column = column;
            this.operation = operation;
            this.value = value;
        }
    }

    @Override
    public void acceptVisitor(@NotNull CommandVisitor visitor) throws ActionsException {
        visitor.visitChangeUserContactsCommand(this);
    }
}

