CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255),
    name VARCHAR(150) NOT NULL,
    avatar_url VARCHAR(500),
    google_id VARCHAR(100) UNIQUE,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE refresh_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token VARCHAR(500) NOT NULL UNIQUE,
    expires_at TIMESTAMP NOT NULL,
    revoked BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE food_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    slug VARCHAR(120) NOT NULL UNIQUE,
    icon VARCHAR(50)
);

CREATE TABLE foods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    slug VARCHAR(250) NOT NULL UNIQUE,
    category_id BIGINT NOT NULL REFERENCES food_categories(id),
    description TEXT,
    image_url VARCHAR(1000),
    calories_per_100g DOUBLE PRECISION,
    protein_g DOUBLE PRECISION,
    carbs_g DOUBLE PRECISION,
    fat_g DOUBLE PRECISION,
    fiber_g DOUBLE PRECISION,
    benefits TEXT,
    consumption_tips TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE recipes (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(300) NOT NULL,
    slug VARCHAR(350) NOT NULL UNIQUE,
    description TEXT,
    image_url VARCHAR(1000),
    prep_time_min INTEGER,
    difficulty VARCHAR(50),
    budget_tag VARCHAR(30),
    calories INTEGER,
    protein_g DOUBLE PRECISION,
    carbs_g DOUBLE PRECISION,
    fat_g DOUBLE PRECISION,
    goal_tags VARCHAR(500),
    instructions TEXT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE recipe_ingredients (
    id BIGSERIAL PRIMARY KEY,
    recipe_id BIGINT NOT NULL REFERENCES recipes(id) ON DELETE CASCADE,
    food_id BIGINT NOT NULL REFERENCES foods(id),
    quantity VARCHAR(100),
    unit VARCHAR(50),
    optional BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE myths (
    id BIGSERIAL PRIMARY KEY,
    myth_text TEXT NOT NULL,
    reality_text TEXT NOT NULL,
    myth_explanation TEXT,
    reality_explanation TEXT,
    category VARCHAR(100),
    image_url VARCHAR(1000),
    scientific_source VARCHAR(500),
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE saved_plates (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    protein_food_id BIGINT REFERENCES foods(id),
    carb_food_id BIGINT REFERENCES foods(id),
    veggie_food_ids TEXT,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_google_id ON users(google_id);
CREATE INDEX idx_foods_slug ON foods(slug);
CREATE INDEX idx_foods_category ON foods(category_id);
CREATE INDEX idx_foods_name ON foods(name);
CREATE INDEX idx_recipes_slug ON recipes(slug);
CREATE INDEX idx_recipes_goals ON recipes(goal_tags);
CREATE INDEX idx_recipes_budget ON recipes(budget_tag);
CREATE INDEX idx_recipe_ingredients_recipe ON recipe_ingredients(recipe_id);
CREATE INDEX idx_recipe_ingredients_food ON recipe_ingredients(food_id);
CREATE INDEX idx_myths_category ON myths(category);
CREATE INDEX idx_saved_plates_user ON saved_plates(user_id);
CREATE INDEX idx_refresh_tokens_token ON refresh_tokens(token);
CREATE INDEX idx_refresh_tokens_user ON refresh_tokens(user_id);
