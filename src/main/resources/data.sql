do
'
declare
    r_id integer;
begin
    insert into public.roles (name)
    values  (''ROLE_ADMIN''),
            (''ROLE_USER''),
            (''ROLE_MODERATOR'');

    insert into public.accounts (deleted, role_id, email, name, password)
    values  (false, 1, ''admin@gmail.com'', ''admin'', ''{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO''),
            (false, 2, ''user@gmail.com'', ''user'', ''{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO'');

    insert into public.amenity (price, type, description, name)
    values  (10, 1, ''какое-то описание сейфа'', ''Сейф''),
            (0, 1, ''небольшой холодильник в номере'', ''Холодильник''),
            (0, 1, ''позволяющий обогревать и охлаждать номер'', ''Кондиционер''),
            (0, 0, ''описание нашего классного бассейна'', ''Бассейн''),
            (100, 0, ''сауна и термальный комплекс'', ''Сауна''),
            (100, 0, ''описание аквапарка'', ''Аквапарк'');

    insert into public.hotels (deleted, hotel_admin_id, description, name, city)
    values  (false, 1, ''описание нашего классного отеля в горах'', ''Отель в горах'', ''Milan'') returning id into r_id;

    insert into public.hotels_amenities (amenity_id, hotel_id)
    VALUES (4, r_id), (5, r_id);

    insert into public.hotels (deleted, hotel_admin_id, description, name, city)
    values (false, 1, ''описание нашего отеля в городе, что рядом и т.д.'', ''Городские апартаменты'', ''Paris'') returning id into r_id;

    insert into public.hotels_amenities (amenity_id, hotel_id)
    VALUES (6, r_id), (5, r_id);

    insert into public.hotels (deleted, hotel_admin_id, description, name, city)
    values (false, 1, ''первая линия, лучшие пляжи и все такое'', ''Морской берег'', ''Barcelona'') returning id into r_id;

    insert into public.hotels_amenities (amenity_id, hotel_id)
    VALUES (4, r_id), (6, r_id);

    insert into public.rooms (capacity, deleted, price, hotel_id, name)
    values  (5, false, 50.00, 1, ''семейный номер'') returning id into r_id;

    insert into public.rooms_amenities (amenity_id, room_id)
    VALUES (1, r_id), (2, r_id);

    insert into public.rooms (capacity, deleted, price, hotel_id, name)
    values (2, false, 23.00, 1, ''Сингл номер'') returning id into r_id;

    insert into public.rooms_amenities (amenity_id, room_id)
    VALUES (2, r_id), (3, r_id);

    insert into public.rooms (capacity, deleted, price, hotel_id, name)
    values (4, false, 43.00, 1, ''Дабл номер'') returning id into r_id;

    insert into public.rooms_amenities (amenity_id, room_id)
    VALUES (1, r_id), (2, r_id), (3, r_id);

    insert into public.rooms (capacity, deleted, price, hotel_id, name)
    values (2, false, 12.00, 2, ''Маленький номер'') returning id into r_id;

    insert into public.rooms_amenities (amenity_id, room_id)
    VALUES (1, r_id);

    insert into public.rooms (capacity, deleted, price, hotel_id, name)
    values (4, false, 24.00, 2, ''Большой номер'') returning id into r_id;

    insert into public.rooms_amenities (amenity_id, room_id)
    VALUES (1, r_id), (2, r_id), (3, r_id);

    insert into public.rooms (capacity, deleted, price, hotel_id, name)
    values (1, false, 150.00, 3, ''Одиночный номер'') returning id into r_id;

    insert into public.rooms_amenities (amenity_id, room_id)
    VALUES (3, r_id);

    insert into public.rooms (capacity, deleted, price, hotel_id, name)
    values (2, false, 220.00, 3, ''Двойной номер'') returning id into r_id;

    insert into public.rooms_amenities (amenity_id, room_id)
    VALUES (1, r_id), (2, r_id), (3, r_id);
end;
' LANGUAGE PLPGSQL;
