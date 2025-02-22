package com.zio.example.common.model;

import java.io.Serializable;

/**
 * @author Zio1037
 * @date 2025-02-21 15:50
 */

public class User implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

