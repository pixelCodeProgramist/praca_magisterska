package com.example.orderservice.orderMenager.business.service.aprioriPackage;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
public class Combination {
    private List<String> products;
    private int instance;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combination that = (Combination) o;
        return instance == that.instance && Objects.equals(products, that.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, instance);
    }

    @Override
    public String toString() {
        return "Combination{" +
                "products=" + products +
                "->" + instance +
                '}';
    }
}
