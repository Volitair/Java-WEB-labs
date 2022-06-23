package com.example.javaweblabs.context;

import com.example.javaweblabs.command.CommandHandler;
import com.example.javaweblabs.command.abstraction.Command;
import com.example.javaweblabs.command.implementation.*;
import com.example.javaweblabs.service.abstraction.*;
import com.example.javaweblabs.service.implementation.*;

import java.util.HashMap;

public class ApplicationContext {
    private static UserService userService;
    private static OrderService orderService;
    private static AuthService authService;
    private static CashAccountService cashAccountService;
    private static UserResponsesService userResponsesService;
    private static CommandHandler commandHandler;
    private static final HashMap<String, Command> commands = new HashMap<>();
    private static AccessManager accessManager;

    public static void init() {
        userService = new UserServiceImpl();
        orderService = new OrderServiceImpl();
        authService = new AuthServiceImpl(userService);
        cashAccountService = new CashAccountServiceImpl();
        userResponsesService = new UserResponsesServiceImpl();
        initialiseCommands();
        commandHandler = new CommandHandler(commands);
        accessManager = new AccessManager();
    }

    public static CommandHandler getCommandHandler() {
        return commandHandler;
    }

    private static void initialiseCommands() {
        commands.put("/home", new HomeCommand());
        commands.put("/user/home", new UserHomeCommand(userService, orderService, cashAccountService));
        commands.put("/manager/home", new ManagerHomeCommand());
        commands.put("/master/home", new MasterHomeCommand(orderService));
        commands.put("/orders", new OrdersCommand(orderService, userService));
        commands.put("/orders/create-form", new CreateOrderFormCommand());
        commands.put("/orders/create", new CreateOrderCommand(orderService));
        commands.put("/orders/search", new FilteringOrdersCommand(orderService));
        commands.put("/orders/edit-form", new EditOrderFormCommand(orderService, userService));
        commands.put("/orders/edit", new EditOrderCommand());
        commands.put("/orders/edit/manager", new EditOrderManagerCommand(orderService));
        commands.put("/orders/edit/master", new EditOrderMasterCommand(orderService));
        commands.put("/responses", new ResponsesCommand(userResponsesService));
        commands.put("/responses/create-form", new CreateResponseFormCommand());
        commands.put("/responses/create", new CreateResponseCommand(orderService, userResponsesService));
        commands.put("/users", new UsersCommand(userService));
        commands.put("/users/cash-account/top-up-form", new TopUpBalanceFormCommand(userService, cashAccountService));
        commands.put("/users/cash-account/top-up", new TopUpBalanceCommand(cashAccountService));
        commands.put("/orders/pay", new PayOrderCommand(orderService));
        commands.put("/login", new LoginCommand(authService));
        commands.put("/logout", new LogoutCommand());
    }

    public static AccessManager getAccessManager() {
        return accessManager;
    }
}
