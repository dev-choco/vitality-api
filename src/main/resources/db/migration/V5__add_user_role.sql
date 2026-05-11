ALTER TABLE users ADD COLUMN IF NOT EXISTS role_id BIGINT;

ALTER TABLE users ADD CONSTRAINT fk_users_role
  FOREIGN KEY (role_id) REFERENCES roles(id);

UPDATE users SET role_id = (SELECT id FROM roles WHERE name = 'ROLE_USER')
WHERE role_id IS NULL;
