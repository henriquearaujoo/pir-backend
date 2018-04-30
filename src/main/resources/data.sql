/* Pages */


INSERT INTO pages (id, title_url, url_path, uuid)
VALUES
	(1, 'user-list', 'user-list', '5b1910ed-e296-48a6-b0a4-46db948543c3'),
	(2, 'profile-list', 'profile-list', '35051d48-afcd-4c88-b9e9-048c881bd33a'),
	(3, 'user-list/user', 'user-list/user', '0e87aaa2-9d35-47bc-aae5-ae2c96fc3fa5'),
	(5, 'user-list/details', 'user-list/details', '5bb6e107-88d0-4aaf-b40b-9e084e5ce75c'),
	(7, 'chapter', 'chapter', 'ce0cac52-d653-40ea-82ea-afec4f5795e2'),
	(8, 'chapter/chapter-dashboard', 'chapter/chapter-dashboard', '483e842c-71ea-4b73-bb04-86068af54231'),
	(9, 'community-list', 'community-list', 'd92be784-8204-4577-9c86-467ae6ebf1ad'),
	(10, 'community-list/community', 'community-list/community', 'b2bae257-56ec-4afa-93cc-bb520938c829'),
	(11, 'child-list', 'child-list', '6ec96585-3a6d-4302-a6c8-0df7f7d13ca4'),
	(12, 'responsible-list', 'responsible-list', '930c665b-4338-46fa-81d5-f03361ef2923'),
	(13, 'responsible-list/responsible', 'responsible-list/responsible', '33a28bd5-03ea-41ef-8a53-19717c505c24'),
	(14, 'child-list/child', 'child-list/child', '28896e54-dbda-41a1-a880-3b3a65c8ff4b'),
	(15, 'community-list/details', 'community-list/details', '46740ac2-ccbb-4598-9dc8-0799fb49da31'),
	(16, 'responsible-list/details', 'responsible-list/details', '50ea9f95-424f-4927-9626-616791d1afe3'),
	(17, 'child-list/details', 'child-list/details', '00550f51-22dc-4b35-ab98-9e0ac0528aa2'),
	(18, 'pregnant-list/pregnant', 'pregnant-list/pregnant', '433b2d51-29dd-4e56-b89c-88e213f52f84'),
	(19, 'pregnant-list', 'pregnant-list', '9f2b3f42-db05-4be4-8ea6-5bc55742bac3'),
	(21, 'pregnant-list/details', 'pregnant-list/details', '3f29fb12-7c16-46c0-8192-300b94039a2d'),
	(22, 'agent-information', 'agent-information', '64d6ef70-d57f-48f4-b914-331b31b633e8'),
	(23, 'agent-information/agent', 'agent-information/agent', 'd9860e12-feee-4ba9-8d15-03b5c845f5ba'),
	(24, 'agent-information/details', 'agent-information/details', '1c49c065-6c4e-4740-93b2-549cae71e889'),
	(25, 'agent-information/location', 'agent-information/location', '81bf2b09-cda8-4a42-8d2a-a256630c85de'),
	(26, 'agent-information/map', 'agent-information/map', 'c94c22f2-2212-43a4-8165-f73ffaa39aec'),
	(27, 'agent-information/visit-historic', 'agent-information/visit-historic', 'b26fa846-6ce7-48c4-bd5c-57f6ee86b5cf'),
	(28, 'agent-information/family-list', 'agent-information/family-list', '78fab70f-0f0f-4708-9682-a38f598b310b'),
	(29, 'agent-information/visit-historic-list', 'agent-information/visit-historic-list', 'dc6707d9-cec0-4cf7-9bb9-39a5f551dccf'),
	(30, 'form-template-list/form', 'form-template-list/form', 'd4b76e4c-e81f-4be5-aacd-34ec0f13568e'),
	(31, 'form-template-list', 'form-template-list', 'f1e3df5f-9025-4045-bef5-d5d87572488b'),
	(32, 'form-template-list/details', 'form-template-list/details', '75dcaa3a-5935-4e2e-ab5d-0e0aef938dde'),
	(33, 'chapter-dashboard', 'chapter-dashboard', 'c1acb643-5a0a-45b7-b485-75590fa11027'),
	(34, 'dashboard', 'dashboard', '87ac8fa9-cf3c-4e55-b446-e15eaab54e56')
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
INSERT INTO account (id, credentials_expired, enabled, expired, locked, password, login, profile_id)
VALUES((SELECT id FROM "user" WHERE name = 'Administrator'), FALSE, TRUE, FALSE, FALSE, '$2a$10$DHiwEO0otW0exjRhcsuhj.mJMUxZ2oAtQ/3SxVEXlETFd8WBn0Hqy', 'admin', (SELECT id FROM public.profile WHERE title = 'Administrator'))
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