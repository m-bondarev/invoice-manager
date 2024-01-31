CREATE TABLE "ADMIN"."USERS"
(
    "ID"       NUMBER,
    "NAME"     VARCHAR2(4000 BYTE) COLLATE "USING_NLS_COMP",
    "SURENAME" VARCHAR2(4000 BYTE) COLLATE "USING_NLS_COMP",
    "EMAIL"    VARCHAR2(4000 BYTE) COLLATE "USING_NLS_COMP"
) DEFAULT COLLATION "USING_NLS_COMP";

CREATE UNIQUE INDEX "ADMIN"."USERS_PK" ON "ADMIN"."USERS" ("ID");

ALTER TABLE "ADMIN"."USERS"
    ADD CONSTRAINT "USERS_PK" PRIMARY KEY ("ID") USING INDEX  ENABLE;

CREATE TABLE "ADMIN"."INVOICES"
(
    "ID"          NUMBER,
    "USERID"      NUMBER,
    "DESCRIPTION" VARCHAR2(20 BYTE) COLLATE "USING_NLS_COMP",
    "STATUS"      NUMBER(3,0)
) DEFAULT COLLATION "USING_NLS_COMP";

CREATE UNIQUE INDEX "ADMIN"."INVOICES_PK" ON "ADMIN"."INVOICES" ("ID");

ALTER TABLE "ADMIN"."INVOICES"
    ADD CONSTRAINT "INVOICES_PK" PRIMARY KEY ("ID") USING INDEX  ENABLE;

ALTER TABLE "ADMIN"."INVOICES"
    ADD CONSTRAINT "NEW_FK_1" FOREIGN KEY ("USERID")
        REFERENCES "ADMIN"."USERS" ("ID") ON DELETE CASCADE ENABLE NOVALIDATE;

CREATE TABLE "ADMIN"."FILES"
(
    "ID"        NUMBER,
    "URL"       VARCHAR2(4000 BYTE) COLLATE "USING_NLS_COMP",
    "INVOICEID" NUMBER
) DEFAULT COLLATION "USING_NLS_COMP";

CREATE UNIQUE INDEX "ADMIN"."FILES_PK" ON "ADMIN"."FILES" ("ID");

ALTER TABLE "ADMIN"."FILES"
    ADD CONSTRAINT "FILES_PK" PRIMARY KEY ("ID") USING INDEX  ENABLE;

ALTER TABLE "ADMIN"."FILES"
    ADD CONSTRAINT "NEW_FK_2" FOREIGN KEY ("INVOICEID")
        REFERENCES "ADMIN"."INVOICES" ("ID") ON DELETE CASCADE ENABLE NOVALIDATE;

CREATE TABLE ADMIN."AUDIT_APP"
(
    "ID"           NUMBER,
    "EVENT_ID"     VARCHAR2 (225),
    "RESTART_DATE" TIMESTAMP
) DEFAULT COLLATION "USING_NLS_COMP";
