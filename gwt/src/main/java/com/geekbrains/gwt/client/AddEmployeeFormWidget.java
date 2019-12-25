package com.geekbrains.gwt.client;

import com.geekbrains.gwt.common.EmployeeDto;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.List;

import static com.geekbrains.gwt.client.Utils.getToken;

public class AddEmployeeFormWidget extends Composite {
    @UiField
    TextBox idText;

    @UiField
    TextBox nameText;

    @UiField
    TextBox ageText;

    private EmployeesTableWidget employeesTableWidget;

    @UiTemplate("AddEmployeeForm.ui.xml")
    interface AddItemFormBinder extends UiBinder<Widget, AddEmployeeFormWidget> {
    }

    private static AddEmployeeFormWidget.AddItemFormBinder uiBinder = GWT.create(AddEmployeeFormWidget.AddItemFormBinder.class);

    public AddEmployeeFormWidget(EmployeesTableWidget employeesTableWidget) {
        this.initWidget(uiBinder.createAndBindUi(this));
        this.employeesTableWidget = employeesTableWidget;
    }

    public void refresh() {
        EmployeeClient employeeClient = GWT.create(EmployeeClient.class);
        employeeClient.getAll(getToken(), new MethodCallback<List<EmployeeDto>>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Unable to load employee list");
            }

            @Override
            public void onSuccess(Method method, List<EmployeeDto> response) {

            }
        });
    }

    @UiHandler("btnSubmit")
    public void submitClick(ClickEvent event) {
        EmployeeClient employeeClient = GWT.create(EmployeeClient.class);

        EmployeeDto employeeDto = new EmployeeDto(null, nameText.getText(), Integer.parseInt(ageText.getText()));

        employeeClient.save(getToken(), employeeDto, new MethodCallback<Void>() {
            @Override
            public void onFailure(Method method, Throwable throwable) {
                Window.alert("Unable to add new task: " + method.getResponse().getText());
            }

            @Override
            public void onSuccess(Method method, Void aVoid) {
                nameText.setText(null);
                ageText.setText(null);
                employeesTableWidget.refresh();
            }
        });
    }
}