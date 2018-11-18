package net.lustenauer.vaadindemo.gui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import net.lustenauer.vaadindemo.dao.PlcRepository;
import net.lustenauer.vaadindemo.model.Plc;
import org.springframework.util.StringUtils;

@Route("test")
public class MainView extends VerticalLayout {

    private final PlcRepository repo;
    private final PlcEditor editor;

    private final Grid<Plc> grid;

    private final Button btnAddNew;
    private final TextField filter;

    public MainView(PlcRepository repo, PlcEditor editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Plc.class);
        this.filter = new TextField();
        this.btnAddNew = new Button("Add PLC", VaadinIcon.PLUS.create());

        HorizontalLayout actions = new HorizontalLayout(filter, btnAddNew);
        add(actions, grid, editor);

        //grid.setHeight("300px");
        grid.setColumns("id", "name", "address", "description","running");
        grid.getColumnByKey("id").setWidth("5em").setFlexGrow(0);
        grid.getColumnByKey("name").setWidth("20em").setFlexGrow(0);
        grid.getColumnByKey("address").setWidth("15em").setFlexGrow(0);
        grid.getColumnByKey("running").setWidth("7em").setFlexGrow(0);

        filter.setPlaceholder("Filter by name");

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listCustomers(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> editor.editPlc(e.getValue()));

        btnAddNew.addClickListener(e -> editor.editPlc(new Plc("", "", "", 10000, 102, 0, 1, true)));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        listCustomers(null);

    }

    void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else {
            grid.setItems(repo.findByNameStartsWithIgnoreCase(filterText));
        }

    }
}
