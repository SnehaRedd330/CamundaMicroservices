INSERT IGNORE INTO roles(name) VALUES('ROLE_USER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_SUPERUSER');
INSERT IGNORE INTO roles(name) VALUES('ROLE_ADMIN');
INSERT IGNORE INTO process_category(process_cate) VALUES('Development');
INSERT IGNORE INTO users (id, created_at, updated_at, email, name, password, username) VALUES (25, NOW(), NOW(), 'SJ00495527@techmahindra.com', 'MuDynamics Admin', '$2a$10$tcjc80BVKHde.en2dfJKU.yfJ1.B5VMTTii/kPGEGDoWmMFTTmUSu', 'ADMIN');
INSERT IGNORE INTO user_roles(user_id, role_id) VALUES(25,3);
INSERT IGNORE INTO user_process_category(user_id, process_category_id) VALUES(25,1);