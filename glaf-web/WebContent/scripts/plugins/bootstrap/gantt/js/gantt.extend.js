//重写了甘特图点击展开关闭图标的事件
GridEditor.prototype.bindRowExpandEvents = function(task, taskRow) {
    var self = this;
    //expand collapse
    taskRow.find(".exp-controller").click(function(e) {
        e.preventDefault();
        e.stopPropagation();
        //expand?
        var el = $(this);
        var taskId = el.closest("[taskid]").attr("taskid");
        var task = self.master.getTask(taskId);
        var opts = self.master.options;
        var sortBy = opts.sortBy;
        var idValue;
        task && task.row && (idValue = task.row[opts.keys.idKey]);
        if (opts.isSync && !el.hasClass('isSync') && idValue) {
            el.addClass('loading');
            var read = $.extend(true, {}, opts.ajax.read);
            read.data.id = idValue;
            read.data = JSON.stringify(read.data);
            read.success = function(ret) {
                if (ret && ret instanceof Object) {
                	ret.data.sort(function(a, b){
						var rst = (a[sortBy] || 0) - (b[sortBy] || 0);
						return false ? -rst : rst;
					})
                    //甘特图数据转换为模板数据
                    var tasksJson = IframeGantt.convertDatas(IframeGantt.initDatas(ret, opts, task.level), opts) /*eval("(" + tasks + ")")*/ ;
                    if (tasksJson.tasks.length) {
                        self.master.syncLoadChildTasks(tasksJson.tasks, task, true);
                        self.master.syncUpdateLinks();
                    } else {
                        el.removeClass('expcoll');
                    }
                    el.addClass('isSync');
                    el.removeClass('loading');

                    var descs = task.getDescendant();
                    el.toggleClass('exp');
                    task.collapsed = !el.is(".exp");
                    var collapsedDescendant = self.master.getCollapsedDescendant();

                    if (el.is(".exp")) {
                        for (var i = 0; i < descs.length; i++) {
                            var childTask = descs[i];
                            if (collapsedDescendant.indexOf(childTask) >= 0) continue;
                            childTask.rowElement.show();
                        }

                    } else {
                        for (var i = 0; i < descs.length; i++)
                            descs[i].rowElement.hide();
                    }
                    self.master.gantt.refreshGantt();
                }
            }
            read.complete = function(XHR, TS) {}
            $.ajax(read);
        } else {

            var descs = task.getDescendant();
            el.toggleClass('exp');
            task.collapsed = !el.is(".exp");
            var collapsedDescendant = self.master.getCollapsedDescendant();

            if (el.is(".exp")) {
                for (var i = 0; i < descs.length; i++) {
                    var childTask = descs[i];
                    if (collapsedDescendant.indexOf(childTask) >= 0) continue;
                    childTask.rowElement.show();
                }

            } else {
                for (var i = 0; i < descs.length; i++)
                    descs[i].rowElement.hide();
            }
            self.master.gantt.refreshGantt();
        }


    });
}


//增加异步加载下级节点方法
GanttMaster.prototype.syncLoadChildTasks = function(tasks, selectedRow, isSync) {
    var self = this;
    selectedRow && (self.currentTask = selectedRow);
    for (var i = tasks.length - 1; i >= 0; i--) {
        tasks[i]['parentTask'] = selectedRow;
        self.addBelowCurrentTask(tasks[i], isSync);
    }
    /*$.each(tasks, function() {
        this['parentTask'] = selectedRow;
        Object.defineProperty(this, "parentTask", {
            enumerable: false
        });
        self.addBelowCurrentTask(this, isSync);
    })*/
};

/**
 * 依赖更改为 通过 index parent
 * @param  {[type]} task [description]
 * @return {[type]}      [description]
 */
GanttMaster.prototype.updateLinks = function(task) {
    //console.debug("updateLinks",task);
    //var prof= new Profiler("gm_updateLinks");

    // defines isLoop function
    function isLoop(task, target, visited) {
        //var prof= new Profiler("gm_isLoop");
        //console.debug("isLoop :"+task.name+" - "+target.name);
        if (target == task) {
            return true;
        }

        var sups = task.getSuperiors();

        //my parent' superiors are my superiors too
        var p = task.getParent();
        while (p) {
            sups = sups.concat(p.getSuperiors());
            p = p.getParent();
        }

        //my children superiors are my superiors too
        var chs = task.getChildren();
        for (var i = 0; i < chs.length; i++) {
            sups = sups.concat(chs[i].getSuperiors());
        }


        var loop = false;
        //check superiors
        for (var i = 0; i < sups.length; i++) {
            var supLink = sups[i];
            if (supLink.from == target) {
                loop = true;
                break;
            } else {
                if (visited.indexOf(supLink.from.id + "x" + target.id) <= 0) {
                    visited.push(supLink.from.id + "x" + target.id);
                    if (isLoop(supLink.from, target, visited)) {
                        loop = true;
                        break;
                    }
                }
            }
        }

        //check target parent
        var tpar = target.getParent();
        if (tpar) {
            if (visited.indexOf(task.id + "x" + tpar.id) <= 0) {
                visited.push(task.id + "x" + tpar.id);
                if (isLoop(task, tpar, visited)) {
                    loop = true;
                }
            }
        }

        //prof.stop();
        return loop;
    }

    //remove my depends
    this.links = this.links.filter(function(link) {
        return link.to != task;
    });

    var todoOk = true;
    if (task.depends) {

        //cannot depend from an ancestor
        var parents = task.getParents();
        //cannot depend from descendants
        var descendants = task.getDescendant();

        var deps = task.depends.split(",");
        var newDepsString = "";

        var visited = [];
        for (var j = 0; j < deps.length; j++) {
            var dep = deps[j]; // in the form of row(lag) e.g. 2:3,3:4,5
            var par = dep.split(":");
            var lag = 0;

            if (par.length > 1) {
                lag = parseInt(par[1]);
            }

            //var sup = this.tasks[parseInt(par[0] - 1)];
            var sup = this.tasks[this.tasksIndexs.indexOf(par[0])];
            
            if (sup) {
                if (parents && parents.indexOf(sup) >= 0) {
                    this.setErrorOnTransaction(task.name + "\n" + GanttMaster.messages.CANNOT_DEPENDS_ON_ANCESTORS + "\n" + sup.name);
                    todoOk = false;

                } else if (descendants && descendants.indexOf(sup) >= 0) {
                    this.setErrorOnTransaction(task.name + "\n" + GanttMaster.messages.CANNOT_DEPENDS_ON_DESCENDANTS + "\n" + sup.name);
                    todoOk = false;

                } else if (isLoop(sup, task, visited)) {
                    todoOk = false;
                    //this.setErrorOnTransaction(GanttMaster.messages.CIRCULAR_REFERENCE + "\n" + task.name + " -> " + sup.name);
                } else {
                    this.links.push(new Link(sup, task, lag));
                    newDepsString = newDepsString + (newDepsString.length > 0 ? "," : "") + dep;
                }
            }
        }

        task.depends = newDepsString;

    }

    //prof.stop();

    return todoOk;
};


//------------------------------------  ADD TASK --------------------------------------------
GanttMaster.prototype.addTask = function(task, row, isSync) {
    //console.debug("master.addTask",task,row,this);
    task.master = this; // in order to access controller from task
    //replace if already exists
    var pos = -1;
    for (var i = 0; i < this.tasks.length; i++) {
        if (task.id == this.tasks[i].id) {
            pos = i;
            break;
        }
    }
    if (pos >= 0) {
        this.tasksIndexs.splice(pos, 1);
        this.tasks.splice(pos, 1);
        row = parseInt(pos);
    }

    //add task in collection
    if (typeof(row) != "number") {
        this.tasksIndexs.push(task['index']);
        this.tasks.push(task);
    } else {
        this.tasksIndexs.splice(row, 0, task['index']);
        this.tasks.splice(row, 0, task);

        //recompute depends string
        if (!isSync) {
            this.updateDependsStrings();
        }
    }
    
    if (isSync) {
        return task;
    }
    //add Link collection in memory
    var linkLoops = !this.updateLinks(task);

    //set the status according to parent
    if (task.getParent())
        task.status = task.getParent().status;
    else
        task.status = "STATUS_ACTIVE";

    var ret = task;
    if (linkLoops || !task.setPeriod(task.start, task.end)) {
        //remove task from in-memory collection
        //console.debug("removing task from memory",task);
        this.tasks.splice(task.getRow(), 1);
        ret = undefined;
    } else {
        //append task to editor
        this.editor.addTask(task, row);
        //append task to gantt
        this.gantt.addTask(task);
    }
    return ret;
};



GanttMaster.prototype.loadTasks = function(tasks, selectedRow) {
    var factory = new TaskFactory();
    //reset
    this.reset();

    for (var i = 0; i < tasks.length; i++) {
        var task = tasks[i];
        if (!(task instanceof Task)) {
            var t = factory.build(task.id, task.name, task.code, task.level, task.start, task.duration, task.collapsed, this.options.isSync, task.end);
            for (var key in task) {
                if (key != "end" && key != "start")
                    t[key] = task[key]; //copy all properties
            }
            task = t;
        }
        task.master = this; // in order to access controller from task
        this.tasks.push(task); //append task at the end
        this.tasksIndexs.push(task['index']);
    }

    //var prof=new Profiler("gm_loadTasks_addTaskLoop");
    for (var i = 0; i < this.tasks.length; i++) {
        var task = this.tasks[i];


        var numOfError = this.__currentTransaction && this.__currentTransaction.errors ? this.__currentTransaction.errors.length : 0;
        //add Link collection in memory
        while (!this.updateLinks(task)) { // error on update links while loading can be considered as "warning". Can be displayed and removed in order to let transaction commits.
            if (this.__currentTransaction && numOfError != this.__currentTransaction.errors.length) {
                var msg = "";
                while (numOfError < this.__currentTransaction.errors.length) {
                    var err = this.__currentTransaction.errors.pop();
                    msg = msg + err.msg + "\n\n";
                }
                alert(msg);
            }
            this.removeAllLinks(task, false);
        }

        if (!task.setPeriod(task.start, task.end)) {
            alert(GanttMaster.messages.GANNT_ERROR_LOADING_DATA_TASK_REMOVED + "\n" + task.name + "\n" + GanttMaster.messages.ERROR_SETTING_DATES);
            //remove task from in-memory collection
            this.tasks.splice(task.getRow(), 1);
        } else {
            //append task to editor
            this.editor.addTask(task, null, true);
            //append task to gantt
            this.gantt.addTask(task);
        }
    }

    this.editor.fillEmptyLines();
    //prof.stop();

    // re-select old row if tasks is not empty
    if (this.tasks && this.tasks.length > 0) {
        selectedRow = selectedRow ? selectedRow : 0;
        this.tasks[selectedRow].rowElement.click();
    }
};

GanttMaster.prototype.addBelowCurrentTask = function(taskNode, isSync) {
    var self = this;
    if (!self.canWrite && !taskNode)
        return;

    var factory = new TaskFactory();
    self.beginTransaction();
    var ch;
    var row = 0;
    
    if (taskNode) {
        ch = factory.build(taskNode.id, taskNode.name, taskNode.code, taskNode.level, taskNode.start, taskNode.duration, false, this.options.isSync, taskNode.end);
        if (!(taskNode instanceof Task)) {
            for (var key in taskNode) {
                if (key != "end" && key != "start")
                    ch[key] = taskNode[key]; //copy all properties
            }
        }
        row = self.currentTask.getRow() + 1;
    } else {
        if (self.currentTask) {
            ch = factory.build("tmp_" + new Date().getTime(), "", "", self.currentTask.level + 1, self.currentTask.start, 1);
            row = self.currentTask.getRow() + 1;
        } else {
            ch = factory.build("tmp_" + new Date().getTime(), "", "", 0, new Date().getTime(), 1);
        }
        ch['index'] = null;
        ch['parent'] = self.currentTask ? self.currentTask.index : null;
    }
    if (taskNode && taskNode['parentTask']) {
        ch['parentTask'] = taskNode['parentTask'];
        Object.defineProperty(ch, "parentTask", {
            enumerable: false
        });
    }
    var task = self.addTask(ch, row, isSync);
    if (task && !isSync) {
        //task.rowElement.click();
        task.rowElement.find("[name=name]").focus();
    }
    self.endTransaction();
};

GanttMaster.prototype.syncUpdateLinks = function() {

    for (var i = 0; i < this.tasks.length; i++) {
        var task = this.tasks[i];
        var numOfError = this.__currentTransaction && this.__currentTransaction.errors ? this.__currentTransaction.errors.length : 0;
        //add Link collection in memory
        while (!this.updateLinks(task)) { // error on update links while loading can be considered as "warning". Can be displayed and removed in order to let transaction commits.
            if (this.__currentTransaction && numOfError != this.__currentTransaction.errors.length) {
                var msg = "";
                while (numOfError < this.__currentTransaction.errors.length) {
                    var err = this.__currentTransaction.errors.pop();
                    msg = msg + err.msg + "\n\n";
                }
                alert(msg);
            }
            this.removeAllLinks(task, false);
        }

        if (!task.setPeriod(task.start, task.end)) {
            alert(GanttMaster.messages.GANNT_ERROR_LOADING_DATA_TASK_REMOVED + "\n" + task.name + "\n" + GanttMaster.messages.ERROR_SETTING_DATES);
            //remove task from in-memory collection
            this.tasks.splice(task.getRow(), 1);
        } else {
            if (!task.rowElement) {
                //append task to editor
                
                this.editor.addTask(task, task.parentTask ? task.parentTask.getRow() + (this.element.find("[taskParent=" + task.parentTask.index + "]").length) + 1 : null);
                //append task to gantt
                this.gantt.addTask(task);
            }
        }
    }
}

/**
 * 不明白原来的为什么要减少一天然后再计算日期
 * @param  {[type]} end [description]
 * @return {[type]}     [description]
 */
function computeEndDate(end) {
    var d = new Date(end);
    //move to next working day
    while (isHoliday(d)) {
        d.setDate(d.getDate() + 1);
    }
    return d;
}