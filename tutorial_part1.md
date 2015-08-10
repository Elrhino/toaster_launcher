# Title
...
This tutorial will be heavily inspired on the ArcBees developer's website [tutorial](TODO).

...
You won't be able to send your toaster into space after this tutorial, but it will be close enough...
...

## Why GWT/GWTP is awesome
When I first heard about GWT/GWTP I thought it was some kind of STD, but when I realized it was Java compiling into JavaScript, it got all my attention. Like many people, except the purists (which I respect), I'm not a big fan of JavaScript. Especially when it comes down to writing logic. So here we have GWTP that does the hard work for you and offers you tools that implements all the best practices on how to build web applications with GWT.

## Getting started
To get started, I strongly suggest watching our how-to video on [how to create a basic project](TODO). This will create a basic "Hello World!" application for you generated from our Maven [archetypes](TODO).

## Creating a basic form
From the basic archetype, we're going to create what we need in order to send our toaster into space. So, let's create a basic POJO representing our toaster:

```java
public class FlyingToaster() {
    private String coordinates; // X, Y, Z separated by semicolons ';'
    private String power;

    ...
}
```

In GWTP, there's this concept of Presenter-View pair. They always come together. The Presenter handles the logic and the View only displays what it's told to. **The Presenter don't care about what the View does and the View just don't know anything at all.**

So to create our form, we won't need a Presenter yet. What we need is a View class that will use a [UiBinder](TODO).

Let's first create a new package under `client/application/launcher`. In this package we'll create a class named `LauncherView`. From this View, we will create some fields to control the toaster launch.

```java
import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class LauncherView extends ViewImpl implements LauncherPresenter.MyView {
    interface Binder extends UiBinder<Widget, LauncherView> {
    }

    @Inject
    LauncherView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
```

As you might have guess, the View implements `LauncherPresenter.MyView` which does not exists yet. We will get into that shortly. But first, we need to talk about *UiBinder*. [UiBinders](TODO) allow you to declare your HTML and GWT Widgets in XML format. So a View that uses a UiBinder will actually be composed of 2 files, a Java file containing your View class and a XML file containing your HTML.

Now all you have to do is to declare an interface extending UiBinder, like we did in the example above, and create a new XML file named after the View. So in this case, we'll create `LauncherView.ui.xml`.

```java
<ui:UiBinder xmlns:ui='urn:ui:com.google.gwt.uibinder'
             xmlns:g='urn:import:com.google.gwt.user.client.ui'>
</ui:UiBinder>
```

From this node, you will declare all of your HTML elements and/or your GWT Widgets.

On the first line, `xmlns:ui='urn:ui:com.google.gwt.uibinder'` and `xmlns:g='urn:import:com.google.gwt.user.client.ui'` are libraries imports. This means that we'll use the default GWT widgets for this project, but this also means that you can create your own.

Let's declare some fields to handle the toaster launch parameters:

```xml
<ui:UiBinder xmlns:ui='urn:ui:com.google.gwt.uibinder'
             xmlns:g='urn:import:com.google.gwt.user.client.ui'>

    <g:HTMLPanel>
        <g:TextBox ui:field="launchCoordinates"/><br/>
        <g:TextBox ui:field="launchPower"/><br/>
        <g:Button ui:field="launchButton"/>
    </g:HTMLPanel>
</ui:UiBinder>
```

`ui:field="someName"` is how you identifies your widgets so you can retrieve them later in your View.

Now that we have our basic widgets declared, we need to validate the input just to be sure that a wrong value doesn't turn our toaster into fireworks.

First, we need to declare the variables in which the widgets we declared will be hold:

```java
public class LauncherView extends ViewImpl implements LauncherPresenter.MyView {
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

    public void onLaunch() {
        validateFields();

        // TODO: Send launch parameters to the LauncherPresenter.
    }

    private boolean validateFields() {
        String coordinates = launchCoordinates.getText();
        String power = launchPower.getText();

        return coordinates.matches("([a-zA-Z;])") && power.matches("([0-9])");
    }
}
```

Now that we're sure to have valid launch parameters, what we need is a way to send them to the `LauncherPresenter` so that it might process them. However, in order to do so we also need a way to detect click events on the `launchButton`.

In GWTP, [UiHandlers](TODO) are great to delegate some of the View Events to the Presenter and that's whats we're gonna use here. We need a new interface that will extends `UiHandlers`.

```java
import com.gwtplatform.mvp.client.UiHandlers;

public interface launcherUiHandlers extends UiHandlers {
    void onLaunch();
}
```

Now, we'll be able to bind the `onLaunch` method to a ClickEvent using the `@UiHandler("someIdentifier")` annotation. To specify which type of event to listen to, we simply pass this event as a method parameter.

```java
@UiHandler("launchButton")
public void onLaunch(ClickEvent event) {
    validateFields();

    // TODO: Send launch parameters to the LauncherPresenter.
}
```

To send the data to the Presenter we first need to tell the View to use the `LauncherUiHandlers`. We do this by extending `LauncherView` with ` ViewWithUiHandlers<LauncherUiHandlers>` and then we'll be able to call the `onLaunch()` method from `LauncherUiHandlers`.

```java
@UiHandler("launchButton")
public void onLaunch(ClickEvent event) {
    if (validateFields()) {
        getUiHandlers().onLaunch(launchCoordinates.getText(), launchPower.getText());
    }
}
```

```java
public class LauncherPresenter extends Presenter<LauncherPresenter.MyView, LauncherPresenter.MyProxy> {
    interface MyView extends View {
    }

    @ProxyStandard
    @NameToken(NameTokens.HOME)
    interface MyProxy extends ProxyPlace<LauncherPresenter> {
    }

    @Inject
    LauncherPresenter(
            EventBus eventBus,
            MyView view,
            MyProxy proxy) {
        super(eventBus, view, proxy, ApplicationPresenter.SLOT_MAIN);
    }
}
```

## Writing some logic
...