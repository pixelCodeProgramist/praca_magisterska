package com.example.orderservice.orderMenager.business.service.aprioriPackage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class AprioriAlgorithm {
    private final List<List<String>> input;

    private final List<String> distinctProduct;

    private final int supportCount;
    private final double confidence;

    public AprioriAlgorithm(List<List<String>> input, double minSupport, double confidence) {
        this.input = input;
        this.distinctProduct = this.input.stream()
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
        this.supportCount = (int) (minSupport *this.distinctProduct.size());
        this.confidence = confidence;
    }


    public List<AssociationRule> solve() {
        int highestSize = this.input.stream().max(Comparator.comparing(List::size)).get().size() - 1;
        List<Combination> combinations = getWordPermutations(this.distinctProduct, highestSize);
        AssociationRuleManager associationRuleManager = new AssociationRuleManager(combinations, confidence);
        return associationRuleManager.solve();
    }

    private List<Combination> getWordPermutations(List<String> distinctProducts, int highestSize) {
        List<Combination> results = new ArrayList<>();
        distinctProducts.forEach(product -> {
            int instance = countInstance(List.of(product));
            if(instance>=this.supportCount) results.add(new Combination(List.of(product), instance));
        });


        int indexFrom = 0;
        int indexTo = results.size();
        for (int h = 0; h < highestSize - 1; h++) {
            List<Combination> localStrings = new ArrayList(results).subList(indexFrom, indexTo);
            for (String product : distinctProducts) {
                for (Combination localString : localStrings) {
                    if (localString.getProducts().contains(product)) continue;
                    List<String> oneResult = new ArrayList<>(localString.getProducts());
                    oneResult.add(product);
                    if (!isSubListInList(results, oneResult)) {
                        int instance = countInstance(oneResult);
                        if (instance >= this.supportCount) results.add(new Combination(oneResult, instance));
                    }
                }
            }
            indexFrom = indexTo;
            indexTo = results.size();

        }

        return results;

    }

    private int countInstance(List<String> product) {
        int instance = 0;
        for(List<String> oneList: this.input) {
            if(new HashSet<>(oneList).containsAll(product)) instance++;
        }
        return instance;
    }

    private boolean isSubListInList(List<Combination> original, List<String> sub) {


        for (Combination orgList : original) {
            int num = 0;
            for (String str : sub) {
                if (orgList.getProducts().contains(str)) num++;
            }
            if (num == sub.size()) return true;
        }
        return false;
    }


}
