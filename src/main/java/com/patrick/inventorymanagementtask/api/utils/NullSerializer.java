package com.patrick.inventorymanagementtask.api.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author patrick on 4/8/20
 * @project  inventory
 */
public class NullSerializer extends JsonSerializer<Object> {
    // Generate the preferred JSON value
    @Override
    public void serialize(Object t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString("");
    }
}
