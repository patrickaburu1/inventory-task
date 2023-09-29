package com.patrick.inventorymanagementtask.security;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @author patrick on 2/8/20
 * @project  inventory
 */
@Component
public class TimeProvider implements Serializable {

    private static final long serialVersionUID = -3301695478208950415L;

    public Date now() {
        return new Date(System.currentTimeMillis());
    }
}
