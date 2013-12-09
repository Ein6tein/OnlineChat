package lv.tsi;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyVaadinApplication extends UI implements Button.ClickListener{

    private Button send;
    private Button login;
    private TextField message;
    private TextArea chat;
    private List<String> userList = new ArrayList<String>();
    private DateFormat format = new SimpleDateFormat("[dd-MMM-yy hh:mm:ss]    ");

    @Override
    public void init(VaadinRequest request) {
        try {
            DriverManager.getConnection("jdbc:hsqldb:mem:.", "SA", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        GridLayout layout = new GridLayout(2, 2);
        layout.setWidth(100, Unit.PERCENTAGE);
        layout.setHeight(100, Unit.PERCENTAGE);

        layout.setRowExpandRatio(0, 0.98f);
        layout.setRowExpandRatio(1, 0.02f);
        layout.setColumnExpandRatio(0, 0.8f);
        layout.setColumnExpandRatio(1, 0.2f);

        chat = new TextArea();
        chat.setWidth(100, Unit.PERCENTAGE);
        chat.setHeight(100, Unit.PERCENTAGE);
        chat.setValue("Welcome to online chat!");
        chat.setReadOnly(true);

        Table users = new Table();
        users.setWidth(100, Unit.PERCENTAGE);
        users.setHeight(100, Unit.PERCENTAGE);
        users.addContainerProperty("users", String.class, null, "Users", null, Table.Align.LEFT);
        users.addItem(new Object[]{"test User"});

        message = new TextField();
        message.setWidth(100, Unit.PERCENTAGE);
        message.setInputPrompt("Enter Your message here");

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setWidth(100, Unit.PERCENTAGE);
        buttons.setHeight(100, Unit.PERCENTAGE);

        send = new Button("Send");
        send.setId("send");
        send.setWidth(100, Unit.PERCENTAGE);
        send.setHeight(100, Unit.PERCENTAGE);
        send.addClickListener(this);

        message.addShortcutListener(new Button.ClickShortcut(send, ShortcutAction.KeyCode.ENTER));

        login = new Button("Login");
        login.setId("login");
        login.setWidth(100, Unit.PERCENTAGE);
        login.setHeight(100, Unit.PERCENTAGE);
        login.addClickListener(this);

        buttons.addComponent(send, 0);
        buttons.addComponent(login, 1);

        layout.addComponent(chat, 0, 0);
        layout.addComponent(users, 1, 0);
        layout.addComponent(message, 0, 1);
        layout.addComponent(buttons, 1, 1);
        setContent(layout);
    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        String id = event.getButton().getId();

        if (id.equals(send.getId())) {
            if (message.getValue() != null && !message.getValue().isEmpty()) {
                chat.setReadOnly(false);
                chat.setValue(chat.getValue() + "\n" + format.format(new Date()) + message.getValue());
                chat.setReadOnly(true);
                message.setValue("");
            }
        }
        if (id.equals(login.getId())) {
        }
    }
}
