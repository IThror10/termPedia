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