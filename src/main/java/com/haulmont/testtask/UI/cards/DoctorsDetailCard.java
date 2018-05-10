package com.haulmont.testtask.UI.cards;

import com.haulmont.testtask.database.controllers.DoctorsController;
import com.haulmont.testtask.database.entities.DoctorsEntity;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;

public class DoctorsDetailCard extends Window {

    private static final String ENTER_VALID_TEXT = "Введите текст, начиная с заглавной буквы";

    private static final String TEXT_FORMAT = "\\D*([А-Я])\\D*";

    private static final String IS_REQUIRED = "Обязательное поле";

    Binder<DoctorsEntity> binder = new Binder<>(DoctorsEntity.class);
    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout buttonsBar = new HorizontalLayout();
    DoctorsController doctorsController = new DoctorsController();
    TextField surName = new TextField("Фамилия");
    TextField name = new TextField("Имя");
    TextField middleName = new TextField("Отчество");
    TextField specialization = new TextField("Специализация");

    public DoctorsDetailCard(String caption, DoctorsEntity doctorsEntity) {
        super(caption);
        setWidth("50%");
        setHeight("50%");
        setModal(true);

        surName.setWidth("100%");
        name.setWidth("100%");
        middleName.setWidth("100%");
        specialization.setWidth("100%");
        if (doctorsEntity != null) {
            surName.setValue(doctorsEntity.getSurname());
            name.setValue(doctorsEntity.getName());
            middleName.setValue(doctorsEntity.getMiddleName());
            specialization.setValue(doctorsEntity.getSpecialization());
            Button updateButton = new Button("Ок");
            updateButton.addClickListener((Button.ClickListener) clickEvent -> {
                if (binder.isValid()) {
                    doctorsEntity.setMiddleName(middleName.getValue());
                    doctorsEntity.setName(name.getValue());
                    doctorsEntity.setSurname(surName.getValue());
                    doctorsEntity.setSpecialization(specialization.getValue());
                    doctorsController.update(doctorsEntity);
                    close();
                }
            });
            buttonsBar.addComponent(updateButton);
        } else {
            Button addButton = new Button("Ок");
            addButton.addClickListener((Button.ClickListener) clickEvent -> {
                if (binder.isValid()) {
                    DoctorsEntity newDoctor = new DoctorsEntity(name.getValue(), surName.getValue(), middleName.getValue(), specialization.getValue());
                    doctorsController.persist(newDoctor);
                    close();
                }
            });
            buttonsBar.addComponent(addButton);
        }
        Button cancelButton = new Button("Отменить");
        cancelButton.addClickListener((Button.ClickListener) clickEvent -> close());
        buttonsBar.addComponent(cancelButton);
        bindingFields();
        layout.addComponent(buttonsBar);
        layout.addComponent(surName);
        layout.addComponent(name);
        layout.addComponent(middleName);
        layout.addComponent(specialization);
        setContent(layout);
    }

    private void bindingFields() {
        binder.forField(specialization).withNullRepresentation("")
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(DoctorsEntity::getSpecialization, DoctorsEntity::setSpecialization);
        binder.forField(surName).withNullRepresentation("")
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(DoctorsEntity::getSurname, DoctorsEntity::setSurname);

        binder.forField(name)
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(DoctorsEntity::getName, DoctorsEntity::setName);

        binder.forField(middleName)
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(DoctorsEntity::getMiddleName, DoctorsEntity::setMiddleName);

        binder.bindInstanceFields(this);
    }
}
