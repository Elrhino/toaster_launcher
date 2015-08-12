package com.projectname.project.client.application.launcher;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class LauncherModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(LauncherPresenter.class, LauncherPresenter.MyView.class, LauncherView.class,
                LauncherPresenter.MyProxy.class);
    }
}
