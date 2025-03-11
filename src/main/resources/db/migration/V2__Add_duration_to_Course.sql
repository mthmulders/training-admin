ALTER TABLE course
ADD COLUMN duration INT;

UPDATE course
SET duration = 0;

ALTER TABLE course
ALTER COLUMN duration SET NOT NULL;
