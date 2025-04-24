ALTER TABLE course ADD COLUMN duration INT;

UPDATE course SET duration = 0 WHERE duration IS NULL;

ALTER TABLE course ALTER COLUMN duration SET NOT NULL;
