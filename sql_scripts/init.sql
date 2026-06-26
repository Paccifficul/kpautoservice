CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('client', 'employee')),
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS clients (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    registration_date DATE NOT NULL DEFAULT CURRENT_DATE
);

CREATE TABLE IF NOT EXISTS employees (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    full_name VARCHAR(100) NOT NULL,
    position VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS service_categories (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS services (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(100) NOT NULL,
    category_id UUID NOT NULL REFERENCES service_categories(id) ON DELETE CASCADE,
    avg_duration_min SMALLINT NOT NULL CHECK (avg_duration_min > 0),
    price NUMERIC(10,2) NOT NULL CHECK (price >= 0),
    description TEXT
);

CREATE TABLE IF NOT EXISTS car_brands (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS car_models (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    brand_id UUID NOT NULL REFERENCES car_brands(id) ON DELETE RESTRICT,
    name VARCHAR(100) NOT NULL,
    body_type VARCHAR(20) NOT NULL,
    production_country VARCHAR(50),
    CONSTRAINT uk_car_models_brand_name UNIQUE (brand_id, name)
);

CREATE TABLE IF NOT EXISTS car_specifications (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    model_id UUID NOT NULL REFERENCES car_models(id) ON DELETE RESTRICT,
    year SMALLINT NOT NULL CHECK (year BETWEEN 1950 AND EXTRACT(YEAR FROM NOW()) + 1),
    engine_type VARCHAR(20) NOT NULL CHECK (engine_type IN ('gasoline','diesel','hybrid','electric')),
    engine_volume NUMERIC(3,1) NOT NULL CHECK (engine_volume BETWEEN 0.6 AND 8.0),
    engine_power_hp SMALLINT NOT NULL CHECK (engine_power_hp BETWEEN 30 AND 800),
    transmission_type VARCHAR(15) NOT NULL CHECK (transmission_type IN ('manual','automatic','cvt','dual_clutch')),
    drive_type VARCHAR(10) NOT NULL CHECK (drive_type IN ('FWD','RWD','AWD')),
    fuel_type VARCHAR(15) NOT NULL CHECK (fuel_type IN ('AI-92','AI-95','AI-98', 'AI-100','diesel','electric')),
    weight_kg INTEGER CHECK (weight_kg > 0),
    CONSTRAINT uk_car_specifications_natural_key UNIQUE (
        model_id, year, engine_type, engine_volume, engine_power_hp,
        transmission_type, drive_type, fuel_type
    )
);

CREATE TABLE IF NOT EXISTS cars (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    vin CHAR(17) NOT NULL UNIQUE CHECK (vin ~ '^[A-HJ-NPR-Z0-9]{17}$'),
    specification_id UUID NOT NULL REFERENCES car_specifications(id) ON DELETE RESTRICT,
    mileage BIGINT NOT NULL CHECK (mileage >= 0),
    color VARCHAR(30),
    client_id UUID REFERENCES clients(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS service_orders (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    car_id UUID NOT NULL REFERENCES cars(id) ON DELETE RESTRICT,
    employee_id UUID REFERENCES employees(id) ON DELETE SET NULL,
    status VARCHAR(15) NOT NULL CHECK (status IN ('scheduled','in_progress','completed','cancelled')),
    scheduled_at TIMESTAMP NOT NULL,
    started_at TIMESTAMP,
    planned_end TIMESTAMP,
    actual_end TIMESTAMP,
    notes TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS order_services (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    order_id UUID NOT NULL REFERENCES service_orders(id) ON DELETE CASCADE,
    service_id UUID NOT NULL REFERENCES services(id) ON DELETE RESTRICT,
    duration_actual_min SMALLINT CHECK (duration_actual_min >= 0),
    price_charged NUMERIC(10,2) NOT NULL,
    notes TEXT,
    CONSTRAINT uk_order_services_order_service UNIQUE (order_id, service_id)
);

CREATE TABLE IF NOT EXISTS audit_log (
    id BIGSERIAL PRIMARY KEY,
    table_name VARCHAR(50) NOT NULL,
    operation VARCHAR(10) NOT NULL CHECK (operation IN ('INSERT','UPDATE','DELETE')),
    user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    old_data JSONB,
    new_data JSONB,
    timestamp TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS system_exceptions_log (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL DEFAULT NOW(),
    source VARCHAR(30) NOT NULL,
    operation VARCHAR(100),
    user_id UUID REFERENCES users(id) ON DELETE SET NULL,
    error_code VARCHAR(50),
    error_message TEXT NOT NULL,
    stack_trace TEXT,
    payload JSONB,
    handled BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_cars_updated_at ON cars;
CREATE TRIGGER trigger_cars_updated_at
    BEFORE UPDATE ON cars
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

CREATE OR REPLACE FUNCTION audit_trigger_function()
RETURNS TRIGGER AS $$
DECLARE
    user_id_val UUID;
BEGIN
    BEGIN
        user_id_val := NULLIF(current_setting('app.user_id', TRUE), '')::UUID;
    EXCEPTION WHEN undefined_object OR invalid_text_representation THEN
        user_id_val := NULL;
    END;

    IF (TG_OP = 'INSERT') THEN
        INSERT INTO audit_log (table_name, operation, user_id, old_data, new_data)
        VALUES (TG_TABLE_NAME, 'INSERT', user_id_val, NULL, row_to_json(NEW));
        RETURN NEW;
    ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO audit_log (table_name, operation, user_id, old_data, new_data)
        VALUES (TG_TABLE_NAME, 'UPDATE', user_id_val, row_to_json(OLD), row_to_json(NEW));
        RETURN NEW;
    ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO audit_log (table_name, operation, user_id, old_data, new_data)
        VALUES (TG_TABLE_NAME, 'DELETE', user_id_val, row_to_json(OLD), NULL);
        RETURN OLD;
    END IF;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS audit_cars_trigger ON cars;
CREATE TRIGGER audit_cars_trigger
    AFTER INSERT OR UPDATE OR DELETE ON cars
    FOR EACH ROW
    EXECUTE FUNCTION audit_trigger_function();

DROP TRIGGER IF EXISTS audit_service_orders_trigger ON service_orders;
CREATE TRIGGER audit_service_orders_trigger
    AFTER INSERT OR UPDATE OR DELETE ON service_orders
    FOR EACH ROW
    EXECUTE FUNCTION audit_trigger_function();

DROP TRIGGER IF EXISTS audit_users_trigger ON users;
CREATE TRIGGER audit_users_trigger
    AFTER INSERT OR UPDATE OR DELETE ON users
    FOR EACH ROW
    EXECUTE FUNCTION audit_trigger_function();

CREATE OR REPLACE VIEW service_stats_view AS
SELECT
    s.id,
    s.name AS service_name,
    sc.name AS category_name,
    COUNT(os.id) AS usage_count,
    COALESCE(AVG(os.duration_actual_min), 0) AS avg_duration,
    COALESCE(SUM(os.price_charged), 0) AS total_revenue
FROM services s
JOIN service_categories sc ON s.category_id = sc.id
LEFT JOIN order_services os ON s.id = os.service_id
GROUP BY s.id, s.name, sc.name;

CREATE OR REPLACE VIEW car_orders_view AS
SELECT
    COALESCE(so.id, c.id) AS id,
    c.vin,
    cb.name AS brand,
    cm.name AS model,
    cs.year,
    cl.full_name AS client_name,
    cl.phone AS client_phone,
    so.id AS order_id,
    so.status AS order_status,
    so.scheduled_at,
    COUNT(os.id)::integer AS total_services,
    COALESCE(SUM(os.price_charged), 0) AS total_cost
FROM cars c
JOIN car_specifications cs ON c.specification_id = cs.id
JOIN car_models cm ON cs.model_id = cm.id
JOIN car_brands cb ON cm.brand_id = cb.id
LEFT JOIN clients cl ON c.client_id = cl.id
LEFT JOIN service_orders so ON c.id = so.car_id
LEFT JOIN order_services os ON so.id = os.order_id
GROUP BY
    c.id, c.vin, cb.name, cm.name, cs.year, cl.full_name, cl.phone,
    so.id, so.status, so.scheduled_at;

DROP INDEX IF EXISTS idx_cars_vin;
DROP INDEX IF EXISTS idx_users_role;
DROP INDEX IF EXISTS idx_cars_brand_model;
DROP INDEX IF EXISTS idx_cars_brand_year;
DROP INDEX IF EXISTS idx_cars_year;

CREATE INDEX IF NOT EXISTS idx_clients_user_id ON clients (user_id);
CREATE INDEX IF NOT EXISTS idx_employees_user_id ON employees (user_id);
CREATE INDEX IF NOT EXISTS idx_services_category_id ON services (category_id);

CREATE INDEX IF NOT EXISTS idx_car_models_brand_id ON car_models (brand_id);
CREATE INDEX IF NOT EXISTS idx_car_specifications_model_id ON car_specifications (model_id);
CREATE INDEX IF NOT EXISTS idx_car_specifications_year ON car_specifications (year);
CREATE INDEX IF NOT EXISTS idx_cars_specification_id ON cars (specification_id);
CREATE INDEX IF NOT EXISTS idx_cars_client_id ON cars (client_id);

CREATE INDEX IF NOT EXISTS idx_service_orders_status ON service_orders (status);
CREATE INDEX IF NOT EXISTS idx_service_orders_scheduled_at ON service_orders (scheduled_at);
CREATE INDEX IF NOT EXISTS idx_service_orders_car_id ON service_orders (car_id);
CREATE INDEX IF NOT EXISTS idx_service_orders_employee_id ON service_orders (employee_id);
CREATE INDEX IF NOT EXISTS idx_service_orders_status_scheduled ON service_orders (status, scheduled_at);
CREATE INDEX IF NOT EXISTS idx_service_orders_car_status_scheduled ON service_orders (car_id, status, scheduled_at);

CREATE INDEX IF NOT EXISTS idx_order_services_order_id ON order_services (order_id);
CREATE INDEX IF NOT EXISTS idx_order_services_service_id ON order_services (service_id);
CREATE INDEX IF NOT EXISTS idx_order_services_service_order_id ON order_services (service_id, order_id);

CREATE INDEX IF NOT EXISTS idx_audit_log_timestamp ON audit_log (timestamp);
CREATE INDEX IF NOT EXISTS idx_audit_log_user_timestamp ON audit_log (user_id, timestamp);
CREATE INDEX IF NOT EXISTS idx_audit_log_table_timestamp ON audit_log (table_name, timestamp);
CREATE INDEX IF NOT EXISTS idx_system_exceptions_timestamp ON system_exceptions_log (timestamp);
CREATE INDEX IF NOT EXISTS idx_system_exceptions_handled_timestamp ON system_exceptions_log (handled, timestamp);
