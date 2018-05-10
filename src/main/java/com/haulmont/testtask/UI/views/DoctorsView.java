package com.haulmont.testtask.UI.views;

import com.haulmont.testtask.UI.cards.DoctorsDetailCard;
import com.haulmont.testtask.UI.cards.StatisticCard;
import com.haulmont.testtask.database.controllers.DoctorsController;
import com.haulmont.testtask.database.entities.DoctorsEntity;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

public class DoctorsView extends VerticalLayout implements View {

	public static final String DOCTORS = "доктора";

	private DoctorsController doctorsController = new DoctorsController();

	private Grid<DoctorsEntity> grid = new Grid<>();


	public DoctorsView() {


		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.setCaption("Таблица докторов");
		grid.addColumn(DoctorsEntity::getUniqueId).setCaption("Номер");
		grid.addColumn(DoctorsEntity::getSurname).setCaption("Фамилия");
		grid.addColumn(DoctorsEntity::getName).setCaption("Имя");
		grid.addColumn(DoctorsEntity::getMiddleName).setCaption("Отчество");
		grid.addColumn(DoctorsEntity::getSpecialization).setCaption("Специализация");
		grid.setSizeFull();
		grid.setItems(doctorsController.findAll());


		addComponent(grid);
		HorizontalLayout actionBar = new HorizontalLayout();

		Button createButton = new Button("Добавить");
		createButton.setStyleName(ValoTheme.BUTTON_TINY);


		createButton.addClickListener((Button.ClickListener) createEvent -> {
			Window createWindow = new DoctorsDetailCard("Добавление нового доктора",null);
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
				DoctorsEntity doctorsEntity = (DoctorsEntity) grid.getSelectedItems().toArray()[0];
				Window editWindow = new DoctorsDetailCard("Редактирование информации о докторе", doctorsEntity);
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
				DoctorsEntity doctorsEntity = (DoctorsEntity) grid.getSelectedItems().toArray()[0];
				doctorsController.delete(doctorsEntity);
				refresh();
			}
        });


		actionBar.addComponent(deleteButton);

		Button statButton = new Button("Показать статистику");
		statButton.setStyleName(ValoTheme.BUTTON_TINY);

		statButton.addClickListener((Button.ClickListener) clickEvent -> {
			Window statWindow = new StatisticCard("Статистика докторов");
			UI.getCurrent().addWindow(statWindow);
		});


		actionBar.addComponent(statButton);
		addComponent(actionBar);

	}

	public void refresh() {
		grid.setItems(doctorsController.findAll());
	}



	@Override
	public void enter(ViewChangeEvent event) {
		// This view is constructed in the init() method()
	}

}
