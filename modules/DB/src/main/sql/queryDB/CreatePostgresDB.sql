CREATE SCHEMA if NOT EXISTS data;
CREATE TABLE IF NOT EXISTS data.terms (
    name varchar(50) NOT NULL,
    description text NOT NULL,
    vector tsvector,
    TID serial PRIMARY KEY,

    UNIQUE(name, description)
);
Create Index idx_terms_gin ON data.terms USING GIN (vector);
Create Index idx_terms_lower ON data.terms (lower(name));

CREATE TABLE IF NOT EXISTS data.tags (
    name varchar(50) UNIQUE,
    vector tsvector
);
Create Index idx_tag_gin ON data.tags USING GIN (vector);
Create Index idx_tag_lower ON data.tags (lower(name));
INSERT INTO data.tags values(NULL, NULL);

CREATE TABLE IF NOT EXISTS data.authors (
    name varchar(50) PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS data.lit_types (
    name varchar(50) PRIMARY KEY,
    vector tsvector NOT NULL
);
Create Index idx_littype_gin ON data.lit_types USING GIN (vector);
Create Index idx_littype_lower ON data.lit_types (lower(name));

CREATE TABLE IF NOT EXISTS data.users (
    UID int PRIMARY KEY,
    CHECK (UID >= 0)
);

CREATE TABLE IF NOT EXISTS data.lit (
    name varchar(50) NOT NULL,
    year int NOT NULL,
    type varchar(50) NOT NULL,
    authors jsonb NOT NULL,
    vector tsvector,
    LID serial UNIQUE,

    UNIQUE (name, year, type, authors),
    PRIMARY KEY (LID),
    FOREIGN KEY (type) REFERENCES data.lit_types (name)
);
Create Index idx_lit_gin ON data.lit USING GIN (vector);
Create Index idx_lit_lower ON data.lit (lower(name));


CREATE TABLE IF NOT EXISTS data.terms_tags (
    TID int NOT NULL,
    tag varchar(50),
    rates_amount int NOT NULL DEFAULT 0,
    rates_sum int NOT NULL DEFAULT 0,
    rating real NOT NULL DEFAULT 0,

    UNIQUE (TID, tag),
    FOREIGN KEY (TID) REFERENCES data.terms (TID),
    FOREIGN KEY (tag) REFERENCES data.tags (name));

CREATE TABLE IF NOT EXISTS data.terms_lit (
    TID int NOT NULL,
    LID int NOT NULL,
    rates_amount int NOT NULL DEFAULT 0,
    rates_sum int NOT NULL DEFAULT 0,
    rating real NOT NULL DEFAULT 0,

    UNIQUE (TID, LID),
    FOREIGN KEY (TID) REFERENCES data.terms(TID),
    FOREIGN KEY (LID) REFERENCES data.lit(LID));

CREATE TABLE IF NOT EXISTS data.authors_lit (
    author varchar(50) NOT NULL,
    LID int NOT NULL,

    UNIQUE (author, LID),
    FOREIGN KEY (author) REFERENCES data.authors (name),
    FOREIGN KEY (LID) REFERENCES data.lit (LID));

CREATE TABLE IF NOT EXISTS data.term_tag_rates (
    UID int NOT NULL,
    TID int NOT NULL,
    tag varchar(50),
    rating int NOT NULL,

    CONSTRAINT single_mark_tag UNIQUE (UID, TID, tag),
    FOREIGN KEY (UID) REFERENCES data.users (UID),
    CONSTRAINT tid_tag_exist FOREIGN KEY (TID, tag) REFERENCES data.terms_tags (TID, tag),
    CHECK (rating >= 0 and rating <= 5)
);

CREATE TABLE IF NOT EXISTS data.term_lit_rates (
    UID int NOT NULL,
    TID int NOT NULL,
    LID int NOT NULL,
    rating int NOT NULL,

    CONSTRAINT single_mark_lit UNIQUE (UID, TID, LID),
    FOREIGN KEY (UID) REFERENCES data.users (UID),
    CONSTRAINT tid_lid_exist FOREIGN KEY (TID, LID) REFERENCES data.terms_lit (TID, LID),
    CHECK (rating >= 0 and rating <= 5)
);


CREATE TABLE IF NOT EXISTS data.book_search (
    LID int NOT NULL,
    name varchar(50) NOT NULL,
    year int NOT NULL,
    type varchar(50) NOT NULL,
    authors jsonb NOT NULL,
    tag varchar(50),
    rating real NOT NULL DEFAULT 0,

    UNIQUE (name, tag),
    FOREIGN KEY (LID) REFERENCES data.lit (LID),
    FOREIGN KEY (tag) REFERENCES data.tags (name),
    CHECK (rating >= 0 and rating <= 5)
);
CREATE INDEX IF NOT EXISTS term_idx ON data.book_search (tag);



-- #### DATA.LIT
Create or Replace Function data.for_new_authors()
    Returns Trigger
AS $Body$
DECLARE
    i json;
Begin
    FOR i IN SELECT * FROM jsonb_array_elements(new.authors) LOOP
        -- Add Author
        INSERT INTO data.authors values (i#>>'{}')
            ON CONFLICT DO NOTHING;
        -- Add Author-Lit Pare
        INSERT INTO data.authors_lit VALUES (i#>>'{}', new.LID)
            ON CONFLICT DO NOTHING;
    END LOOP;
    -- Book search with Null Tag
    INSERT INTO data.book_search
        SELECT new.LID, new.name, new.year, new.type, new.authors, null;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_lit_insert
    AFTER INSERT on data.lit for each row
        execute Function data.for_new_authors();

-- #### DATA.TERMS_TAGS
Create or Replace Function data.on_new_term_tag()
    Returns Trigger
AS $Body$ Begin
    INSERT INTO data.book_search
        SELECT tl.LID, l.name, l.year, l.type, l.authors, new.tag
            FROM data.terms_lit tl join data.lit l on tl.TID = new.TID and l.lid = tl.lid
        ON CONFLICT DO NOTHING;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_terms_tags_insert
    AFTER INSERT on data.terms_tags for each row
        execute Function data.on_new_term_tag();

Create or Replace Function data.on_term_tag_rating_change()
    Returns Trigger
AS $Body$ Begin
    -- Change Tag Rating
    with lit_tag_rating(lit_id, tag_rating) as
        (SELECT tl.LID, max(tl.rating * tt.rating / 5)
            FROM data.terms_lit tl join data.terms_tags tt on tt.tag = new.tag and tt.TID = tl.TID
                GROUP BY tl.LID)
    UPDATE data.book_search
        SET rating = tag_rating
            FROM lit_tag_rating
                WHERE lid = lit_id and tag = new.tag;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_terms_tags_update
    AFTER UPDATE on data.terms_tags for each row
        execute Function data.on_term_tag_rating_change();

-- #### DATA.TERMS_LIT
Create or Replace Function data.on_new_term_lit()
    Returns Trigger
AS $Body$ Begin
    -- Associate book with new tags
    INSERT INTO data.book_search
        SELECT l.LID, l.name, l.year, l.type, l.authors, tt.tag
            FROM data.lit l join data.terms_tags tt on l.LID = new.LID and tt.TID = new.TID
        ON CONFLICT DO NOTHING;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_term_lit_insert
    AFTER INSERT on data.terms_lit for each row
        execute Function data.on_new_term_lit();

Create or Replace Function data.on_term_lit_rating_change()
    Returns Trigger
AS $Body$ Begin
    -- Change Tag Rating
    with lit_tag_rating (tag_name, tag_rating) as
        (SELECT tt.tag, max(tl.rating * tt.rating / 5) as rating
            FROM data.terms_lit tl join data.terms_tags tt on tl.lid = new.lid and tt.TID = tl.TID
                and tt.tag in (SELECT tag FROM data.terms_tags WHERE TID = new.TID)
            GROUP BY tt.tag)
    UPDATE data.book_search
        SET rating = tag_rating
            FROM lit_tag_rating
                WHERE tag = tag_name and lid = new.lid;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_terms_lit_update
    AFTER UPDATE on data.terms_lit for each row
        execute Function data.on_term_lit_rating_change();

-- #### Keep Actual Rating in data.terms_tags && data.terms_lit
Create or Replace Function data.keep_actual_rating()
    Returns Trigger
AS $Body$ Begin
    new.rating := new.rates_sum::real / new.rates_amount::real;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger before_tag_rating_change
    BEFORE UPDATE on data.terms_tags
        for each row
            execute Function data.keep_actual_rating();

Create or Replace Trigger before_lit_rating_change
    BEFORE UPDATE on data.terms_lit for each row
        execute Function data.keep_actual_rating();

-- #### Term_Lit_Rates
Create or Replace Function data.on_change_lit_rating()
    Returns Trigger
AS $Body$ Begin
    -- Changed rating
    IF (TG_OP = 'INSERT') then
        UPDATE data.terms_lit
        SET (rates_amount, rates_sum) = (rates_amount + 1, rates_sum + new.rating)
            WHERE lid = new.lid and TID = new.TID;
    ELSE
        UPDATE data.terms_lit
        SET rates_sum = rates_sum + new.rating - old.rating
            WHERE lid = new.lid and TID = new.TID;
    END IF;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_term_lit_rate_change
    AFTER INSERT or UPDATE on data.term_lit_rates for each row
        execute Function data.on_change_lit_rating();

-- #### Term_Tag_Rates
Create or Replace Function data.on_change_tag_rating()
    Returns Trigger
AS $Body$ Begin
    -- Changed rating
    IF (TG_OP = 'INSERT') then
        UPDATE data.terms_tags
        SET (rates_amount, rates_sum) = (rates_amount + 1, rates_sum + new.rating)
            WHERE TID = new.TID and tag = new.tag;
    ELSE
        UPDATE data.terms_tags
        SET rates_sum = rates_sum + new.rating - old.rating
            WHERE TID = new.TID and tag = new.tag;
    END IF;
    return new;
End $Body$ language plpgsql;

Create or Replace Trigger after_term_tag_rate_change
    AFTER INSERT or UPDATE on data.term_tag_rates
        for each row
            execute Function data.on_change_tag_rating();


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


-- Updater.newUser()
Create or Replace Procedure data.add_user(in_uid int)
AS $Body$ Begin
    INSERT INTO data.users VALUES (in_uid)
        ON CONFLICT DO NOTHING;
End $Body$ language plpgsql SECURITY DEFINER;

-- Updater.newTerm()
Create or Replace Procedure data.add_term(data jsonb)
AS $Body$ Begin
    INSERT INTO data.terms VALUES (data->>'Term', data->>'Description',
    to_tsvector('english', concat(data->>'Term', ' ', data->>'Description')))
        ON CONFLICT DO NOTHING;
End $Body$ language plpgsql SECURITY DEFINER;

-- Updater.newLit()
Create or Replace Procedure data.add_lit(data jsonb)
AS $Body$ Begin
    INSERT INTO data.lit_types VALUES (data->'Book'->>'Type', to_tsvector(data->'Book'->>'Type'))
        ON CONFLICT DO NOTHING;
    INSERT INTO data.lit VALUES (data->'Book'->>'Name', (data->'Book'->>'Year')::int,
        data->'Book'->>'Type', data->'Book'->'Authors', to_tsvector(data->'Book'->>'Name'))
            ON CONFLICT DO NOTHING;
End $Body$ language plpgsql SECURITY DEFINER;

-- Updater.newLitTermPare()
Create or Replace Procedure data.add_lit_term(data jsonb)
AS $Body$
Declare
    unique_lid int;
Begin
    INSERT INTO data.terms_lit VALUES ((data->>'TID')::int, (data->>'LID')::int);
End $Body$ language plpgsql SECURITY DEFINER;

--Updater.NewTagTermPare()
Create or Replace Procedure data.add_tag_term(data jsonb)
AS $Body$ Begin
    INSERT INTO data.tags VALUES (data->>'Tag', to_tsvector(data->>'Tag'))
        ON CONFLICT DO NOTHING;
    INSERT INTO data.terms_tags VALUES ((data->>'TID')::int, data->>'Tag')
        ON CONFLICT DO NOTHING;
End $Body$ language plpgsql SECURITY DEFINER;

--Updater.newRateTermLit()
Create or Replace Procedure data.rate_lit_term(data jsonb, in_uid int)
AS $Body$ Begin
    INSERT INTO data.term_lit_rates as tlr
        VALUES (in_uid, (data->>'TID')::int, (data->>'LID')::int, (data->>'Mark')::int)
    ON CONFLICT ON CONSTRAINT single_mark_lit
        DO UPDATE set rating = (data->>'Mark')::int
    WHERE tlr.uid = in_uid and tlr.TID = (data->>'TID')::int and tlr.lid = (data->>'LID')::int;
End $Body$ language plpgsql SECURITY DEFINER;

--Updater.newRateTermTag()
Create or Replace Procedure data.rate_tag_term(data jsonb, in_uid int)
AS $Body$ Begin
    INSERT INTO data.term_tag_rates as ttr
        VALUES (in_uid, (data->>'TID')::int, data->>'Tag', (data->>'Mark')::int)
    ON CONFLICT ON CONSTRAINT single_mark_tag
        DO UPDATE set rating = (data->>'Mark')::int
    WHERE ttr.uid = in_uid and ttr.TID = (data->>'TID')::int and ttr.tag = data->>'Tag';
End $Body$ language plpgsql SECURITY DEFINER;


-- Create Roles
Create User query_reader Password 'read_only';
Create User query_synchronizer Password 'synch';

-- Grant Privileges
    GRANT USAGE ON SCHEMA data TO query_synchronizer;
GRANT EXECUTE ON ALL PROCEDURES IN SCHEMA data TO query_synchronizer;
GRANT SELECT ON ALL TABLES IN SCHEMA data TO query_synchronizer;
GRANT SELECT ON SEQUENCE data.lit_lid_seq TO query_synchronizer;

    GRANT USAGE ON SCHEMA data TO query_reader;
GRANT SELECT ON ALL TABLES IN SCHEMA data TO query_reader;
GRANT SELECT ON SEQUENCE data.lit_lid_seq TO query_reader;


-- Users
call data.add_user(0);

-- Terms
call data.add_term('{"Term" : "OOP", "Description" : "Object Oriented Programming"}');
call data.add_term('{"Term" : "Singleton", "Description" : "Single object pattern"}');

-- Lit
call data.add_lit('{"Book" : {"Name" : "Go4", "Type" : "book", "Year" : 2010, "Authors" : ["ABCD", "DEF"]}}');
call data.add_lit('{"Book" : {"Name" : "White Fang", "Type" : "Novel", "Year" : 1906, "Authors" : ["London J."]}}');