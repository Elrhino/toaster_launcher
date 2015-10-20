package com.projectname.project.client.application.launcher;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.projectname.project.client.application.launcher.widget.ToasterWidgetModule;

public class LauncherModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new ToasterWidgetModule());

        bindPresenter(LauncherPresenter.class, LauncherPresenter.MyView.class, LauncherView.class,
                LauncherPresenter.MyProxy.class);
    }
}
