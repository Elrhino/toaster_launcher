# GWTP Beginner's tutorial
When I first heard about GWT or "gwit", I thought it sounded like some kind of STD, but when I realized it was Java compiling into JavaScript, it got all my attention. Like many people, except the purists (which I respect), I'm not a big fan of JavaScript. Especially when it comes down to writing logic. So here we have GWTP that does the hard work for you and offers you tools that implements all the best practices on how to build web applications with GWT.

This tutorial will be heavily inspired on the ArcBees developer's website [tutorial][tutorial]. You won't be able to send your toaster into space after this tutorial, but it will be close enough...

## Getting started
To get started, I strongly suggest watching our how-to video on [how to create a basic project][generate]. This will create a basic "Hello World!" application for you generated from our Maven [archetypes][archetypes].

## The Visual: Writing the View
From the basic archetype, we're going to create what we need in order to send our toaster into space.

In GWTP, there's this concept of Presenter-View pair. They always come together. The [Presenter][presenter] handles the logic and the [View][view] only displays what it's told to. So to create our form, we won't need a Presenter yet. What we need is a View class that will use a [UiBinder][uibinder].

Let's first create a new package under `client/application/launcher`. In this package we'll create a class named `LauncherView`. From this View, we will create some fields that will hold the toaster launch parameters.

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

As you might have guess, the View implements `LauncherPresenter.MyView` which does not exists yet. We will get into that shortly. But first, we need to talk about UiBinder. [UiBinders][uibinder] allow you to declare your HTML and GWT Widgets in XML format. So a View that uses a UiBinder will actually be composed of 2 files, a Java file containing your View class and a XML file containing your HTML.

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

The `ui:field="someName"` attribute is how you identifies your widgets so you can retrieve them later in your View.

First, we need to declare the variables that will be associated with your widgets in your View.

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
        // TODO: Send launch parameters to the LauncherPresenter.
    }
}
```

Now that we have access to the widgets values, what we need is a way to send them to the `LauncherPresenter` so that it might process them. However, in order to do so we also need a way to detect click events on the `launchButton`.

In GWTP, [UiHandlers][uihandlers] are great to delegate some of the View Events to the Presenter and that's whats we're gonna use here. We need a new interface that will extends `UiHandlers`.

```java
import com.gwtplatform.mvp.client.UiHandlers;

public interface launcherUiHandlers extends UiHandlers {
    void onLaunch();
}
```

Now, we'll be able to bind the `onLaunch()` method to a ClickEvent using the `@UiHandler("someIdentifier")` annotation. To specify which type of event to listen to, we simply pass the event type as a method parameter.

```java
@UiHandler("launchButton")
public void onLaunch(ClickEvent event) {
    // TODO: Send launch parameters to the LauncherPresenter.
}
```

To send the data to the Presenter we first need to tell the View to use the `LauncherUiHandlers`. We do this by extending `LauncherView` with ` ViewWithUiHandlers<LauncherPresenter>` and this will gives us access to `getUiHandlers`.

```java
@UiHandler("launchButton")
public void onLaunch(ClickEvent event) {
    getUiHandlers().onLaunch(launchCoordinates.getText(), launchPower.getText());
}
```

## The Base: Writing the Presenter
Now that we have a LauncherView with some basic controls and a UiHandler to delegate event handling to the Presenter, we can write the LauncherPresenter to handle logic.

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

In order for the LauncherPresenter to use LauncherUiHandlers, we need to do the following:
1. Implements LauncherUiHandlers for the LauncherPresenter
1. Extend MyView interface with HasUiHandlers<LauncherPresenter>
1. Set the UiHandler for the View: `getView().setUiHandlers(this)`

Then we'll need to implement the `onLaunch()` method:

```java
@Override
public void onLaunch(String launchCoordinates, String launchPower) {
    // TODO: Validate values.

    // TODO: Do something with the values.
}
```

Now we're going to add validation so we're sure wrong values will not turn our toaster into a flaming pile of dust.

```java
private boolean validateFields(String coordinates, String power) {
    return coordinates.matches("[0-9]{3};[0-9]{3};[0-9]{3}") && power.matches("[0-9]");
}
```

```java
@Override
public void onLaunch(String coordinates, String power) {
    if (validateFields(coordinates, power)) {
        // TODO: Do something with the values. Process with service.
    }
}
```

In the next section, we'll see how the toaster will process the values using [RestDispatch][rest], we'll also protect the LauncherPresenter with a [Gatekeeper][security]. The user will need to login before he can access the LauncherPresenter. For this, we'll create LoginPresenter that will use a [NestedSlot][slots].

[uibinder]: http://www.gwtproject.org/doc/latest/DevGuideUiBinder.html
[tutorial]: http://dev.arcbees.com/gwtp/tutorials/
[archetypes]: https://github.com/ArcBees/Arcbees-Archetypes
[presenter]: http://dev.arcbees.com/gwtp/core/presenters/index.html
[view]: http://dev.arcbees.com/gwtp/core/presenters/view.html
[uihandlers]: http://dev.arcbees.com/gwtp/core/presenters/view-with-ui-handlers.html
[execute]: https://youtu.be/6_MQSJy92m0
[generate]: https://youtu.be/Im1DGozNCsU
[slots]: http://localhost:8080/gwtp/core/slots/
[rest]: http://localhost:8080/gwtp/communication/index.html
[security]: http://localhost:8080/gwtp/core/security/