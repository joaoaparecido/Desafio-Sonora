DO $$
BEGIN
   IF NOT EXISTS (
      SELECT FROM pg_database WHERE datname = 'produto_db'
   ) THEN
      CREATE DATABASE produto_db;
END IF;
END
$$;