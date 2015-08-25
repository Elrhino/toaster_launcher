package com.projectname.project.client.application.launcher;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class LauncherView extends ViewWithUiHandlers<LauncherPresenter> implements LauncherPresenter.MyView {
    interface Binder extends UiBinder<Widget, LauncherView> {
    }

    @UiField
    TextBox launchCoordinates;
    @UiField
    TextBox launchPower;
    @UiField
    Button launchButton;

    @Inject
    LauncherView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("launchButton")
    public void onLaunch(ClickEvent event) {
        getUiHandlers().onLaunch(launchCoordinates.getText(), launchPower.getText());
    }
}
