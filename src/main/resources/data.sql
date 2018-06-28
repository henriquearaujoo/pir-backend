/* Pages */


INSERT INTO pages (title_url, url_path)
VALUES
	('Lista de Capítulos', 'capitulos'),
	('Registro de Capítulos', 'capitulos/dashboard'),
	('Lista de Usuários', 'usuarios'),
	('Registro de Usuários', 'usuarios/registro'),
	('Detalhes do Usuário', 'usuarios/detalhes'),
	('Informaçoes do Agente', 'agente-dashboard'),
	('Registro do Agente', 'agente-dashboard/registro'),
	('Detalhes do Agente', 'agente-dashboard/detalhes'),
	('Localização do Agente', 'agente-dashboard/localizacao'),
	('Histórico de Visita', 'agente-visita'),
	('Lista de Visitas', 'agente-visita/historico'),
	('Desempenho do Agente', 'agente-desempenho'),
	('Mapa dos Agentes', 'agentes-mapa'),
	('Lista de Perfis', 'perfis'),
	('Lista de Responsáveis', 'responsaveis'),
	('Detalhes do Responsável', 'responsaveis/detalhes'),
	('Registro do Responsável', 'responsaveis/registro'),
	('Lista de Gestantes', 'gestantes'),
	('Detalhes da Gestante', 'gestantes/detalhes'),
	('Registro da Gestante', 'gestantes/registro'),
	('Lista de Crianças', 'criancas'),
	('Detalhes da Criança', 'criancas/detalhes'),
	('Registro da Criança', 'criancas/registro'),
	('Lista de Comunidades', 'comunidades'),
	('Registro da Comunidade', 'comunidades/registro'),
	('Detalhes da Comunidade', 'comunidades/detalhes'),
	('Lista de Formulários', 'formularios'),
	('Registro de Formulário', 'formularios/registro'),
	('Detalhes do Formulário', 'formularios/detalhes'),
	('Dashboard', 'dashboard'),
	('Registro de Capítulos', 'capitulos/registro'),
	('Relatório', 'relatorios'),
	('Construtor de Formulários', 'formularios/construtor'),
	('Registro de Formulários', 'formularios/construtor/registro')
ON CONFLICT DO NOTHING;

/* User */
INSERT INTO "user" (email, name, register_date)
VALUES ('example@example.com', 'Administrator', current_date)
ON CONFLICT DO NOTHING;

/* Profile */
INSERT INTO profile (id, status, created_at, description, title, type, updated_at, created_by, modified_by)
VALUES (1, TRUE, current_date, 'Administrator ROLE', 'Administrator', 'ADMIN', current_date, (SELECT id FROM "user" WHERE name = 'Administrator'), (SELECT id FROM "user" WHERE name = 'Administrator'))
ON CONFLICT DO NOTHING;

/* Page Profile Permissions */
INSERT INTO profile_pages (can_create, can_delete, can_read, can_update, page_id, profile_id)
	(SELECT cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), cast(TRUE AS BOOLEAN), pages.id, profile.id
	 FROM public.profile, public.pages)
ON CONFLICT DO NOTHING;

/* Account */
INSERT INTO account (user_id, credentials_non_expired, enabled, account_non_expired, account_non_locked, password, login, profile_id)
VALUES((SELECT id FROM "user" WHERE name = 'Administrator'), TRUE, TRUE, TRUE, TRUE, '$2a$10$DHiwEO0otW0exjRhcsuhj.mJMUxZ2oAtQ/3SxVEXlETFd8WBn0Hqy', 'admin', (SELECT id FROM public.profile WHERE title = 'Administrator'))
ON CONFLICT DO NOTHING;

INSERT INTO states (id, uf_abbr, name)
VALUES (1, 'AC', 'Acre'), (2, 'AL', 'Alagoas'), (3, 'AM', 'Amazonas'), (4, 'AP', 'Amapá'), (5, 'BA', 'Bahia'),
(6, 'CE', 'Ceará'), (7, 'DF', 'Distrito Federal'), (8, 'ES', 'Espirito Santo'), (9, 'GO', 'Goiás'), (10, 'MA', 'Maranhão'),
(11, 'MG', 'Minas Gerais'), (12, 'MS', 'Mato Grosso do Sul'), (13, 'MT', 'Mato Grosso'), (14, 'PA', 'Pará'),
(15, 'PB', 'Paraíba'), (16, 'PE', 'Pernambuco'), (17, 'PI', 'Piauí'), (18, 'PR', 'Paraná'), (19, 'RJ', 'Rio de Janeiro'),
(20, 'RN', 'Rio Grande do Norte'), (21, 'RO', 'Rondônia'), (22, 'RR', 'Roraima'), (23, 'RS', 'Rio Grande do Sul'),
(24, 'SC', 'Santa Catarina'), (25, 'SE', 'Sergipe'), (26, 'SP', 'São Paulo'), (27, 'TO', 'Tocantins')
ON CONFLICT DO NOTHING;

INSERT INTO cities (id, name, state_id_fk)
VALUES (238, 'Manacapuru', 3), (239, 'Manaquirí', 3), (240, 'Manaus', 3), (241, 'Manicore', 3), (242, 'Maraa', 3),
(243, 'Massauari', 3), (244, 'Maues', 3), (245, 'Mocambo', 3)
ON CONFLICT DO NOTHING;