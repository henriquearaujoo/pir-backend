/* Pages */
INSERT INTO pirdb.public.pages (title_url, url_path)
VALUES
	('user-list', 'user-list'),
	('profile-list', 'profile-list'),
	('user', 'user'),
	('user-edit', 'user-edit'),
	('user-details', 'user-details'),
	('page-list', 'page-list'),
	('template-chapter', 'template-chapter'),
	('chapter-dashboard',	'chapter-dashboard'),
	('community-list', 'community-list'),
	('community', 'community'),
	('child-list', 'child-list'),
	('responsible-list', 'responsible-list'),
	('responsible', 'responsible'),
	('child', 'child'),
	('community-details',	'community-details'),
	('responsible-details',	'responsible-details'),
	('child-details',	'child-details'),
	('pregnant', 'pregnant'),
	('pregnant-list', 'pregnant-list'),
	('child', 'child'),
	('pregnant-details', 'pregnant-details')
ON CONFLICT DO NOTHING;

/* User */
INSERT INTO "user" (email, name, register_date)
VALUES ('example@example.com', 'Administrator', current_date)
ON CONFLICT DO NOTHING;

/* Profile */
INSERT INTO pirdb.public.profile (id, status, created_at, description, title, updated_at, created_by, modified_by)
VALUES (1, TRUE, current_date, 'Administrator ROLE', 'Administrator', current_date, (SELECT id FROM "user" WHERE name = 'Administrator'), (SELECT id FROM "user" WHERE name = 'Administrator'))
ON CONFLICT DO NOTHING;

/* Page Profile Permissions */
INSERT INTO profile_pages (can_create, can_delete, can_read, can_update, page_id, profile_id)
	(SELECT cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), pages.id, profile.id
	 FROM pirdb.public.profile, public.pages)
ON CONFLICT DO NOTHING;

/* Account */
INSERT INTO pirdb.public.account (id, credentials_expired, enabled, expired, locked, password, login, profile_id)
VALUES((SELECT id FROM "user" WHERE name = 'Administrator'), FALSE, TRUE, FALSE, FALSE, '$2a$10$DHiwEO0otW0exjRhcsuhj.mJMUxZ2oAtQ/3SxVEXlETFd8WBn0Hqy', 'admin', (SELECT id FROM pirdb.public.profile WHERE title = 'Administrator'))
ON CONFLICT DO NOTHING;

INSERT INTO public.state (id, uf_abbr, name)
VALUES (1, 'AC', 'Acre'), (2, 'AL', 'Alagoas'), (3, 'AM', 'Amazonas'), (4, 'AP', 'Amapá'), (5, 'BA', 'Bahia'),
(6, 'CE', 'Ceará'), (7, 'DF', 'Distrito Federal'), (8, 'ES', 'Espirito Santo'), (9, 'GO', 'Goiás'), (10, 'MA', 'Maranhão'),
(11, 'MG', 'Minas Gerais'), (12, 'MS', 'Mato Grosso do Sul'), (13, 'MT', 'Mato Grosso'), (14, 'PA', 'Pará'),
(15, 'PB', 'Paraíba'), (16, 'PE', 'Pernambuco'), (17, 'PI', 'Piauí'), (18, 'PR', 'Paraná'), (19, 'RJ', 'Rio de Janeiro'),
(20, 'RN', 'Rio Grande do Norte'), (21, 'RO', 'Rondônia'), (22, 'RR', 'Roraima'), (23, 'RS', 'Rio Grande do Sul'),
(24, 'SC', 'Santa Catarina'), (25, 'SE', 'Sergipe'), (26, 'SP', 'São Paulo'), (27, 'TO', 'Tocantins')
ON CONFLICT DO NOTHING;

INSERT INTO public.city (id, name, state_id_fk)
VALUES (238, 'Manacapuru', 3), (239, 'Manaquirí', 3), (240, 'Manaus', 3), (241, 'Manicore', 3), (242, 'Maraa', 3),
(243, 'Massauari', 3), (244, 'Maues', 3), (245, 'Mocambo', 3)
ON CONFLICT DO NOTHING;