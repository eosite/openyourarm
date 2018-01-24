insert into sys_tree (id, parent, name, nodedesc, sort, code, deleteflag, locked) values (31, 4, '部门层级', '', 31, 'DeptLevel', 0, 0);

insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext11) values (311, 31, 'A001', '厅', 99, null, 0, 1);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext11) values (312, 31, 'B002', '总队(处)', 98, null, 0, 2);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext11) values (313, 31, 'C003', '局', 97, null, 0, 3);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext11) values (314, 31, 'D004', '科', 96, null, 0, 4);
insert into sys_dictory (id, typeId, code, name, sort, dictDesc, blocked, ext11) values (315, 31, 'E005', '班组', 95, null, 0, 5);