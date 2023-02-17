package com.TermPedia.queries.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.exceptions.FormatException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.QueryVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class UserTermLitRatingQuery implements IQuery {
    private UserRatingResult result;
    private final Integer userId;
    private final Integer termId;
    private final Integer litId;

    public UserTermLitRatingQuery (
            @Nullable Integer userId,
            @NotNull Integer termId,
            @NotNull Integer litID
    ) throws FormatException {
        if (litID == null)
            throw new FormatException("LitId is not provided");
        else if (termId == null)
            throw new FormatException("TermIds Not provided");
        else {
            this.userId = userId;
            this.termId = termId;
            this.litId = litID;
        }
    }
    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitUserTermLitRatingQuery(this);
    }
}
