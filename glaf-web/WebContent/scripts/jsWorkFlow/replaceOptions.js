var replaceOptions = {
    "START_EVENT": [{
        label: 'Start Event',
        actionName: 'replace-with-none-start',
        className: 'bpmn-icon-start-event-none',
        target: {
            type: 'bpmn:StartEvent'
        }
    }],
    "TASK": [
        {
            label: 'Service Task',
            actionName: 'replace-with-service-task',
            className: 'bpmn-icon-service',
            target: {
                type: 'bpmn:ServiceTask'
            }
        }, {
            label: 'Script Task',
            actionName: 'replace-with-script-task',
            className: 'bpmn-icon-script',
            target: {
                type: 'bpmn:ScriptTask'
            }
        }
    ]
}