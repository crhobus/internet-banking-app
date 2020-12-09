INSERT INTO permission
(id, permission, created_at, updated_at)
VALUES(nextval('permission_seq'), 'ADMIN', timezone('utc',now()), timezone('utc',now()));

INSERT INTO permission
(id, permission, created_at, updated_at)
VALUES(nextval('permission_seq'), 'MANAGER', timezone('utc',now()), timezone('utc',now()));

INSERT INTO permission
(id, permission, created_at, updated_at)
VALUES(nextval('permission_seq'), 'USER', timezone('utc',now()), timezone('utc',now()));