MERGE
INTO    role d
USING   (
        SELECT  '1' as id, 'ADMIN' as role FROM dual
        ) s
ON      (s.id = d.id)
WHEN NOT MATCHED THEN
INSERT VALUES  (s.id, s.role)
WHEN MATCHED THEN
UPDATE
SET d.role = s.role;

MERGE
INTO    role d
USING   (
        SELECT  '2' as id, 'USER' as role FROM dual
        ) s
ON      (s.id = d.id)
WHEN NOT MATCHED THEN
INSERT VALUES  (s.id, s.role)
WHEN MATCHED THEN
UPDATE
SET d.role = s.role;