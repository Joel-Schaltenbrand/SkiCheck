INSERT INTO `application_user` (`id`, `email`, `first_name`, `has_equipment`, `has_paid`, `hashed_password`,
                                `last_name`, `phone_number`, `username`)
VALUES (1, 'admin@admin.ch', 'Admin', b'1', b'1', '$2a$12$VWF6jIhEYSC6CAnmrJ.n3.oMOmxdEnE6uIx5ZYJTHEQ3GCpvKYOCS',
        'Admin', '1234567890', 'admin'),
       (2, 'user@user.ch', 'User', b'1', b'1', '$2a$12$HAZKScigVI/OTAgwrKwm1.pCMNl2.NUkRdovzmq0r5DV3M4PN9zAC', 'User',
        '123456789', 'user');
INSERT INTO `user_roles` (`user_id`, `roles`)
VALUES ('1', 'ADMIN'),
       ('1', 'USER'),
       ('2', 'USER');
