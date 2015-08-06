# Title
...
This tutorial will be heavily inspired on the ArcBees developer's website [tutorial](TODO).

...
You won't be able to send your toaster into space after this tutorial, but it will be close enough...
...

## Why GWT/GWTP is awesome
When I first heard about GWT/GWTP I thought it was some kind of STD, but when I realized it was Java compiling into JavaScript, it got all my attention. Like many people, except the purists (which I respect), I'm not a big fan of JavaScript. Especially when you need to write logic on the client side. So here we have GWTP that does the hard work for you and offers you tools that implements all the best practices on how to build web applications with GWT.

## Getting started
To get started, I strongly suggest watching our how-to video on [how to create a basic project](TODO). This will create a basic "Hello World!" application for you generated from our Maven [archetypes](TODO).

## Creating a basic form
From the basic archetype, we're going to create what we need in order to send our toaster into space. So, let's create a basic POJO representing our toaster:

```java
public class FlyingToaster() {
    private String name;

    ...
}
```

In GWTP, there's this concept of Presenter-View pair. They always come together. The Presenter handles the logic and the View only displays what it's told to. The Presenter don't care about what the View does and the View just don't know anything at all.

So to create our form, we won't need a Presenter yet. What we need is a View class that will use a [UIBinder](TODO).

Let's first create a new package under `client/application/launcher`. In this package we'll create a class named `LauncherView`.

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

As you might have guess, the View implements `LauncherPresenter.MyView` which is not yet declared. We will get into that shortly. But first, we need to talk about UiBinder. What this does is that it will allow you to declare your HTML and GWT Widgets in XML format. So a View that uses UiBinder will actually be composed of 2 files, a Java file containing your View class and a XML file containing your HTML.

Now all you have to do is to declare an interface extending UiBinder, like we did in the example above, and create a new XML file named after the View. So in this case, we'll create `LauncherView.ui.xml`.

```java
<ui:UiBinder xmlns:ui='urn:ui:com.google.gwt.uibinder'
             xmlns:g='urn:import:com.google.gwt.user.client.ui'>
</ui:UiBinder>
```

This code snippet is where you will declare all of your HTML elements and/or your GWT Widgets. On the first line, `xmlns:ui='urn:ui:com.google.gwt.uibinder'` and `xmlns:g='urn:import:com.google.gwt.user.client.ui'` are libraries imports. This means that you can define your own HTML elements ... 

To launch our toaster into space, we need a form so we can set some parameters for our toaster.

```xml
<ui:UiBinder xmlns:ui='urn:ui:com.google.gwt.uibinder'
             xmlns:g='urn:import:com.google.gwt.user.client.ui'>

    <g:HTMLPanel>
        <g:TextBox ui:field="txtCoordinates"/><br/>
        <g:TextBox ui:field="txtPower"/><br/>
        <g:Button ui:field="btnLaunch"/>
    </g:HTMLPanel>
</ui:UiBinder>
```

You can see here that

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

##
