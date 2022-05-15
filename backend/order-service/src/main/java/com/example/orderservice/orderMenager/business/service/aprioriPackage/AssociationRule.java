package com.example.orderservice.orderMenager.business.service.aprioriPackage;

import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class AssociationRule {
    private List<String> main;
    private int mainInt;
    private List<String> secondary;
    private int secondaryInt;
    private double confidence;

    public AssociationRule(List<String> main, List<String> secondary, double confidence, int mainInt, int secondaryInt) {
        this.main = main;
        this.mainInt = mainInt;
        this.secondary = secondary;
        this.secondaryInt = secondaryInt;
        this.confidence = confidence;
    }

    public void setSecondaryInt(int secondaryInt) {
        this.secondaryInt = secondaryInt;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssociationRule that = (AssociationRule) o;
        return Objects.equals(main, that.main) && Objects.equals(secondary, that.secondary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(main, secondary, confidence);
    }

    @Override
    public String toString() {
        return "AssociationRule{" +
                 main + " (" +mainInt+")"+
                " -> " + secondary + " (" +secondaryInt+")"+
                " => " + confidence +
                '}';
    }
}
