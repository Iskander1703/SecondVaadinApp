package iskander.tabaev.admin.component;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.*;
import iskander.tabaev.admin.dto.ShowDto;
import iskander.tabaev.admin.services.ShowDtoService;

public class ShowEditorWindow extends Window {


    private Button cancelButton = new Button("Отмена");

    private Button saveButton = new Button("Сохранить");

    private Binder<ShowDto> binder = new Binder<>(ShowDto.class);

    private final ShowDtoService showDtoService;

    private ShowDto showDto = new ShowDto();

    public ShowEditorWindow(ShowDtoService showDtoService) {
        this.showDtoService = showDtoService;

        VerticalLayout windowLayout = new VerticalLayout();
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addComponent(cancelButton);
        buttonLayout.addComponent(saveButton);


        TextField showId = new TextField("ID мероприятия");
        TextField name = new TextField("Название мероприятия");
        TextField city = new TextField("Город");
        TextField AgeRestriction = new TextField("Возрастное ограничение");

        binder.bind(showId, ShowDto::getShowIdText, ShowDto::setShowIdText);
        binder.bind(name, ShowDto::getName, ShowDto::setName);
        binder.bind(city, ShowDto::getCity, ShowDto::setCity);
        binder.bind(AgeRestriction, ShowDto::getAgeRestrictionText, ShowDto::setAgeRestrictionText);
        binder.readBean(showDto);

//        saveButton.addClickListener(event -> {
//            try {
//                binder.writeBean(showDto);
//            } catch (ValidationException e) {
//                throw new RuntimeException(e);
//            }
//            showDtoService.saveShow(showDto);
//        });


        windowLayout.addComponent(showId);
        windowLayout.addComponent(name);
        windowLayout.addComponent(city);
        windowLayout.addComponent(AgeRestriction);
        windowLayout.addComponent(buttonLayout);
        this.setContent(windowLayout);


    }

    //Метод который устанавливает в Биндер нужное значение
    public final void editShow(ShowDto s) {
        final boolean persisted = s.getShowId() != 0;
        if (persisted) {
            // Find fresh entity for editing
            this.showDto = showDtoService.findShowById(s.getShowId());
        } else {
            this.showDto = s;
        }
        binder.readBean(this.showDto);
    }

    public Button getCancelButton() {
        return cancelButton;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Binder<ShowDto> getBinder() {
        return binder;
    }

    public ShowDto getShowDto() {
        return showDto;
    }
}
