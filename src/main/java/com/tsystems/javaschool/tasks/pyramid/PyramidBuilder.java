package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */

    public int[][] buildPyramid(List<Integer> inputNumbers) {
        try {
            if (inputNumbers.contains(null))
                throw new CannotBuildPyramidException();
            int level = setup(new ArrayList<>(inputNumbers));
            return makePyramid(inputNumbers, level);
        }   catch (OutOfMemoryError e) {
            throw new CannotBuildPyramidException();
        }

        //return new int[0][0];
    }

    public int[][] makePyramid(List<Integer> array, int numberOfLevels) {
        ArrayList<Integer> copyArray = new ArrayList<>(array);
        int lengthOfPyramidArray = 2 * (numberOfLevels - 1) + 1;
        int firstPosition = numberOfLevels - 1;
        int distanceBetweenNumbers = 2;
        int[][] pyramid = new int[numberOfLevels][lengthOfPyramidArray];
        for (int i = 1; i <= numberOfLevels; i++) {
            int currentPosition = firstPosition;
            int[] levelArray = new int[lengthOfPyramidArray];
            for (int k = 1; k <= i; k++) {
                int tmpElement = minInList(copyArray);
                levelArray[currentPosition] = tmpElement;
                currentPosition += distanceBetweenNumbers;
            }
            firstPosition--;
            pyramid[i - 1] = levelArray;
        }
        return pyramid;
    }

    public int setup(List<Integer> array) {
        int pyramidLevel = 1;
        try {
            while (array.size() != 0) {
                for (int i = 0; i < pyramidLevel; i++) {
                    array.remove(0);

                }
                pyramidLevel++;
            }
            pyramidLevel--;
        } catch (Exception e) {
            throw new CannotBuildPyramidException();
        }
        return pyramidLevel;

    }

    public int minInList(ArrayList<Integer> array) {
        int min = array.get(0);
        for (int i = 0; i < array.size(); i++) {
            int tmp = array.get(i);
            if (tmp < min)
                min = tmp;

        }
        array.remove(array.indexOf(min));
        return min;
    }

}