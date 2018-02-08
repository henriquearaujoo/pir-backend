/* Create UUID Extension */
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

/* Pages */
INSERT INTO pirdb.public.pages (title_url, url_path)
VALUES 	('user-list',					'user-list'),
				('profile-list',			'profile-list'),
				('user', 							'user'),
				('user-edit', 				'user-edit'),
				('user-details', 			'user-details'),
				('page-list', 				'page-list'),
				('template-chapter', 	'template-chapter'),
				('chapter-dashboard',	'chapter-dashboard')
ON CONFLICT DO NOTHING;

/* Profile */
INSERT INTO pirdb.public.profile (status, created_at, description, title, updated_at, created_by, modified_by)
VALUES 	(TRUE, current_date, 'Administrator ROLE', 'Administrator', current_date, NULL, NULL)
ON CONFLICT DO NOTHING;

/* Page Profile Permissions */
INSERT INTO profile_pages (can_create, can_delete, can_view, can_update, page, profile)
(SELECT cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), pages.id, profile.id
FROM pirdb.public.profile, public.pages)
ON CONFLICT DO NOTHING;

/* User */
INSERT INTO "user" (email, full_name, dt_register, type)
VALUES ('example@example.com', 'Administrator', current_date, 'PFIS')
ON CONFLICT DO NOTHING;

/* Account */
INSERT INTO pirdb.public.account (id, credentials_expired, enabled, expired, locked, password, login, profile_id)
VALUES((SELECT id FROM "user" WHERE full_name = 'Administrator'),
FALSE, TRUE, FALSE, FALSE, '$2a$10$DHiwEO0otW0exjRhcsuhj.mJMUxZ2oAtQ/3SxVEXlETFd8WBn0Hqy', 'admin',
(SELECT id FROM pirdb.public.profile WHERE title = 'Administrator'))
ON CONFLICT DO NOTHING;