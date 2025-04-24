ALTER TABLE course ADD COLUMN duration INT NOT NULL;

-- P3966
UPDATE course SET duration = 0 WHERE duration IS NULL;
