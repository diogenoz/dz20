package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.EmployeeDto;
import com.google.gwt.cell.client.ActionCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

import static com.geekbrains.gwt.client.Utils.getToken;

@UiTemplate("EmployeesTable.ui.xml")
public class EmployeesTableWidget extends Composite {
    @UiField
    CellTable<EmployeeDto> table;
    private EmployeeClient client;

    @UiTemplate("EmployeesTable.ui.xml")
    interface EmployeeTableBinder extends UiBinder<Widget, EmployeesTableWidget> {
    }

    private static EmployeeTableBinder uiBinder = GWT.create(EmployeeTableBinder.class);

    public EmployeesTableWidget() {
        initWidget(uiBinder.createAndBindUi(this));

        TextColumn<EmployeeDto> idColumn = new TextColumn<EmployeeDto>() {
            @Override
            public String getValue(EmployeeDto employeeDto) {
                return employeeDto.getId().toString();
            }
        };

        client = GWT.create(EmployeeClient.class);

        TextColumn<EmployeeDto> nameColumn = new TextColumn<EmployeeDto>() {
            @Override
            public String getValue(EmployeeDto employeeDto) {
                return employeeDto.getName();
            }
        };

        TextColumn<EmployeeDto> ageColumn = new TextColumn<EmployeeDto>() {
            @Override
            public String getValue(EmployeeDto employeeDto) {
                return employeeDto.getAge().toString();
            }
        };

        Column<EmployeeDto, Long> actionColumn = new Column<EmployeeDto, Long>(new ActionCell<Long>("REMOVE", new ActionCell.Delegate<Long>() {
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
            public Long getValue(EmployeeDto EmployeeDto) {
                return EmployeeDto.getId();
            }
        };
        table.addColumn(idColumn, "ID");
        table.addColumn(nameColumn, "Name");
        table.addColumn(ageColumn, "Age");
        table.addColumn(actionColumn, "Actions");
        table.setColumnWidth(idColumn, 100, Style.Unit.PX);
        table.setColumnWidth(nameColumn, 400, Style.Unit.PX);
        table.setColumnWidth(actionColumn, 200, Style.Unit.PX);
    }

    public void refresh() {
        GWT.log("STORAGE: " + Utils.getToken());
        client.getAll(Utils.getToken(), new MethodCallback<List<EmployeeDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                GWT.log(throwable.toString());
                GWT.log(throwable.getMessage());
                GWT.log("Status code: " + method.getResponse().getStatusCode());
                Window.alert("Невозможно получить список сотрудников: Сервер не отвечает");
            }

            @Override
            public void onSuccess(Method method, List<EmployeeDto> i) {
                GWT.log("Received " + i.size() + " employees");
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
