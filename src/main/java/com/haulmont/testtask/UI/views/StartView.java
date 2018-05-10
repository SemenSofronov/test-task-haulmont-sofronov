package com.haulmont.testtask.UI.views;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/** A start view for navigating to the main view */
public class StartView extends VerticalLayout implements View {
//	public StartView() {
//		Button button = new Button("Go to Main View",
//				new Button.ClickListener() {
//					@Override
//					public void buttonClick(Button.ClickEvent event) {
//						navigator.navigateTo(MAINVIEW);
//					}
//				});
//		addComponent(button);
//		setComponentAlignment(button, Alignment.MIDDLE_CENTER);
//	}

	@Override
	public void enter(ViewChangeEvent event) {
		Notification.show("Добро пожаловать!");
	}
}
