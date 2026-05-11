INSERT INTO users (email, password_hash, name, role_id) VALUES
('admin@vitality.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
 'Admin Vitality', (SELECT id FROM roles WHERE name = 'ROLE_ADMIN'))
ON CONFLICT (email) DO NOTHING;

INSERT INTO food_categories (name, slug, icon) VALUES
('Proteínas', 'proteinas', 'egg_alt'),
('Granos', 'granos', 'grain'),
('Vegetales', 'vegetales', 'eco'),
('Frutas', 'frutas', 'nutrition');

INSERT INTO foods (name, slug, category_id, description, image_url, calories_per_100g, protein_g, carbs_g, fat_g, fiber_g, benefits, consumption_tips) VALUES
('Huevo', 'huevo', 1, 'Proteína completa de origen animal con todos los aminoácidos esenciales.', 'https://placehold.co/600x400/986b42/fffbff?text=Huevo', 155, 13, 1.1, 11, 0, 'Proteína completa,Económico', 'Revuelto con vegetales'),
('Pollo', 'pollo', 1, 'Pechuga de pollo magra, ideal para desarrollo muscular.', 'https://placehold.co/600x400/986b42/fffbff?text=Pollo', 165, 31, 0, 3.6, 0, 'Alto en proteína,Bajo en grasa', 'A la plancha con especias'),
('Frijoles', 'frijoles', 1, 'Legumbre rica en proteína vegetal y fibra.', 'https://placehold.co/600x400/986b42/fffbff?text=Frijoles', 127, 8.7, 22.8, 0.5, 6.4, 'Fibra y proteína,Saciedad prolongada', 'Guiso con especias'),
('Lentejas', 'lentejas', 1, 'Legumbre económica con alto contenido de hierro.', 'https://placehold.co/600x400/986b42/fffbff?text=Lentejas', 116, 9, 20, 0.4, 8, 'Hierro y energía,Muy barato', 'Guiso con zanahoria'),
('Avena', 'avena', 2, 'Cereal integral rico en fibra soluble.', 'https://placehold.co/600x400/e6e3d0/666556?text=Avena', 389, 16.9, 66.3, 6.9, 10.6, 'Rica en fibra,Genera saciedad', 'Avena con banano'),
('Arroz', 'arroz', 2, 'Carbohidrato complejo base en muchas culturas.', 'https://placehold.co/600x400/e6e3d0/666556?text=Arroz', 130, 2.7, 28.2, 0.3, 0.4, 'Energía sostenida,Fácil de digerir', 'Acompañamiento versátil'),
('Papa', 'papa', 2, 'Tubérculo versátil y económico.', 'https://placehold.co/600x400/e6e3d0/666556?text=Papa', 77, 2, 17.5, 0.1, 2.2, 'Fuente de potasio,Saciedad duradera', 'Al horno o hervida'),
('Pasta', 'pasta', 2, 'Carbohidrato ideal para energía rápida.', 'https://placehold.co/600x400/e6e3d0/666556?text=Pasta', 131, 5, 25, 1.1, 1.8, 'Energía disponible,Rendimiento físico', 'Al dente con vegetales'),
('Brócoli', 'brocoli', 3, 'Vegetal crucífero con múltiples vitaminas.', 'https://placehold.co/600x400/0b6947/ffffff?text=Brocoli', 34, 2.8, 7, 0.4, 2.6, 'Vitamina C,Fibra y antioxidantes', 'Al vapor o salteado'),
('Ensalada mixta', 'ensalada-mixta', 3, 'Mezcla de hojas verdes frescas con vitaminas A y K.', 'https://placehold.co/600x400/0b6947/ffffff?text=Ensalada', 20, 1.5, 3.6, 0.2, 2.2, 'Vitaminas esenciales,Bajo en calorías', 'Con aderezo ligero'),
('Zanahoria', 'zanahoria', 3, 'Raíz rica en betacaroteno y vitamina A.', 'https://placehold.co/600x400/0b6947/ffffff?text=Zanahoria', 41, 0.9, 10, 0.2, 2.8, 'Salud ocular,Antioxidante natural', 'Cruda o al vapor'),
('Espinaca', 'espinaca', 3, 'Hoja verde densa en nutrientes.', 'https://placehold.co/600x400/0b6947/ffffff?text=Espinaca', 23, 2.9, 3.6, 0.4, 2.2, 'Salud ocular,Inmunidad', 'Batido verde'),
('Aguacate', 'aguacate', 4, 'Fruta rica en grasas monoinsaturadas saludables.', 'https://placehold.co/600x400/30835f/f5fff6?text=Aguacate', 160, 2, 8.5, 14.7, 6.7, 'Grasas saludables,Piel radiante', 'Tostada integral');

INSERT INTO recipes (title, slug, description, image_url, prep_time_min, difficulty, budget_tag, calories, protein_g, carbs_g, fat_g, goal_tags, meal_type, instructions) VALUES
('Avena con Frutos Rojos', 'avena-con-frutos-rojos', 'Desayuno nutritivo listo en minutos. Rico en fibra y antioxidantes.', 'https://placehold.co/600x400/e6e3d0/666556?text=Avena+Frutos+Rojos', 10, 'Fácil', 'bajo', 320, 12, 55, 8, 'bajar-peso,energia-diaria', 'desayuno', 'Mezclar avena con leche y frutos rojos. Endulzar al gusto.'),
('Tostada de Aguacate y Huevo', 'tostada-de-aguacate-y-huevo', 'Tostada integral con aguacate fresco y huevo en todas sus formas.', 'https://placehold.co/600x400/30835f/f5fff6?text=Tostada+Aguacate', 15, 'Fácil', 'bajo', 450, 18, 35, 22, 'subir-peso,energia-diaria', 'desayuno', 'Tostar el pan. Machacar aguacate y colocar encima. Cocinar huevo al gusto.'),
('Bowl de Pollo y Arroz', 'bowl-de-pollo-y-arroz', 'Almuerzo balanceado con proteína magra y carbohidratos complejos.', 'https://placehold.co/600x400/986b42/fffbff?text=Pollo+Arroz', 25, 'Media', 'bajo', 580, 42, 45, 12, 'subir-peso,energia-diaria', 'almuerzo', 'Cocinar arroz. Grillar el pollo. Mezclar con vegetales.'),
('Ensalada de Garbanzos', 'ensalada-de-garbanzos', 'Ensalada vegana fresca rica en proteína vegetal.', 'https://placehold.co/600x400/0b6947/ffffff?text=Ensalada+Garbanzos', 15, 'Fácil', 'bajo', 420, 15, 48, 18, 'bajar-peso', 'almuerzo', 'Mezclar garbanzos cocidos con verduras frescas y aderezo.'),
('Arroz con Huevo y Espinaca', 'arroz-con-huevo-y-espinaca', 'Comida rápida y nutritiva con ingredientes básicos.', 'https://placehold.co/600x400/e6e3d0/666556?text=Arroz+Huevo+Espinaca', 10, 'Fácil', 'bajo', 380, 18, 45, 12, 'bajar-peso', 'almuerzo', 'Cocinar arroz. Saltear espinaca. Acompañar con huevo frito.'),
('Lentejas al Curry', 'lentejas-al-curry', 'Guiso cremoso de lentejas con especias orientales.', 'https://placehold.co/600x400/986b42/fffbff?text=Lentejas+Curry', 25, 'Fácil', 'bajo', 350, 18, 52, 6, 'bajar-peso', 'almuerzo', 'Cocinar lentejas con cebolla. Agregar curry y leche de coco.'),
('Bowl de Garbanzos Vital', 'bowl-de-garbanzos-vital', 'Bowl mediterráneo con garbanzos, aguacate y limón.', 'https://placehold.co/600x400/0b6947/ffffff?text=Garbanzos+Vital', 15, 'Fácil', 'bajo', 430, 16, 50, 16, 'energia-diaria', 'almuerzo', 'Mezclar garbanzos, aguacate en cubos y jugo de limón.'),
('Tostada Energética', 'tostada-energetica', 'Tostada integral con palta y semillas para iniciar el día.', 'https://placehold.co/600x400/30835f/f5fff6?text=Tostada+Energetica', 5, 'Fácil', 'bajo', 310, 10, 32, 16, 'energia-diaria', 'desayuno', 'Tostar pan integral. Untar palta y esparcir semillas.');

INSERT INTO recipe_ingredients (recipe_id, food_id, quantity, unit) VALUES
(1, 5, '1/2', 'taza'),
(1, 12, '1', 'puñado'),
(2, 13, '1/2', 'unidad'),
(2, 1, '1', 'unidad'),
(3, 2, '150', 'g'),
(3, 6, '1', 'taza'),
(4, 3, '200', 'g'),
(4, 13, '1/2', 'unidad'),
(5, 6, '1', 'taza'),
(5, 1, '1', 'unidad'),
(5, 12, '1', 'puñado'),
(6, 4, '1', 'taza'),
(6, 11, '1', 'unidad'),
(7, 3, '200', 'g'),
(7, 13, '1/2', 'unidad'),
(8, 13, '1/2', 'unidad');

INSERT INTO myths (myth_text, reality_text, myth_explanation, reality_explanation, category) VALUES
('Cenar carbohidratos engorda', 'Lo que importa es el total de calorías del día', 'Se cree que comer carbohidratos en la noche se almacena como grasa por la falta de actividad física nocturna.', 'El balance calórico total del día es lo que determina el peso. El cuerpo procesa los nutrientes igual sin importar la hora.', 'Carbohidratos'),
('El pan engorda', 'El pan es energía necesaria', 'Se demoniza el pan como causa directa de aumento de peso.', 'Consumido con moderación y prefiriendo versiones integrales, es una fuente vital de fibra y energía compleja para el cerebro.', 'Carbohidratos'),
('Comer sano es más caro', 'Planificar ahorra dinero', 'Existe la creencia de que una dieta saludable requiere productos orgánicos y especializados costosos.', 'Comprar productos de temporada y legumbres a granel es significativamente más económico que los ultraprocesados.', 'Hábitos'),
('Los jugos detox limpian tu cuerpo', 'Tus órganos ya hacen ese trabajo', 'Los jugos detox se promocionan como necesarios para eliminar toxinas acumuladas.', 'El hígado y los riñones son los encargados naturales de la desintoxicación. Los jugos suelen ser altos en azúcar libre.', 'Hábitos'),
('Los carbohidratos engordan', 'Son el combustible vital de tu cuerpo', 'Los carbohidratos son culpados del aumento de peso sin excepción.', 'Los carbohidratos complejos proveen energía esencial para el cerebro y los músculos. Lo que engorda es el exceso calórico.', 'Carbohidratos'),
('El jugo es fruta', 'La fibra se pierde, consume fruta entera', 'Mucha gente equipara un vaso de jugo con una pieza de fruta.', 'Al extraer el jugo se pierde casi toda la fibra, que es clave para la saciedad y el control glucémico.', 'Hábitos');
