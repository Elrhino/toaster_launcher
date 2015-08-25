package com.projectname.project.client.application.launcher;

import com.gwtplatform.mvp.client.UiHandlers;

public interface LauncherUiHandlers extends UiHandlers {
    void onLaunch(String coordinates, String power);
}
