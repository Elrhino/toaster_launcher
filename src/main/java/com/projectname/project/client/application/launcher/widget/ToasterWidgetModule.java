package com.projectname.project.client.application.launcher.widget;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ToasterWidgetModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindSingletonPresenterWidget(ToasterWidgetPresenter.class, ToasterWidgetPresenter.MyView.class,
                ToasterWidgetView.class);
    }
}
