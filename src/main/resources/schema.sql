CREATE TABLE IF NOT EXISTS tasklist (
id VARCHAR(8) PRIMARY KEY,
task VARCHAR(256),
deadline VARCHAR(10),
done BOOLEAN
);

CREATE TABLE IF NOT EXISTS Account (
userId VARCHAR(8) PRIMARY KEY,
userName VARCHAR(256),
userAge VARCHAR(256)
);
