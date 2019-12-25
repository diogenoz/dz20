package com.geekbrains.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.*;
import org.fusesource.restygwt.client.*;

public class WebApp implements EntryPoint {
    public void onModuleLoad() {
        Defaults.setServiceRoot("http://localhost:8189/gwt-rest");

        // taskListPanel
        TasksTableWidget tasksTableWidget = new TasksTableWidget();
        AddTaskFormWidget addTaskFormWidget = new AddTaskFormWidget(tasksTableWidget);
        VerticalPanel taskListPanel = new VerticalPanel();
        taskListPanel.add(addTaskFormWidget);
        taskListPanel.add(tasksTableWidget);

        // employeeListPanel
        EmployeesTableWidget employeesTableWidget = new EmployeesTableWidget();
        AddEmployeeFormWidget addEmployeeFormWidget = new AddEmployeeFormWidget(employeesTableWidget);
        VerticalPanel employeeListPanel = new VerticalPanel();
        employeeListPanel.add(addEmployeeFormWidget);
        employeeListPanel.add(employeesTableWidget);


        TabLayoutPanel tabPanel = new TabLayoutPanel(2.5, Style.Unit.EM);
        tabPanel.setAnimationDuration(100);
        tabPanel.getElement().getStyle().setMarginBottom(10.0, Style.Unit.PX);

        LoginForm loginForm = new LoginForm(tabPanel, tasksTableWidget, addTaskFormWidget);

        tabPanel.add(loginForm, "Login");
        tabPanel.add(taskListPanel, "Task List Page");
        tabPanel.add(employeeListPanel, "Employee List Page");

        tabPanel.setHeight("800px");
        tabPanel.ensureDebugId("cwTabPanel");
        tabPanel.addSelectionHandler(event -> History.newItem("page" + event.getSelectedItem()));

        History.addValueChangeHandler(event -> {
            String historyToken = event.getValue();
            try {
                if (historyToken.substring(0, 4).equals("page")) {
                    String tabIndexToken = historyToken.substring(4, 5);
                    int tabIndex = Integer.parseInt(tabIndexToken);
                    tabPanel.selectTab(tabIndex);
                } else {
                    tabPanel.selectTab(0);
                }
            } catch (IndexOutOfBoundsException e) {
                tabPanel.selectTab(0);
            }
        });

        tabPanel.selectTab(0);

        Label header = new Label("GWT Task list Application");
        header.setStyleName("headerLabel");

        RootPanel.get().add(header);
        RootPanel.get().add(tabPanel);
    }
}