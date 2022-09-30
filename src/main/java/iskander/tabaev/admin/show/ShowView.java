package iskander.tabaev.admin.show;


import com.vaadin.data.ValidationException;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import iskander.tabaev.admin.component.ShowEditorWindow;
import iskander.tabaev.admin.dto.ShowDto;
import iskander.tabaev.admin.services.ShowDtoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


@SpringComponent
@UIScope
@SpringView
public class ShowView extends VerticalLayout implements View {
    private final ShowDtoService showDtoService;


    private Grid<ShowDto> adminGrid = new Grid<>();
    private Button addButton = new Button("Добавить");

    private Button editButton = new Button("Редактировать");
    private Button deleteButton = new Button("Удалить");
    private HorizontalLayout actions = new HorizontalLayout(addButton, editButton, deleteButton);

    @Autowired
    public ShowView(ShowDtoService showDtoService) {

        this.showDtoService = showDtoService;


        adminGrid.setItems(showDtoService.getAllShowDto());
        adminGrid.addColumn(ShowDto::getShowId).setCaption("ID Мероприятия");
        adminGrid.addColumn(ShowDto::getName).setCaption("Название мероприятия");
        adminGrid.addColumn(ShowDto::getAgeRestriction).setCaption("Возрастное ограничение");
        adminGrid.addColumn(ShowDto::getCity).setCaption("Город");

        adminGrid.setHeight(100, Unit.PERCENTAGE);
        adminGrid.setWidth(100, Unit.PERCENTAGE);

        adminGrid.setItems(showDtoService.getAllShowDto());


        //Установка кнопок удалить и изменить в неактивное положение
        deleteButton.setEnabled(false);
        editButton.setEnabled(false);

        ShowEditorWindow showEditorWindow = new ShowEditorWindow(showDtoService);
        showEditorWindow.setHeight(50, Unit.PERCENTAGE);
        showEditorWindow.setWidth(20, Unit.PERCENTAGE);
        showEditorWindow.center();
        showEditorWindow.getCancelButton().addClickListener(event -> showEditorWindow.close());


        showEditorWindow.getSaveButton().addClickListener(event -> {

            try {
                showEditorWindow.getBinder().writeBean(showEditorWindow.getShowDto());
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
            showDtoService.saveShow(showEditorWindow.getShowDto());
            showEditorWindow.close();
            this.refreshData();
        });

        //Удаление текщуего мероприятия
        deleteButton.addClickListener(event -> {
            Optional<ShowDto> optionalShowDto = adminGrid
                    .getSelectionModel()
                    .getFirstSelectedItem();
            if (optionalShowDto.isEmpty()) {
                Notification.show("Выберите значение из таблицы", Notification.Type.HUMANIZED_MESSAGE);
            } else {
                showDtoService.deleteShowById(optionalShowDto.get());
               this.refreshData();
            }
        });

        //Изменение существующего мероприятия
        editButton.addClickListener(event -> {
            Optional<ShowDto> optionalShowDto = adminGrid
                    .getSelectionModel()
                    .getFirstSelectedItem();

            if (optionalShowDto.isEmpty()) {
                Notification.show("Выберите значение из таблицы", Notification.Type.HUMANIZED_MESSAGE);
            } else {
                showEditorWindow.editShow(adminGrid.getSelectionModel()
                        .getFirstSelectedItem()
                        .get());
                UI.getCurrent().addWindow(showEditorWindow);
            }
        });

        //Добавление нового шоу
        addButton.addClickListener(event -> {
                UI.getCurrent().addWindow(showEditorWindow);
        });


        //Дабл клик по таблице
        adminGrid.addItemClickListener(event ->
                {
                    if (event.getMouseEventDetails().isDoubleClick()) {
                        showEditorWindow.editShow(event.getItem());
                        UI.getCurrent().addWindow(showEditorWindow);
                    }
                }
        );

        //Одиночный клик по теблице
        adminGrid.asSingleSelect().addValueChangeListener(event ->
        {
            deleteButton.setEnabled(adminGrid.getSelectionModel()
                    .getFirstSelectedItem().isPresent());
            editButton.setEnabled(adminGrid.getSelectionModel()
                    .getFirstSelectedItem().isPresent());
        });


        this.addComponent(adminGrid);
        this.addComponent(actions);
    }


    //Перезагрузка списка всех мероприятий для того что бы
    //Показывать всегда актуальный список мероприятий
    private void refreshData() {
        adminGrid.setItems(showDtoService.getAllShowDto());
    }


}
