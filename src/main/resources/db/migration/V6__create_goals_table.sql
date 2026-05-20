CREATE TABLE IF NOT EXISTS goals (
  id BIGSERIAL PRIMARY KEY,
  slug VARCHAR(100) NOT NULL UNIQUE,
  name VARCHAR(150) NOT NULL,
  icon VARCHAR(50),
  description TEXT,
  color_class VARCHAR(30),
  active BOOLEAN NOT NULL DEFAULT true,
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

INSERT INTO goals (slug, name, icon, description, color_class) VALUES
('bajar-peso', 'Bajar de peso', 'fitness_center', 'Recetas y alimentos para alcanzar tu peso ideal', 'primary'),
('subir-peso', 'Subir de peso', 'trending_up', 'Gana masa muscular de forma saludable', 'tertiary'),
('comer-economico', 'Comer económico', 'payments', 'Nutrición completa sin gastar de más', 'secondary'),
('energia-diaria', 'Energía diaria', 'bolt', 'Alimentos que te mantienen activo todo el día', 'primary')
ON CONFLICT (slug) DO NOTHING;
