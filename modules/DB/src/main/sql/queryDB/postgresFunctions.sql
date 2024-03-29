-- Get User's Term Tag Rating
Create or Replace Function data.term_tag_rating(in_uid int, in_tid int, in_tag varchar)
    Returns Table (
            status int,
            rating int
        )
AS $BODY$ Begin
    IF (SELECT tag FROM data.terms_tags WHERE tid = in_tid and tag = in_tag) IS NULL THEN
        return query SELECT -1, 0;
    ELSE
        return query SELECT 0, ttr.rating FROM data.term_tag_rates ttr WHERE
            tid = in_tid and tag = in_tag and uid = in_uid;
    END IF;
END $BODY$ language plpgsql;


-- Get User's Term Lit Rating
Create or Replace Function data.term_lit_rating(in_uid int, in_tid int, in_lid int)
    Returns Table (
            status int,
            rating int
        )
AS $BODY$ Begin
    IF (SELECT lid FROM data.terms_lit WHERE tid = in_tid and lid = in_lid) IS NULL THEN
        return query SELECT -1, 0;
    ELSE
        return query SELECT 0, tlr.rating FROM data.term_lit_rates tlr WHERE
            tid = in_tid and lid = in_lid and uid = in_uid;
    END IF;
END $BODY$ language plpgsql;

-- Get Term By Id
Create or Replace Function data.term_by_id(in_tid int)
    Returns Table (
        status int,
        tid int,
        name varchar,
        description text
    )
AS $BODY$ Begin
    IF (SELECT t.tid FROM data.terms t WHERE t.tid = in_tid) IS NULL THEN
        return query SELECT -1, 0, '-'::varchar, '-'::text;
    ELSE
        return query SELECT 0, t.tid, t.name, t.description FROM data.terms t WHERE t.tid = in_tid;
    END IF;
END $BODY$ language plpgsql;