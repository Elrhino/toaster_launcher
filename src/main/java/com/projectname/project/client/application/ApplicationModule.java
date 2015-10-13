package com.projectname.project.client.application;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.projectname.project.client.application.launcher.LauncherModule;
import com.projectname.project.client.application.login.LoginModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new LauncherModule());
        install(new LoginModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
