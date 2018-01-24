function CmdLine(canvasId) {
    var vdcanvas = vdmanager.vdrawObject(canvasId);
    vdcanvas.GetUserLine(_onActioncmdLineStateChanged);
}
//status = 'start' or 'end' or 'count'
function _onActioncmdLineStateChanged(action, status) {
    var vdcanvas = action.vdrawOwner();

    if (status == 'start') {
        vdcanvas.Prompt('pick start point');
    } else if (status == 'count') {
        vdcanvas.Prompt('pick end point');
    } else if (status == 'end') {
        vdcanvas.Prompt('');
        if (!action.IsCanceled()) {
            vdcanvas.AddLine(action.ResValue[0], action.ResValue[1], true);
        }
    }
}

function CmdGetDistance(canvasId) {//We use CmdLine because they are the same regarding user interaction
    var vdcanvas = vdmanager.vdrawObject(canvasId);
    vdcanvas.GetUserLine(_onActionGetDistanceStateChanged);
}
//status = 'start' or 'end' or 'count'
function _onActionGetDistanceStateChanged(action, status) {
    var vdcanvas = action.vdrawOwner();

    if (status == 'start') {
        vdcanvas.Prompt('pick first point');
    } else if (status == 'count') {
        vdcanvas.Prompt('pick second point');
    } else if (status == 'end') {
        vdcanvas.Prompt('');
        if (!action.IsCanceled()) {
            action.ResValue[0]
            action.ResValue[1]
            var distance = vdgeo.Distance2D(action.ResValue[0], action.ResValue[1]);
            vdcanvas.Prompt('Distance = ' + distance.toString());
        }
    }
}


function CmdZoomWindow(canvasId) {
    var vdcanvas = vdmanager.vdrawObject(canvasId);
    vdcanvas.GetUserRect(_onActioncmdZoomStateChanged);
}

//status = 'start' or 'end' or 'count'
function _onActioncmdZoomStateChanged(action, status) {
    var vdcanvas = action.vdrawOwner();
    if (status == 'start') {
        action.DispProps = (vdConst.ACTION_DISPLAY_DEFAULT ^ vdConst.ACTION_DISPLAY_USEFILLCOLOR);
        vdcanvas.Prompt('pick first corner');
    } else if (status == 'count') {
        vdcanvas.Prompt('pick second corner');
    } else if (status == 'end') {
        vdcanvas.Prompt('');
        if (!action.IsCanceled()) {
            vdcanvas.zoomwindow(action.ResValue[0], action.ResValue[1]);
            vdcanvas.redraw();
        }
    }
}

function CmdPointId(canvasId) {
    var vdcanvas = vdmanager.vdrawObject(canvasId);
    vdcanvas.GetUserPoint(_onActionPointIdStateChanged);
}

//status = 'start' or 'end' or 'count'
function _onActionPointIdStateChanged(action, status) {
    var vdcanvas = action.vdrawOwner();
    if (status == 'start') {
        vdcanvas.Prompt('pick a point');
    } else if (status == 'end') {
        if (!action.IsCanceled()) {
            vdcanvas.Prompt('X= ' + action.ResValue[X].toString() + ' Y= ' + action.ResValue[Y].toString());
        } else {
            vdcanvas.Prompt('');
        }
    }
}

function CmdSelect(canvasId) {
    var vdcanvas = vdmanager.vdrawObject(canvasId);
    vdcanvas.GetUserRect(_onActionSelectStateChanged);
}
//status = 'start' or 'end' or 'count'
function _onActionSelectStateChanged(action, status) {
    var vdcanvas = action.vdrawOwner();
    if (status == 'start') {
        action.DispProps = (vdConst.ACTION_DISPLAY_DEFAULT | vdConst.ACTION_DISPLAY_USEFILLCOLOR);
        vdcanvas.Prompt('pick first corner');
    } else if (status == 'count') {
        vdcanvas.Prompt('pick second corner');
    } else if (status == 'end') {
        vdcanvas.Prompt('');
        if (!action.IsCanceled()) {

            var p1 = vdcanvas.ViewToPixel(action.ResValue[0]);
            var p2 = vdcanvas.ViewToPixel(action.ResValue[1]);
            var iscross = (p1[X] > p2[X]);
            var ret = null;
            if (iscross) ret = vdcanvas.GetEntitiesFromBox(p1[X], p1[Y], p2[X], p2[Y]); //crossing window select(entities in side or cross the rect bounds)
            else ret = vdcanvas.GetEntitiesInWindowBox(p1[X], p1[Y], p2[X], p2[Y]); //window select (entities complete in side)
            if (ret == null || ret.length == 0) return;
            for (var i = 0; i < ret.length; i++) {
                ret[i].HighLight = true;
            }
            vdcanvas.redraw();
        }
    }
}

function CmdErase(canvasId) {
    var vdcanvas = vdmanager.vdrawObject(canvasId);
    vdcanvas.GetUserRect(_onActionEraseStateChanged);
}
//status = 'start' or 'end' or 'count'
function _onActionEraseStateChanged(action, status) {
    var vdcanvas = action.vdrawOwner();
    if (status == 'start') {
        action.DispProps = (vdConst.ACTION_DISPLAY_DEFAULT | vdConst.ACTION_DISPLAY_USEFILLCOLOR);
        vdcanvas.Prompt('pick first corner');
    } else if (status == 'count') {
        vdcanvas.Prompt('pick second corner');
    } else if (status == 'end') {
        vdcanvas.Prompt('');
        if (!action.IsCanceled()) {

            var p1 = vdcanvas.ViewToPixel(action.ResValue[0]);
            var p2 = vdcanvas.ViewToPixel(action.ResValue[1]);
            var iscross = (p1[X] > p2[X]);
            var ret = null;
            if(iscross) ret = vdcanvas.GetEntitiesFromBox(p1[X], p1[Y], p2[X], p2[Y]); //crossing window select(entities in side or cross the rect bounds)
            else ret = vdcanvas.GetEntitiesInWindowBox(p1[X], p1[Y], p2[X], p2[Y]); //window select (entities complete in side)
            if(ret == null || ret.length == 0) return;
            for (var i = 0; i < ret.length; i++) {
                ret[i].Deleted = true;
            }
            vdcanvas.redraw();
        }
    }
}

