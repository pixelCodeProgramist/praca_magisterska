package com.example.orderservice.orderMenager.business.service.aprioriPackage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AssociationRuleManager {

    private final List<Combination> prevResults;
    private final List<AssociationRule> associationRules;
    private final double confidence;

    public AssociationRuleManager(List<Combination> prevResults, double confidence) {
        this.prevResults = prevResults;
        this.confidence = confidence;
        this.associationRules = new ArrayList<>();
    }

    public List<AssociationRule> solve() {

        int maxLevel = this.prevResults.get(this.prevResults.size() - 1).getProducts().size();

        for (int minLevel = 2; minLevel <= maxLevel; minLevel++) {
            int finalMinLevel = minLevel;

            List<Combination> firstSearchList = this.prevResults.stream()
                    .filter(combination -> combination.getProducts().size() == finalMinLevel).toList();

           if(minLevel == 3)
               System.out.println();

            for (Combination first : firstSearchList) {
                for (String product : first.getProducts()) {
                    List<String> main = List.of(product);
                    List<String> domain = first.getProducts().stream()
                            .filter(pr -> !main.contains(pr))
                            .collect(Collectors.toList());
                    AssociationRule associationRule1 = new AssociationRule(domain, main, 0, first.getInstance(),0);
                    int numerator = first.getInstance();

                    fillAssociationRules(domain, numerator, associationRule1);

                    AssociationRule associationRule2 = new AssociationRule(main, domain, 0, first.getInstance(),0);
                    fillAssociationRules(main, numerator, associationRule2);

                }
            }
        }

        return associationRules;
    }

    private void fillAssociationRules(List<String> main, double numerator, AssociationRule associationRule) {
        if (!associationRules.contains(associationRule)){
            List<Combination> secondSearchList = this.prevResults.stream()
                    .filter(combination -> combination.getProducts().size() == (main.size())).toList();
            Optional<Combination> denominator = secondSearchList.stream().filter(combination ->
                    new HashSet<>(combination.getProducts()).containsAll(main)).findFirst();
            if(denominator.isPresent()) {
                double result = numerator /(double) denominator.get().getInstance();
                if(result>1) result = 1;
                if(result>=confidence) {
                    associationRule.setSecondaryInt(denominator.get().getInstance());
                    associationRule.setConfidence(result);

                    associationRules.add(associationRule);
                }

            }

        }
    }
}

