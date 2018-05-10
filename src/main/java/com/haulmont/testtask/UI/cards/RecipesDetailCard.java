package com.haulmont.testtask.UI.cards;

import com.haulmont.testtask.database.controllers.DoctorsController;
import com.haulmont.testtask.database.controllers.PatientsController;
import com.haulmont.testtask.database.controllers.PrioritiesController;
import com.haulmont.testtask.database.controllers.RecipesController;
import com.haulmont.testtask.database.entities.DoctorsEntity;
import com.haulmont.testtask.database.entities.PatientsEntity;
import com.haulmont.testtask.database.entities.PrioritiesEntity;
import com.haulmont.testtask.database.entities.RecipesEntity;
import com.vaadin.data.Binder;
import com.vaadin.data.converter.StringToIntegerConverter;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;

import java.sql.Timestamp;
import java.util.List;

public class RecipesDetailCard extends Window {

    private static final String ENTER_VALID_TEXT = "Введите текст, начиная с заглавной буквы";

    private static final String ENTER_VALID_PHONE_NUMBER = "Введите корректный номер сотового телефона";

    private static final String ENTER_VALID_VALIDITY = "Введите верное количество дней";

    private static final String PHONE_FORMAT = "\\D*([0-9]\\d{10})\\D*";

    private static final String TEXT_FORMAT = "\\D*([А-Я])\\D*";

    private static final String VALIDITY_FORMAT = "\\D*([0-9])\\D*";

    private static final String IS_REQUIRED = "Обязательное поле";

    Binder<RecipesEntity> binder = new Binder<>();
    VerticalLayout layout = new VerticalLayout();
    HorizontalLayout buttonsBar = new HorizontalLayout();
    RecipesController recipesController = new RecipesController();
    PatientsController patientsController = new PatientsController();
    DoctorsController doctorsController = new DoctorsController();
    PrioritiesController prioritiesController = new PrioritiesController();

    TextArea description = new TextArea("Описание");
    TextField validity = new TextField("Срок действия (кол-во дней)");
    NativeSelect<DoctorsEntity> doctor = new NativeSelect<>("Доктор");
    NativeSelect<PatientsEntity> patient = new NativeSelect<>("Пациент");
    NativeSelect<PrioritiesEntity> priority = new NativeSelect<>("Приоритет");

    public RecipesDetailCard(String caption, RecipesEntity recipesEntity) {
        super(caption);
        setWidth("50%");
        setHeight("50%");
        setModal(true);

        doctor.setItemCaptionGenerator(DoctorsEntity::getSurname);
        patient.setItemCaptionGenerator(PatientsEntity::getSurname);
        priority.setItemCaptionGenerator(PrioritiesEntity::getName);
        List<DoctorsEntity> doctorsEntities = doctorsController.findAll();
        doctor.setItems(doctorsEntities);
        List<PatientsEntity> patientsEntities = patientsController.findAll();
        patient.setItems(patientsEntities);
        List<PrioritiesEntity> prioritiesEntities = prioritiesController.findAll();
        priority.setItems(prioritiesEntities);
        description.setWidth("100%");
        validity.setWidth("100%");
        doctor.setWidth("100%");
        patient.setWidth("100%");
        priority.setWidth("100%");
        if (recipesEntity != null) {
            description.setValue(recipesEntity.getDescription());
            doctor.setValue(recipesEntity.getDoctor());
            patient.setValue(recipesEntity.getPatient());
            priority.setValue(recipesEntity.getPriority());
            validity.setVisible(false);
            Button updateButton = new Button("Ок");
            updateButton.addClickListener((Button.ClickListener) clickEvent -> {
                if (binder.isValid()) {
                    recipesEntity.setDescription(description.getValue());
                    recipesEntity.setDoctor(doctor.getValue());
                    recipesEntity.setPatient(patient.getValue());
                    recipesEntity.setPriority(priority.getValue());
                    recipesController.update(recipesEntity);
                    close();
                }
            });
            buttonsBar.addComponent(updateButton);
        } else {
            Button addButton = new Button("Ок");
            addButton.addClickListener((Button.ClickListener) clickEvent -> {
                if (binder.isValid()) {
                    RecipesEntity newRecipe = new RecipesEntity(description.getValue(), patient.getValue(), doctor.getValue(), priority.getValue(), validity.getValue());
                    recipesController.persist(newRecipe);
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
        layout.addComponent(description);
        layout.addComponent(validity);
        layout.addComponent(doctor);
        layout.addComponent(patient);
        layout.addComponent(priority);
        setContent(layout);
    }

    private void bindingFields() {
        binder.forField(description)
                .withValidator(new RegexpValidator(ENTER_VALID_TEXT, TEXT_FORMAT))
                .asRequired(IS_REQUIRED)
                .bind(RecipesEntity::getDescription, RecipesEntity::setDescription);

        binder.forField(patient)
                .asRequired(IS_REQUIRED);

        binder.forField(doctor)
                .asRequired(IS_REQUIRED);

        binder.forField(priority)
                .asRequired(IS_REQUIRED);

        binder.forField(validity)
                .withValidator(str -> str.length() < 3, "Не более 2 цифр")
                .withConverter(new StringToLongConverter("Введите цифры"))
                .asRequired(IS_REQUIRED)
                .bind(RecipesEntity::getUniqueId, RecipesEntity::setUniqueId);

    }
}
