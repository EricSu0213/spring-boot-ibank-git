MERGE INTO role d
USING   (
        SELECT  '1' as id, 'ADMIN' as role FROM dual
        ) s
ON      (s.id = d.id)
WHEN NOT MATCHED THEN
INSERT VALUES  (s.id, s.role)
WHEN MATCHED THEN
UPDATE
SET d.role = s.role;

MERGE INTO role d
USING   (
        SELECT  '2' as id, 'USER' as role FROM dual
        ) s
ON      (s.id = d.id)
WHEN NOT MATCHED THEN
INSERT VALUES  (s.id, s.role)
WHEN MATCHED THEN
UPDATE
SET d.role = s.role;

MERGE INTO ACCOUNT a
USING (
	SELECT 'admin' as NAME, 'admin' as EMAIL, '$2a$10$2RlHs3ewyGXs1vEbn8Cj.e8oaI.qtD12V73.P2c0epfjamMB1RmS6' as PASSWORD, 1 as ACTIVE FROM dual
) s
ON (s.EMAIL = a.EMAIL)
WHEN NOT MATCHED THEN
INSERT (a.ID, a.NAME, a.EMAIL, a.PASSWORD, a.CREATED_DATE, a.ACTIVE)
VALUES (ACCOUNT_SEQ.nextval, s.NAME, s.EMAIL, s.PASSWORD, sysdate, s.ACTIVE);

MERGE INTO ACCOUNT_ROLE ar
USING (
	SELECT * FROM ACCOUNT WHERE EMAIL = 'admin'
) s
ON (s.ID = ar.ACCOUNT_ID)
WHEN NOT MATCHED THEN
INSERT (ar.ACCOUNT_ID, ar.ROLE_ID)
VALUES (s.ID, 1);