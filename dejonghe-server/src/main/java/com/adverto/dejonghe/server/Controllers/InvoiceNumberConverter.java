package com.adverto.dejonghe.server.Controllers;

import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;

public class InvoiceNumberConverter implements Converter<String, Integer> {
    @Override
    public Result<Integer> convertToModel(String value, ValueContext context) {
        if (value == null || value.isEmpty()) {
            return Result.ok(null);
        }

        // Verwijder de "/"
        String cleaned = value.replace("/", "");

        try {
            return Result.ok(Integer.valueOf(cleaned));
        } catch (NumberFormatException e) {
            return Result.error("Ongeldig factuurnummer");
        }
    }

    @Override
    public String convertToPresentation(Integer value, ValueContext context) {
        if (value == null) {
            return "";
        }

        String str = value.toString();

        if (str.length() > 2) {
            return str.substring(0, 2) + "/" + str.substring(2);
        }

        return str;
    }
}
