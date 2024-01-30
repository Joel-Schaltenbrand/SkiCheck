/*
 * MIT License
 *
 * Copyright (c) 2024 Masterplan AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

INSERT INTO `application_user` (`id`, `email`, `first_name`, `has_equipment`, `has_paid`, `hashed_password`,
                                `last_name`, `username`)
VALUES (1, 'r.zaugg@bbzbl-it.ch', 'Roger', b'1', b'1', '$2a$12$22OtCV2pI0t1jOzrZKlt3uWhLjcz..brQv1Vdo1bh7EWPzZlaMzeu',
        'Zaugg', 'roger.zaugg'),
       (2, 'joel.schaltenbrand@bbzbl-it.ch', 'Joel', b'1', b'1',
        '$2a$12$Ny1BLWIyQH8F4JvP5dAA9uvzPUT7X78L8RKFi/VEQeqXe5k73isBK', 'Schaltenbrand',
        'joel.schaltenbrand');
INSERT INTO `user_roles` (`user_id`, `roles`)
VALUES ('1', 'ADMIN'),
       ('1', 'USER'),
       ('2', 'USER');
