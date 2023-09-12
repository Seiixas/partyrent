package br.ifnmg.edu.partyrent.modules.presentation.desktop.shared.utils;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SessionManager {
    private final HashMap<String, Object> objectHashMap = new HashMap<>();

    public SessionManager() {

    }

    public Object getObject(String key) {
        return this.objectHashMap.get(key);
    }

    public void setObject(String key, Object value) {
        this.objectHashMap.put(key, value);
    }
}
