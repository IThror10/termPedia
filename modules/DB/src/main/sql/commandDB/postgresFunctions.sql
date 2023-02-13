-- ReqAuthHandler.register()
Create or Replace Function app.register_user(data jsonb, datetime timestamp, given_type int)
    Returns Table (
        status int,
        login varchar(100)
    )
AS $BODY$ Begin
    IF data->>'Login' in (SELECT users.login FROM app.Users) THEN
        return query SELECT -1, '-'::varchar(100);
    ELSEIF data->>'Email' in (SELECT users.email FROM app.Users) THEN
        return query SELECT -2, '-'::varchar(100);
    ELSEIF NOT data->>'Email' like '___%@_%._%' THEN
        return query SELECT -3, '-'::varchar(100);
    ELSE
        INSERT INTO app.Users (login, password, email, datetime, eventType)
            VALUES (data->>'Login', data->>'Password', data->>'Email', datetime, given_type);
        return query SELECT 0, (data->>'Login')::varchar(100);
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;


    -- ReqAuthHandler.authorize()
Create or Replace Function app.authorize_user(data jsonb)
    Returns Table (
        status int,
        login varchar(100),
        secret varchar(32),
        email varchar(100),
        phone jsonb,
        post jsonb
    )
AS $BODY$ Begin
    IF (SELECT u.uid FROM app.users u WHERE u.login = data->>'Login' and u.password = data->>'Password') is NULL THEN
        return query SELECT -1, '-'::varchar(100), '-'::varchar(32), '-'::varchar(100), '{}'::jsonb, '{}'::jsonb;
    ELSE
        UPDATE app.users u SET (secret, validTo) = (substr(md5(random()::text), 0, 25), CURRENT_TIMESTAMP + '2 hours')
            WHERE u.login = data->>'Login';
        return query SELECT 0, u.login, u.secret, u.email, u.phone_post->'/phone', u.phone_post->'/post'
            FROM app.users u WHERE u.login = data->>'Login';
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;


    --ReqAuthHandler.getUserData()
Create or Replace Function app.get_user_data(data jsonb)
    Returns Table (
        login varchar(100),
        email varchar(100),
        phone jsonb,
        post jsonb
    )
AS $BODY$ Begin
    return query SELECT u.login, u.email, u.phone_post->'/phone', u.phone_post->'/post'
        FROM app.users u WHERE u.login = data->>'Login';
END $BODY$ language plpgsql SECURITY DEFINER;


    --ReqAuthHandler.validate()
Create or Replace Function app.validate(in_login varchar(100), in_secret varchar(50))
    Returns Table (
        status int,
        uid int,
        login varchar(100)
    )
AS $BODY$ Begin
    IF (SELECT u.uid FROM app.users u WHERE u.secret = in_secret and u.login = in_login and CURRENT_TIMESTAMP < u.validTo) is NULL THEN
        UPDATE app.users u SET validTo = null WHERE u.login = in_login;
        return query SELECT -1, 0, '-'::varchar(100);
    ELSE
        return query SELECT 0, u.uid, u.login FROM app.users u WHERE u.login = in_login;
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;


    --ReqAuthHandler.logout()
Create or Replace Function app.logout(in_uid int)
    Returns varchar(1000)
AS $BODY$ Begin
    IF (SELECT u.uid FROM app.users u WHERE u.uid = in_uid) is NULL THEN
        return null::varchar(100);
    ELSE
        UPDATE app.users u SET (validTo, secret) = (NULL, NULL) WHERE u.uid = in_uid;
        return login FROM app.users WHERE uid = in_uid;
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;


    --ReqAuthHandler.changeContactData()
Create or Replace Function app.update_contact_info(in_uid int, op varchar, col varchar, val varchar)
    Returns Table (
        status int,
        login varchar(100),
        email varchar(100),
        phone jsonb,
        post jsonb
    )
AS $BODY$
Begin
    IF col != '/post' and col != '/phone' THEN
        return query SELECT -1, '-'::varchar, '-'::varchar, '[]'::jsonb, '[]'::jsonb;
    ELSE
        IF op = 'add' THEN
            UPDATE app.users u
                SET (phone_post) = (SELECT jsonb_set(phone_post, array[col::text], (phone_post->col) || jsonb_build_array(val)))
                    WHERE u.uid = in_uid;
        ELSEIF op = 'remove' THEN
            UPDATE app.users u
                SET (phone_post) = (SELECT jsonb_set(phone_post, array[col::text], (phone_post->col) - val::text))
                    WHERE u.uid = in_uid;
        ELSE
            return query SELECT -2, '-'::varchar, '-'::varchar, '[]'::jsonb, '[]'::jsonb;
        END IF;

        return query SELECT 0, u.login, u.email, u.phone_post->'/phone', u.phone_post->'/post'
            FROM app.users u WHERE u.uid = in_uid;
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;


    -- EventHandler.acceptEvent()
Create or Replace Function app.accept_event(in_uid integer, in_datetime timestamp, in_type integer, in_data jsonb)
    Returns varchar(100)
AS $BODY$
Declare
    event_uid int;
Begin
    SELECT users.eventType FROM app.users WHERE users.UID = in_uid INTO event_uid;
    IF event_uid = 0 THEN
        INSERT INTO app.Events VALUES (in_uid, in_datetime, in_type, in_data);
        return login from app.users where uid = in_uid;
    ELSEIF event_uid is NULL THEN
        RAISE NOTICE 'User % is Not allowed To do <<%>> Command With Data <<%>>', in_uid, in_type, in_data;
    ELSE
        return null::varchar(100);
    END IF;
END $BODY$ language plpgsql SECURITY DEFINER;