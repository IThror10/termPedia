package com.TermPedia.queries.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.results.user.UserRatingResult;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@Getter
@Setter
public class UserTermTagRatingQuery implements IQuery {
    private UserRatingResult result;
    private final Integer userId;
    private final Integer termId;
    private final String tag;

    public UserTermTagRatingQuery(
            @Nullable Integer userId,
            @NotNull Integer termId,
            @NotNull String tag
    ) throws FormatException {
        if (tag == null)
            throw new FormatException("Tag is not provided");
        else if (termId == null)
            throw new FormatException("TermId is Not provided");
        else {
            this.userId = userId;
            this.termId = termId;
            this.tag = tag;
        }
    }

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitUserTermTagRatingQuery(this);
    }
}
