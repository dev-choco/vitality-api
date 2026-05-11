        INSERT INTO users (email, password_hash, name, role_id, created_at, updated_at)
        SELECT 'admin@vitality.com',
               '$2b$10$ZCXKUTKmvo2R70wiXtF78..26qW392rfOmOvHNlrnI0NGvwYyRwuK',
               'Admin Vitality',
               r.id,
               NOW(),
               NOW()
        FROM roles r
        WHERE r.name = 'ROLE_ADMIN'
        AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@vitality.com');
