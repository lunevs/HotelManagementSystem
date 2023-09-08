
insert into public.roles (name)
values  ('ROLE_ADMIN'),
        ('ROLE_USER'),
        ('ROLE_MODERATOR');

insert into public.accounts (deleted, role_id, email, name, password)
values  (false, 1, 'admin@gmail.com', 'admin', '{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO'),
        (false, 2, 'user@gmail.com', 'user', '{bcrypt}$2a$12$ZJxPYIUg0al2/qf0AwqI1./QfF.WAVrYXt/GcRDMJB2CKFcSx9ksO');

insert into public.amenity (price, type, description, name)
values  (10, 1, 'какое-то описание сейфа', 'Сейф'),
        (0, 1, 'небольшой холодильник в номере', 'Холодильник'),
        (0, 0, 'описание нашего классного бассейна', 'Бассейн'),
        (100, 0, 'сауна и термальный комплекс', 'Сауна'),
        (100, 0, 'описание аквапарка', 'Аквапарк'),
        (0, 1, 'позволяющий обогревать и охлаждать номер', 'Кондиционер');

insert into public.hotels (deleted, hotel_admin_id, description, name, city)
values  (false, 1, 'описание нашего классного отеля в горах', 'Отель в горах', 'Milan'),
        (false, 1, 'описание нашего отеля в городе, что рядом и т.д.', 'Городские апартаменты', 'Paris'),
        (false, 1, 'первая линия, лучшие пляжи и все такое', 'Морской берег', 'Barcelona');

insert into public.rooms (capacity, deleted, price, hotel_id, name)
values  (5, false, 50.00, 1, 'семейный номер'),
        (2, false, 23.00, 1, 'Сингл номер'),
        (4, false, 43.00, 1, 'Дабл номер'),
        (2, false, 12.00, 2, 'Маленький номер'),
        (4, false, 24.00, 2, 'Большой номер'),
        (1, false, 150.00, 3, 'Одиночный номер'),
        (2, false, 220.00, 3, 'Двойной номер');

