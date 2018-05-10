package com.haulmont.testtask.UI.cards;


import com.haulmont.testtask.database.controllers.DoctorsController;
import com.haulmont.testtask.database.entities.DoctorsStatistic;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class StatisticCard extends Window {
    VerticalLayout layout = new VerticalLayout();
    DoctorsController doctorsController = new DoctorsController();

    public StatisticCard(String caption) {
        super(caption);
        setWidth("80%");
        setHeight("80%");
        setModal(true);
        Grid<DoctorsStatistic> grid = new Grid();
        grid.addColumn(DoctorsStatistic::getUniqueId).setCaption("Номер");
        grid.addColumn(DoctorsStatistic::getSurname).setCaption("Фамилия");
        grid.addColumn(DoctorsStatistic::getValue).setCaption("Количество выписанных рецептов");
        grid.setSizeFull();
        grid.setItems(doctorsController.getStatistic());
        layout.addComponent(grid);
        setContent(grid);
    }
}
