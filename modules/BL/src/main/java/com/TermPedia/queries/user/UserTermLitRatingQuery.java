package com.TermPedia.queries.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.results.user.UserRatingResult;
import com.TermPedia.queries.QueryVisitor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class UserTermLitRatingQuery implements IQuery {
    private UserRatingResult result;
    private final Integer userID;
    private final Integer termID;
    private final Integer litID;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitUserTermLitRatingQuery(this);
    }
}
