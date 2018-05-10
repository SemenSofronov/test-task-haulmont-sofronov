package com.haulmont.testtask.UI.views;

import com.haulmont.testtask.UI.cards.RecipesDetailCard;
import com.haulmont.testtask.database.controllers.RecipesController;
import com.haulmont.testtask.database.entities.RecipesEntity;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;

public class RecipesView extends VerticalLayout implements View {

    public static final String RECIPES = "рецепты";

    private RecipesController recipesController = new RecipesController();

    private Grid<RecipesEntity> grid = new Grid<>();


    public RecipesView() {

        Panel panel = new Panel("Панель фильтрации");
        panel.setSizeUndefined();
        HorizontalLayout content = new HorizontalLayout();
        TextField patientFilter = new TextField("Пациент");
        TextField priorityFilter = new TextField("Приоритет");
        TextField descriptionFilter = new TextField("Описание");
        content.addComponent(patientFilter);
        content.addComponent(priorityFilter);
        content.addComponent(descriptionFilter);
        content.setSizeUndefined(); // Shrink to fit
        content.setMargin(true);
        Button confirmButton = new Button("Применить");
        confirmButton.addClickListener((Button.ClickListener) clickEvent -> {
            grid.setItems(recipesController.getItemsByFilter(patientFilter.getValue(), priorityFilter.getValue(), descriptionFilter.getValue()));
        });

        content.addComponent(confirmButton);
        content.setComponentAlignment(confirmButton, Alignment.MIDDLE_RIGHT);
        Button resetButton = new Button("Сброс");
        resetButton.addClickListener((Button.ClickListener) clickEvent -> {
            grid.setItems(recipesController.findAll());
        });
        content.addComponent(resetButton);
        content.setComponentAlignment(resetButton, Alignment.MIDDLE_RIGHT);
        panel.setContent(content);
        addComponent(panel);
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.setCaption("Таблица рецептов");
        grid.addColumn(RecipesEntity::getUniqueId).setCaption("Номер");
        grid.addColumn(RecipesEntity::getDescription).setCaption("Описание");
        grid.addColumn(recipesEntity -> recipesEntity.getDoctor().getSurname()).setCaption("Доктор");
        grid.addColumn(recipesEntity -> recipesEntity.getPatient().getSurname()).setCaption("Пациент");
        grid.addColumn(RecipesEntity::getCreatedTimestamp).setCaption("Дата создания");
        grid.addColumn(RecipesEntity::getValidity).setCaption("Срок действия");
        grid.addColumn(recipesEntity -> recipesEntity.getPriority().getName()).setCaption("Приоритет");
        grid.setSizeFull();
        grid.setItems(recipesController.findAll());

        addComponent(grid);
        HorizontalLayout actionBar = new HorizontalLayout();

        Button createButton = new Button("Добавить");
        createButton.setStyleName(ValoTheme.BUTTON_TINY);

        createButton.addClickListener((Button.ClickListener) createEvent -> {
            Window createWindow = new RecipesDetailCard("Добавление нового рецепта", null);
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
                RecipesEntity recipesEntity = (RecipesEntity) grid.getSelectedItems().toArray()[0];
                Window editWindow = new RecipesDetailCard("Редактирование информации о рецепте", recipesEntity);
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
				RecipesEntity recipesEntity = (RecipesEntity) grid.getSelectedItems().toArray()[0];
				recipesController.delete(recipesEntity);
				refresh();
			}
        });


        actionBar.addComponent(deleteButton);

        addComponent(actionBar);

    }

    public void refresh() {
        grid.setItems(recipesController.findAll());
    }


    @Override
    public void enter(ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

}
