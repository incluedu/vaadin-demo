package net.lustenauer.vaadindemo.gui;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import net.lustenauer.vaadindemo.dao.PlcRepository;
import net.lustenauer.vaadindemo.model.Plc;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class PlcEditor extends VerticalLayout implements KeyNotifier {
    private final PlcRepository repository;

    private Plc plc;

    TextField name = new TextField("Name");
    TextArea description = new TextArea("Description");
    TextField address = new TextField("PLC Address(IP)");
    TextField db = new TextField("PLC DB with data");
    TextField port = new TextField("PLC TCP port");
    TextField rack = new TextField("PLC rack");
    TextField slot = new TextField("PLC slot");
    Checkbox active = new Checkbox("PLC active");

    Button btnSave = new Button("Save", VaadinIcon.CHECK.create());
    Button btnCancel = new Button("Cancel");
    Button btnDelete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(btnSave, btnCancel, btnDelete);

    Binder<Plc> binder = new Binder<>(Plc.class);

    private ChangeHandler changeHandler;


    @Autowired
    public PlcEditor(PlcRepository repository) {
        this.repository = repository;

        description.setWidth("40em");

        add(name, description, address,db, port, rack, slot, active, actions);

        binder.bind(name, Plc::getName, Plc::setName);
        binder.bind(description, Plc::getDescription, Plc::setDescription);
        binder.bind(address, Plc::getAddress, Plc::setAddress);
        binder.forField(db).bind(plc -> Integer.toString(plc.getDb()), (plc, formValue) -> plc.setDb(Integer.valueOf(formValue)));
        binder.forField(port).bind(plc -> Integer.toString(plc.getPort()), (plc, formValue) -> plc.setPort(Integer.valueOf(formValue)));
        binder.forField(rack).bind(plc -> Integer.toString(plc.getRack()), (plc, formValue) -> plc.setRack(Integer.valueOf(formValue)));
        binder.forField(slot).bind(plc -> Integer.toString(plc.getSlot()), (plc, formValue) -> plc.setSlot(Integer.valueOf(formValue)));
        binder.bind(active, Plc::isActive, Plc::setActive);

        //binder.bindInstanceFields(this);

        setSpacing(true);

        btnSave.getElement().getThemeList().add("primary");
        btnDelete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        btnSave.addClickListener(e -> save());
        btnDelete.addClickListener(e -> delete());
        btnCancel.addClickListener(e -> editPlc(plc));
        setVisible(false);
    }

    void delete() {
        repository.delete(plc);
        changeHandler.onChange();
    }

    void save() {
        repository.save(plc);
        changeHandler.onChange();
    }

    public final void editPlc(Plc p) {
        if (p == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = p.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            plc = repository.findById(p.getId()).get();
        } else {
            plc = p;
        }
        btnCancel.setVisible(persisted);

        // Bind plc properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(plc);

        setVisible(true);

        // Focus first name initially
        name.focus();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }


}

