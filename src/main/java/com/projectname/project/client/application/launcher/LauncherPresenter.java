package com.projectname.project.client.application.launcher;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyStandard;
import com.gwtplatform.mvp.client.presenter.slots.Slot;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.projectname.project.client.application.ApplicationPresenter;
import com.projectname.project.client.application.launcher.widget.ToasterPresenterWidget;
import com.projectname.project.client.place.NameTokens;

public class LauncherPresenter extends Presenter<LauncherPresenter.MyView, LauncherPresenter.MyProxy>
        implements LauncherUiHandlers {
    interface MyView extends View, HasUiHandlers<LauncherPresenter> {
    }

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<LauncherPresenter> {
    }

    public static final Slot SLOT_CONTENT = new Slot();

    private final ToasterPresenterWidget toasterPresenterWidget;

    @Inject
    LauncherPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy,
            ToasterPresenterWidget toasterPresenterWidget) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);

        this.toasterPresenterWidget = toasterPresenterWidget;

        getView().setUiHandlers(this);
        setInSlot(SLOT_CONTENT, toasterPresenterWidget);
    }

    @Override
    public void onLaunch(String coordinates, String power) {
        validateFields(coordinates, power);
    }

    private boolean validateFields(String coordinates, String power) {
        return coordinates.matches("[0-9]{3};[0-9]{3};[0-9]{3}") && power.matches("[0-9]");
    }
}
