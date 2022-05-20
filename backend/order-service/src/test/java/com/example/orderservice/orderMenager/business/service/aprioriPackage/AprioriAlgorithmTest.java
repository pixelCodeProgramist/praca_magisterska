package com.example.orderservice.orderMenager.business.service.aprioriPackage;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
class AprioriAlgorithmTest {
    List<List<String>> inputs = new ArrayList<>();

    @BeforeEach
    public void init() {
        this.inputs = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("Beach", "Sunshine", "Holiday")),
                new ArrayList<>(Arrays.asList("Sand", "Beach")),
                new ArrayList<>(Arrays.asList("Sunshine", "Beach", "Ocean")),
                new ArrayList<>(Arrays.asList("Ocean", "People", "Beach", "Sunshine")),
                new ArrayList<>(Arrays.asList("Holiday", "Sunshine"))
        ));
    }

    @Test
    public void giveCorrectResultOfAllAlgorithm() {
        AprioriAlgorithm aprioriAlgorithm = new AprioriAlgorithm(inputs, 0.4, 0.7);
        List<AssociationRule> result = aprioriAlgorithm.solve();
        Assertions.assertEquals(8, result.size());
        Assertions.assertEquals(0, result.stream().filter(resultOne -> resultOne.getConfidence() < 0.7).toList().size());
        Assertions.assertEquals(0, result.stream()
                .filter(resultOne -> resultOne.getMainInt() < 0.4 * inputs.size() || resultOne.getSecondaryInt() < 0.4 * inputs.size()
                ).toList().size());

        AssociationRule associationRule = result.stream().filter(resultOne -> new AssociationRule(
                        new ArrayList<>(List.of("Sunshine")),
                        new ArrayList<>(List.of("Beach")),
                        0.75,
                        3,
                        4
                ).equals(resultOne)
        ).findFirst().get();

        Assertions.assertNotNull(associationRule);
    }

    @Test
    public void giveCorrectResultOfAssociationRule() {
        List<Combination> combinations = new ArrayList<>(List.of(
                new Combination(new ArrayList<>(List.of("Sunshine")), 8),
                new Combination(new ArrayList<>(List.of("Beach")), 6),
                new Combination(new ArrayList<>(List.of("Beach","Sunshine")), 3)
        ));

        AssociationRuleManager associationRuleManager = new AssociationRuleManager(combinations, 0.5);
        List<AssociationRule> results = associationRuleManager.solve();
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals(0.5, results.get(0).getConfidence());
        associationRuleManager = new AssociationRuleManager(combinations, 0.2);
        results = associationRuleManager.solve();
        Assertions.assertEquals(2, results.size());
        Assertions.assertEquals(0.375, results.get(0).getConfidence());
    }
}