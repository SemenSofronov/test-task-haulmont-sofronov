package com.haulmont.testtask.UI;

import com.haulmont.testtask.UI.views.DoctorsView;
import com.haulmont.testtask.UI.views.PatientsView;
import com.haulmont.testtask.UI.views.RecipesView;
import com.haulmont.testtask.UI.views.StartView;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class NavigatorUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Больничный Реестр");
        VerticalLayout layout = new VerticalLayout();
        HorizontalLayout topBar = new HorizontalLayout();
        VerticalLayout viewLayout = new VerticalLayout();
        layout.addComponent(topBar);
        layout.addComponent(viewLayout);
        setContent(layout);

        final Navigator navigator = new Navigator(this, viewLayout);
        navigator.addView("", new StartView());
        navigator.addView(RecipesView.RECIPES, new RecipesView());
        navigator.addView(DoctorsView.DOCTORS, new DoctorsView());
        navigator.addView(PatientsView.PATIENTS, new PatientsView());

        for(String s: new String[]{RecipesView.RECIPES,DoctorsView.DOCTORS,PatientsView.PATIENTS})
            topBar.addComponent(this.createNavigationButton(s,navigator));
    }

    private Button createNavigationButton(final String state, final Navigator navigator) {
        return new Button(state, (Button.ClickListener) clickEvent -> navigator.navigateTo(state));
    }
}
