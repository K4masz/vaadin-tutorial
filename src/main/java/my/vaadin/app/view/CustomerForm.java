package my.vaadin.app.view;

import com.vaadin.data.Binder;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import my.vaadin.app.MyUI;
import my.vaadin.app.model.domain.Customer;
import my.vaadin.app.model.domain.CustomerStatus;
import my.vaadin.app.model.services.CustomerService;

public class CustomerForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private NativeSelect<CustomerStatus> status = new NativeSelect<>("Status");
    private DateField birthdate = new DateField("Birthday");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private CustomerService service = CustomerService.getInstance();
    private Customer customer;
    private MyUI myUI;

    private Binder<Customer> binder = new Binder<>(Customer.class);

    public CustomerForm(MyUI myUI) {
        this.myUI = myUI;
        setSizeUndefined();

        binder.bindInstanceFields(this);

        status.setItems(CustomerStatus.values());

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        save.addClickListener(e -> this.save());

        delete.addClickListener(e -> this.delete());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        addComponents(firstName, lastName, email, status, birthdate, buttons);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;

        binder.setBean(customer);

        delete.setVisible(customer.isPersisted());
        setVisible(true);
        firstName.selectAll();
    }

    private void delete() {
        service.delete(customer);
        myUI.updateList();
        setVisible(false);
    }

    private void save() {
        service.save(customer);
        myUI.updateList();
        setVisible(false);
    }
}
