DROP TABLE IF EXISTS products;
CREATE TABLE products(
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    special_id TEXT NOT NULL,
	title TEXT NOT NULL,
	source VARCHAR(10) CHECK (source IN ('FLIPKART','AMAZON')) NOT NULL,
	product_url TEXT NOT NULL,
	image_url TEXT NOT NULL,
	hit_interval TEXT NOT NULL,
	hit_interval_type VARCHAR(8) CHECK(hit_interval_type IN ('SECOND','MINUTE','HOUR','DAY','WEEK','MONTH','YEAR')) NOT NULL,
	hit_interval_in_millis TEXT NOT NULL,
	last_hit_in_millis TEXT NOT NULL,
	next_hit_in_millis TEXT NOT NULL,
	is_hit_active INTEGER CHECK (is_hit_active IN (0,1)) NOT NULL,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

DROP TABLE IF EXISTS price_histories;
CREATE TABLE price_histories(
	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
	product_id INTEGER NOT NULL,
	price INTEGER NOT NULL,
	created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (product_id) REFERENCES products(id) ON UPDATE CASCADE ON DELETE CASCADE
);