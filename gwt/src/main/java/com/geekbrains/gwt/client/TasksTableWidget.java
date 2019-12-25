package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.TaskDto;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import static com.geekbrains.gwt.client.Utils.getToken;

import java.util.List;

public class TasksTableWidget extends Composite {
    @UiField
    CellTable<TaskDto> table;

    private TasksClient client;

    @UiTemplate("TasksTable.ui.xml")
    interface TasksTableBinder extends UiBinder<Widget, TasksTableWidget> {
    }

    private static TasksTableBinder uiBinder = GWT.create(TasksTableBinder.class);

    public TasksTableWidget() {
        initWidget(uiBinder.createAndBindUi(this));

        TextColumn<TaskDto> idColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return taskDto.getId().toString();
            }
        };
        table.addColumn(idColumn, "ID");

        TextColumn<TaskDto> titleColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return taskDto.getTitle();
            }
        };
        table.addColumn(titleColumn, "Title");

        TextColumn<TaskDto> owmerColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                if (taskDto.getOwner() == null) {
                    return "unknown";
                }
                return taskDto.getOwner().getName();
            }
        };
        table.addColumn(owmerColumn, "Owner");

        TextColumn<TaskDto> assigneeColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                if (taskDto.getAssignee() == null) {
                    return "unknown";
                }
                return taskDto.getAssignee().getName();
            }
        };
        table.addColumn(assigneeColumn, "Assignee");

        TextColumn<TaskDto> descriptionColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                return taskDto.getDescription();
            }
        };
        table.addColumn(descriptionColumn, "Description");

        TextColumn<TaskDto> statusColumn = new TextColumn<TaskDto>() {
            @Override
            public String getValue(TaskDto taskDto) {
                if (taskDto.getStatus() == null) {
                    return "unknown";
                }
                return taskDto.getStatus().getStatusName();
            }
        };
        table.addColumn(statusColumn, "Status");

        client = GWT.create(TasksClient.class);

        Column<TaskDto, Long> actionColumn = new Column<TaskDto, Long>(new ActionCell<Long>("REMOVE", new ActionCell.Delegate<Long>() {
            @Override
            public void execute(Long aLong) {
                client.delete(getToken(), aLong.toString(), new MethodCallback<Void>() {
                    @Override
                    public void onFailure(Method method, Throwable throwable) {
                        GWT.log(throwable.toString());
                        GWT.log(throwable.getMessage());
                        GWT.log("Status code: " + method.getResponse().getStatusCode());
                    }

                    @Override
                    public void onSuccess(Method method, Void result) {
                        GWT.log("Status code: " + method.getResponse().getStatusCode());
                        refresh();
                    }
                });
            }
        })) {
            @Override
            public Long getValue(TaskDto TaskDto) {
                return TaskDto.getId();
            }
        };

        table.addColumn(actionColumn, "Actions");
        table.setColumnWidth(idColumn, 100, Style.Unit.PX);
        table.setColumnWidth(titleColumn, 400, Style.Unit.PX);
        table.setColumnWidth(actionColumn, 200, Style.Unit.PX);
    }

    public void refresh() {
        GWT.log("STORAGE: " + Utils.getToken());
        client.getAll(Utils.getToken(), new MethodCallback<List<TaskDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                GWT.log("Status code: " + method.getResponse().getStatusCode());
                Window.alert("Невозможно получить список задач: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<TaskDto> i) {
                GWT.log("Received " + i.size() + " tasks");
                GWT.log("Status code: " + method.getResponse().getStatusCode());
                table.setRowData(i);
            }
        });
    }

    @UiHandler("btnRefresh")
    public void submitClick(ClickEvent event) {
        refresh();
    }
}
