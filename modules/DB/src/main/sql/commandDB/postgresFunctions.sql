-- ReqAuthHandler.register()
Create or Replace Function app.register_user(data jsonb, datetime timestamp, given_type int)
    Returns Table (
        uid int,
        login varchar(100),
        email varchar(100),
        phone varchar(50)[],
        post varchar(50)[]
    )
AS $BODY$ Begin
    IF data->>'Login' in (SELECT users.login FROM app.Users) THEN
        return query SELECT -1, null, null, null, null;
    ELSEIF data->>'Email' in (SELECT users.email FROM app.Users) THEN
        return query SELECT -2, null, null, null, null;
    ELSEIF NOT data->>'Email' like '___%@_%._%' THEN
        return query SELECT -3, null, null, null, null;
    ELSE
        INSERT INTO app.Users (login, password, email, datetime, eventType)
            VALUES (data->>'Login', data->>'Password', data->>'Email', datetime, given_type);
        return query SELECT u.uid, u.login, u.email, u.phone, u.post
             FROM app.users u WHERE u.login = data->>'Login';
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;

    -- ReqAuthHandler.authorize()
Create or Replace Function app.authorize_user(data jsonb)
    Returns Table (
        uid int,
        login varchar(100),
        email varchar(100),
        phone varchar(50)[],
        post varchar(50)[]
    )
AS $BODY$ Begin
    IF (SELECT u.uid FROM app.users u WHERE u.login = data->>'Login' and u.password = data->>'Password') is NULL THEN
        return query SELECT -1, '-'::varchar(100), '-'::varchar(100), '{}'::varchar(50)[], '{}'::varchar(50)[];
    ELSE
        return query SELECT u.uid, u.login, u.email, u.phone, u.post
            FROM app.users u WHERE u.login = data->>'Login' and u.password = data->>'Password';
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;

    -- EventHandler.acceptEvent()
Create or Replace Function app.accept_event(in_uid integer, in_datetime timestamp, in_type integer, in_data jsonb)
    Returns boolean
AS $BODY$
Declare
    event_uid int;
Begin
    SELECT users.eventType FROM app.users WHERE users.UID = in_uid INTO event_uid;
    IF event_uid = 0 THEN
        INSERT INTO app.Events VALUES (in_uid, in_datetime, in_type, in_data);
        return true;
    ELSEIF event_uid is NULL THEN
        RAISE NOTICE 'User % is Not allowed To do <<%>> Command With Data <<%>>', in_uid, in_type, in_data;
    ELSE
        return false;
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;


    --ReqAuthHandler.getUserData()
Create or Replace Function app.get_user_data(data jsonb)
    Returns Table (
        uid int,
        login varchar(100),
        email varchar(100),
        phone varchar(50)[],
        post varchar(50)[]
    )
AS $BODY$ Begin
    return query SELECT u.uid, u.login, u.email, u.phone, u.post
        FROM app.users u WHERE u.login = data->>'Login';
END $BODY$ language plpgsql SECURITY DEFINER;