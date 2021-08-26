package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.List;

public class Subsequence {

    public boolean findString(List searchIt, List searchInIt) {
        ArrayList<String> compareTmp = new ArrayList<>(searchIt);
        ArrayList<String> searchInItIterableCopy = new ArrayList<>(searchInIt);
        for (int i = 0; i < searchInItIterableCopy.size(); i++) {
            String currentElement = searchInItIterableCopy.get(i);
            boolean res = substringSequenceString(compareTmp, currentElement);

            if (res == false)
                searchInIt.remove(currentElement);

            if (compareTmp.size() == 0) {
                String arrayOne = stringListToString(searchIt);
                String arrayTwo = stringListToString(searchInIt);

                if (arrayOne.equals(arrayTwo)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean findInt(List searchIt, List searchInIt) {
        ArrayList<Integer> compareTmp = new ArrayList<>(searchIt);
        ArrayList<Integer> searchInItIterableCopy = new ArrayList<>(searchInIt);
        for (int i = 0; i < searchInItIterableCopy.size(); i++) {
            Integer currentElement = searchInItIterableCopy.get(i);
            boolean res = substringSequenceInt(compareTmp, String.valueOf(currentElement));

            if (res == false)
                searchInIt.remove(currentElement);

            if ((compareTmp.size() == 0) && (searchIt.size() == searchInIt.size())) {
                String arrayOne = integerListToString(searchIt);
                String arrayTwo = integerListToString(searchInIt);

                if (arrayOne.equals(arrayTwo)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean find(List searchIt, List searchInIt) {

        if ((searchIt == null) || (searchInIt == null))
            throw new IllegalArgumentException();

        if (searchIt.isEmpty())
            return true;

        try {
            String check = (String) searchIt.get(0);
            return findString(searchIt, searchInIt);
        } catch (Exception e) {
            return findInt(searchIt, searchInIt);
        }
    }

    private boolean substringSequenceString(List<String> searchIt, String searchInIt) {
        int numberOfLettersInElement = searchInIt.length();
        StringBuilder tmp = new StringBuilder();
        if (searchIt.size() < numberOfLettersInElement)
            return false;
        for (int i = 0; i < numberOfLettersInElement; i++) {
            tmp.append(searchIt.get(i));
        }
        String compare = tmp.toString();
        if (compare.equals(searchInIt)) {
            for (int i = 0; i < numberOfLettersInElement; i++) {
                searchIt.remove(0);
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean substringSequenceInt(List<Integer> searchIt, String searchInIt) {
        int numberOfLettersInElement = searchInIt.length();
        StringBuilder tmp = new StringBuilder();
        if (searchIt.size() < numberOfLettersInElement)
            return false;
        for (int i = 0; i < numberOfLettersInElement; i++) {
            tmp.append(searchIt.get(i));
        }
        String compare = tmp.toString();
        if (compare.equals(searchInIt)) {
            for (int i = 0; i < numberOfLettersInElement; i++) {
                searchIt.remove(0);
            }
            return true;
        } else {
            return false;
        }
    }

    private String stringListToString(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
        }
        return result.toString();
    }

    private String integerListToString(List<Integer> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append(list.get(i));
        }
        return result.toString();
    }
}