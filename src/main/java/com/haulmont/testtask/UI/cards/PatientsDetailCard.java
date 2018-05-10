package com.haulmont.testtask.UI.cards;

import com.haulmont.testtask.database.controllers.PatientsController;
import com.haulmont.testtask.database.entities.PatientsEntity;
import com.vaadin.data.Binder;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;

public class PatientsDetailCard extends Window {


    private static final String ENTER_VALID_TEXT = "Введите текст, начиная с заглавной буквы";

    private static final String ENTER_VALID_PHONE_NUMBER = "Введите корректный номер сотового телефона";

    private static final String PHONE_FORMAT = "\\D*([0-9]\\d{10})\\D*";

    private static final String TEXT_FORMAT = "\\D*([А-Я])\\D*";

    private static final String IS_REQUIRED = "Обязательное поле";

    Binder<PatientsEntity> binder = new Binder<>(PatientsEntity.class);

    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout buttonsBar = new HorizontalLayout();
    PatientsController patientsController = new PatientsController();

    TextField surName = new TextField("Фамилия");
    TextField name = new TextField("Имя");
    TextField middleName = new TextField("Отчество");
    TextField phoneNumber = new TextField("Номер телефона");

    public PatientsDetailCard(String caption, PatientsEntity patientsEntity) {
        super(caption);
        setWidth("50%");
        setHeight("50%");
        setModal(true);


        surName.setWidth("100%");
        name.setWidth("100%");
        middleName.setWidth("100%");
        phoneNumber.setWidth("100%");
        if (patientsEntity != null) {
            surName.setValue(patientsEntity.getSurname());
            name.setValue(patientsEntity.getName());
            middleName.setValue(patientsEntity.getMiddleName());
            phoneNumber.setValue(patientsEntity.getPhone());
            Button updateButton = new Button("Ок");
            updateButton.addClickListener((Button.ClickListener) clickEvent -> {
                if (binder.isValid()) {
                    patientsEntity.setMiddleName(middleName.getValue());
                    patientsEntity.setName(name.getValue());
                    patientsEntity.setSurname(surName.getValue());
                    patientsEntity.setPhone(phoneNumber.getValue());
                    patientsController.update(patientsEntity);
                    close();
                }
            });
            buttonsBar.addComponent(updateButton);
        } else {
            Button addButton = new Button("Ок");
            addButton.addClickListener((Button.ClickListener) clickEvent -> {
                if (binder.isValid()) {
                    PatientsEntity newPatient = new PatientsEntity(name.getValue(), surName.getValue(), middleName.getValue(), phoneNumber.getValue());
                    patientsController.persist(newPatient);
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
        layout.addComponent(phoneNumber);
        setContent(layout);
    }

    private void bindingFields() {
        binder.forField(phoneNumber).withNullRepresentation("")
                .withValidator(new RegexpValidator(ENTER_VALID_PHONE_NUMBER, PHONE_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(PatientsEntity::getPhone, PatientsEntity::setPhone);
        binder.forField(surName).withNullRepresentation("")
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(PatientsEntity::getSurname, PatientsEntity::setSurname);

        binder.forField(name)
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(PatientsEntity::getName, PatientsEntity::setName);

        binder.forField(middleName)
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(PatientsEntity::getMiddleName, PatientsEntity::setMiddleName);

        binder.bindInstanceFields(this);
    }
}
