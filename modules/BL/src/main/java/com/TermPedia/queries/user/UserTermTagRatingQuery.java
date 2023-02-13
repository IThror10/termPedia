package com.TermPedia.queries.user;

import com.TermPedia.dto.exceptions.ActionsException;
import com.TermPedia.dto.tags.Tag;
import com.TermPedia.queries.IQuery;
import com.TermPedia.queries.QueryVisitor;
import com.TermPedia.queries.results.user.UserRatingResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserTermTagRatingQuery implements IQuery {
    private UserRatingResult result;
    private final Integer userId;
    private final Integer termId;
    private final String tag;

    @Override
    public void acceptVisitor(QueryVisitor visitor) throws ActionsException {
        visitor.visitUserTermTagRatingQuery(this);
    }
}
