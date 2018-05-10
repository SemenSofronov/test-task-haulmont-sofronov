package com.haulmont.testtask.UI.views;

import com.haulmont.testtask.UI.cards.PatientsDetailCard;
import com.haulmont.testtask.database.controllers.PatientsController;
import com.haulmont.testtask.database.entities.PatientsEntity;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

public class PatientsView extends VerticalLayout implements View {

	public static final String PATIENTS = "пациенты";

	private PatientsController patientsController = new PatientsController();

	private Grid<PatientsEntity> grid = new Grid<>();


	public PatientsView() {

		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setCaption("Таблица пациентов");
		grid.addColumn(PatientsEntity::getUniqueId).setCaption("Номер");
		grid.addColumn(PatientsEntity::getSurname).setCaption("Фамилия");
		grid.addColumn(PatientsEntity::getName).setCaption("Имя");
		grid.addColumn(PatientsEntity::getMiddleName).setCaption("Отчество");
		grid.addColumn(PatientsEntity::getPhone).setCaption("Телефон");
		grid.setSizeFull();
		grid.setItems(patientsController.findAll());

		addComponent(grid);
		HorizontalLayout actionBar = new HorizontalLayout();

		Button createButton = new Button("Добавить");
		createButton.setStyleName(ValoTheme.BUTTON_TINY);

		createButton.addClickListener((Button.ClickListener) createEvent -> {
			Window createWindow = new PatientsDetailCard("Добавление нового пациента",null);
			UI.getCurrent().addWindow(createWindow);
            createWindow.addCloseListener((Window.CloseListener) closeEvent -> {
                refresh();
            });
		});
		actionBar.addComponent(createButton);

		Button editButton = new Button("Изменить");
		editButton.setStyleName(ValoTheme.BUTTON_TINY);

		editButton.addClickListener((Button.ClickListener) createEvent -> {
			if (!grid.getSelectedItems().isEmpty()) {
				PatientsEntity patientsEntity = (PatientsEntity) grid.getSelectedItems().toArray()[0];
				Window editWindow = new PatientsDetailCard("Редактирование информации о пациенте", patientsEntity);
				UI.getCurrent().addWindow(editWindow);
                editWindow.addCloseListener((Window.CloseListener) closeEvent -> {
                    refresh();
                });
			}
		});

		actionBar.addComponent(editButton);

		Button deleteButton = new Button("Удалить");
		deleteButton.setStyleName(ValoTheme.BUTTON_TINY);

		deleteButton.addClickListener((Button.ClickListener) clickEvent -> {
            if (!grid.getSelectedItems().isEmpty()) {
				PatientsEntity patientsEntity = (PatientsEntity) grid.getSelectedItems().toArray()[0];
				patientsController.delete(patientsEntity);
				refresh();
			}
        });

		actionBar.addComponent(deleteButton);

		addComponent(actionBar);

	}

	public void refresh() {
		grid.setItems(patientsController.findAll());
	}



	@Override
	public void enter(ViewChangeEvent event) {
		// This view is constructed in the init() method()
	}

}
