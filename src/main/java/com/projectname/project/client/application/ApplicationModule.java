package com.projectname.project.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.projectname.project.client.application.launcher.LauncherModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new LauncherModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
