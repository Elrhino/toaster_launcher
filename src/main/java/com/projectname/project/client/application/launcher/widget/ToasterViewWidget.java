package com.projectname.project.client.application.launcher.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;
import com.projectname.project.client.api.Toaster;

public class ToasterViewWidget extends ViewWithUiHandlers<ToasterWidgetUiHandlers>
        implements ToasterPresenterWidget.MyView {
    interface Binder extends UiBinder<HTMLPanel, ToasterViewWidget> {
    }

    @UiField
    Button fetchButton;
    @UiField
    TextBox name;
    @UiField
    TextBox coordinates;
    @UiField
    TextBox power;

    @Inject
    public ToasterViewWidget(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @UiHandler("fetchButton")
    void onFetchToaster(ClickEvent clickEvent) {
        getUiHandlers().fetchToaster();
    }

    @Override
    public void setToaster(Toaster toaster) {
        name.setText(toaster.getName());
        coordinates.setText(toaster.getCoordinates());
        power.setText(String.valueOf(toaster.getPower()));
    }
}

