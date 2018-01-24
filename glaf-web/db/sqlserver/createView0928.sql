DROP VIEW DEPT_USER_TREE;
CREATE VIEW DEPT_USER_TREE (ID, PARENT, TREEID, CODE, NAME, DEPTLEVEL, SORT, TYPE) AS (
        SELECT
            c.id                    AS ID,
            c.PARENT                AS PARENT,
            c.TREEID                AS TREEID,
            b.userId                AS CODE,
            b.UserName              AS NAME,
            a.DEPTLEVEL             AS DEPTLEVEL,
            a.SORT                  AS SORT,
            '1'                     AS TYPE
        FROM
            sys_tree c,
            sys_department a,
            UserInfo b
        WHERE
            a.ID=b.depid
        AND c.ID=a.nodeid
        UNION
        SELECT
            a.ID,
            a.PARENT,
            a.TREEID,
            b.CODE,
            b.NAME,
            b.DEPTLEVEL,
            b.SORT,
            '0' AS TYPE
        FROM
            sys_tree a,
            sys_department b
        WHERE
            a.ID=b.nodeid
    );
DROP VIEW V_DEPARTMENT;
CREATE VIEW V_DEPARTMENT (ID, CODE, CODE2, CREATEBY, CREATETIME, DEPTDESC, FINCODE, DEPTLEVEL, NAME, DEPTNO, NODEID, SORT, STATUS, UPDATEBY, UPDATEDATE, DELETEFLAG, TID, PTID, TREEID) AS (
        SELECT
            a.ID   AS ID,
            a.CODE AS CODE,
            a.CODE2,
            a.CREATEBY,
            a.CREATETIME,
            a.DEPTDESC,
            a.FINCODE,
            a.DEPTLEVEL,
            a.NAME,
            a.DEPTNO,
            a.NODEID,
            a.SORT,
            a.STATUS,
            a.UPDATEBY,
            a.UPDATEDATE,
            a.DELETEFLAG,
            b.ID     AS tId,
            b.PARENT AS ptId,
            b.TREEID
        FROM
            sys_department a,
            sys_tree b
        WHERE
            a.NODEID=b.ID
    );
DROP VIEW V_USERDEPARTMENT;
CREATE VIEW V_USERDEPARTMENT (USERID, USERNAME, DEPID, DEPTNO, DEPTLEVEL, NAME) AS (
        SELECT
            USERID,
            USERNAME,
            b.ID AS DEPID,
            b.DEPTNO,
	    b.DEPTLEVEL,
            b.NAME AS NAME
        FROM
            UserInfo a,
            SYS_DEPARTMENT b
        WHERE
            a.depid=b.ID
    );
DROP VIEW V_USERROLE;
CREATE VIEW V_USERROLE (USERID, USERNAME, ROLEID, ROLECODE, ROLENAME) AS (
        SELECT
            u.USERID,
            u.USERNAME,
            r.id   AS ROLEID,
            r.CODE AS ROLECODE,
            r.ROLENAME
        FROM
            net_role r
        INNER JOIN
            userrole ur
        ON
            r.ID = ur.ROLEID
        INNER JOIN
            UserInfo u
        ON
            ur.USERID = u.USERID
        WHERE
            r.CODE IS NOT NULL
    );
