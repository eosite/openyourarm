insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1101, 3, '��������', '��������', 30, 'user_task');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1102, 1101, '��������', '��������', 30, 'pending_task');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1103, 1101, '�Ѱ�����', '�Ѱ�����', 30, 'worked_task');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1104, 1101, '�˻�����', '�˻�����', 30, 'back_task');
insert into sys_tree (id, parent, name, nodedesc, sort, code) values (1105, 1101, 'ȫ������', 'ȫ������', 30, 'all_task');

insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (1101, '��������', '', '', 15, 2, 1101);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (1102, '��������', '', '/mx/bpmn/task/taskList?taskType=RN', 15, 2, 1102);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (1103, '�Ѱ�����', '', '/mx/bpmn/task/taskList?taskType=WD', 15, 2, 1103);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (1104, '�˻�����', '', '/mx/bpmn/task/taskList?taskType=FB', 15, 2, 1104);
insert into sys_application (id, name, appdesc, url, sort, showmenu, nodeid) values (1105, 'ȫ������', '', '/mx/bpmn/task/taskList?taskType=ALL', 15, 2, 1105);
