
CREATE TABLE jettysessionids(
     id VARCHAR(120) NOT NULL,
     PRIMARY KEY (id)
);

CREATE TABLE jettysessions (
        rowId VARCHAR(120) NOT NULL,
        sessionId VARCHAR(120)  ,
        contextPath VARCHAR(60) ,
        virtualHost VARCHAR(60) ,
        lastNode VARCHAR(60) ,
        accessTime bigint,
        lastAccessTime bigint,
        createTime bigint,
        cookieTime bigint,
        lastSavedTime bigint,
        expiryTime bigint,
        maxInterval bigint,
        map varbinary(max),
        PRIMARY KEY (rowId)
);

create index idx_JettySessions_expiry on jettysessions (expiryTime);

create index idx_JettySessions_session on jettysessions (sessionId, contextPath);
