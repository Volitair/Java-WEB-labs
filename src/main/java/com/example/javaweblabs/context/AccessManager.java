package com.example.javaweblabs.context;

import com.example.javaweblabs.enums.Role;

import java.util.*;

public class AccessManager {
    private final List<String> commonUri;
    private final Map<Role, List<String>> uriCollectionsByRole;

    public AccessManager() {
        uriCollectionsByRole = new HashMap<>();
        commonUri = Arrays.asList("/login", "/logout", "/home");
        ArrayList<String> userUri = new ArrayList<>(Arrays.asList("/user/home", "/orders/create-form", "/orders/create",
                "/responses/create-form", "/responses/create", "/orders/pay"));
        ArrayList<String> managerUri = new ArrayList<>(Arrays.asList("/manager/home", "/orders", "/orders/search",
                "/orders/edit-form", "/orders/edit", "/orders/edit/manager", "/responses", "/users",
                "/users/cash-account/top-up-form", "/users/cash-account/top-up"));
        ArrayList<String> masterUri = new ArrayList<>(Arrays.asList("/master/home", "/orders/edit-form", "/orders/edit",
                "/orders/edit/master"));
        uriCollectionsByRole.put(Role.USER, userUri);
        uriCollectionsByRole.put(Role.MASTER, masterUri);
        uriCollectionsByRole.put(Role.MANAGER, managerUri);
    }

    public boolean isAccessAllowed(String uri, Role role) {
        if (commonUri.contains(uri)) return true;
        if (role != null) {
            List<String> uriList = uriCollectionsByRole.get(role);
            if (uriList != null) return uriList.contains(uri);
        }
        return false;
    }
}
