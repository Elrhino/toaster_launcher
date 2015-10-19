package com.projectname.project.client.application.launcher.widget;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.dispatch.rest.client.RestDispatch;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;
import com.projectname.project.client.api.Toaster;
import com.projectname.project.client.api.ToasterResource;

public class ToasterPresenterWidget extends PresenterWidget<ToasterPresenterWidget.MyView>
        implements ToasterWidgetUiHandlers {
    interface MyView extends View, HasUiHandlers<ToasterWidgetUiHandlers> {
        void setToaster(Toaster toaster);
    }

    private final RestDispatch dispatch;
    private final ToasterResource resource;

    @Inject
    public ToasterPresenterWidget(
            EventBus eventBus,
            MyView view,
            ToasterResource resource,
            RestDispatch dispatch) {
        super(eventBus, view);
        this.resource = resource;
        this.dispatch = dispatch;

        getView().setUiHandlers(this);
    }

    @Override
    public void fetchToaster() {
        dispatch.execute(resource.getToaster(), new AsyncCallback<Toaster>() {
            @Override
            public void onFailure(Throwable caught) {
            }

            @Override
            public void onSuccess(Toaster toaster) {
                getView().setToaster(toaster);
            }
        });
    }
}
