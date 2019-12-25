package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.EmployeeDto;
import com.geekbrains.gwt.common.TaskDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import static com.geekbrains.gwt.client.Utils.getToken;

import java.util.List;

public class AddTaskFormWidget extends Composite {
    @UiField
    TextBox idText;

    @UiField
    TextBox titleText;

    @UiField
    ListBox ownerBox;

    @UiField
    ListBox assigneeBox;

    @UiField
    TextBox descriptionText;

    @UiField
    ListBox statusBox;

    private TasksTableWidget tasksTableWidget;

    @UiTemplate("AddTaskForm.ui.xml")
    interface AddItemFormBinder extends UiBinder<Widget, AddTaskFormWidget> {
    }

    private static AddTaskFormWidget.AddItemFormBinder uiBinder = GWT.create(AddTaskFormWidget.AddItemFormBinder.class);

    public AddTaskFormWidget(TasksTableWidget itemsTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.tasksTableWidget = itemsTableWidget;
    }

    public void refresh() {
        EmployeeClient employeeClient = GWT.create(EmployeeClient.class);
        employeeClient.getAll(getToken(), new MethodCallback<List<EmployeeDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Unable to load employees list");
            }

            @Override
            public void onSuccess(Method method, List<EmployeeDto> EmployeeDtos) {
                ownerBox.clear();
                assigneeBox.clear();
                for (EmployeeDto o : EmployeeDtos) {
                    ownerBox.addItem(o.getName(), o.getId().toString());
                    assigneeBox.addItem(o.getName(), o.getId().toString());
                }
                statusBox.clear();
                for (TaskDto.TaskStatusDto o : TaskDto.TaskStatusDto.values()) {
                    statusBox.addItem(o.getStatusName(), o.toString());
                }
            }
        });
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        TasksClient tasksClient = GWT.create(TasksClient.class);
        EmployeeClient employeeClient = GWT.create(EmployeeClient.class);

        EmployeeDto owner = new EmployeeDto(Long.parseLong(ownerBox.getSelectedValue()), ownerBox.getSelectedItemText());
        EmployeeDto assignee = new EmployeeDto(Long.parseLong(assigneeBox.getSelectedValue()), assigneeBox.getSelectedItemText());
        TaskDto.TaskStatusDto status = TaskDto.TaskStatusDto.valueOf(statusBox.getSelectedValue());

        TaskDto TaskDto = new TaskDto(null, titleText.getText(), owner, assignee, descriptionText.getText(), status);

        tasksClient.save(getToken(), TaskDto, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Unable to add new task: " + method.getResponse().getText());
            }

            @Override
            public void onSuccess(Method method, Void aVoid) {
                titleText.setText(null);
                tasksTableWidget.refresh();
            }
        });
    }
}