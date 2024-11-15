-- DROP SCHEMA IF EXISTS public CASCADE;
-- CREATE SCHEMA IF NOT EXISTS public;
SELECT * FROM "users-projects" inner join public.projects p on p.id = "users-projects".project_id inner join public.users u on u.id = "users-projects".user_id;