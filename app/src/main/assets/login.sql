BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "login" (
	"custID"	INTEGER,
	"stuID"	INTEGER,
	"name"	TEXT,
	"email"	TEXT,
	"phone"	TEXT,
	"department"	TEXT,
	PRIMARY KEY("custID")
);
INSERT INTO "login" VALUES (1,2216110017,'유호정','i19162013@gmail.com','01050912538','소프트웨어융합과');
COMMIT;
