package util;

import buildings.interfaces.Space;

public final class Quicksort {

    private Quicksort() {}

    private static void sort(Space[] spaces, int start, int end) {
        if (start >= end) {
            return;
        }
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (spaces[i].getArea() >= spaces[cur].getArea())) {
                i++;
            }
            while (j > cur && (spaces[cur].getArea() >= spaces[j].getArea())) {
                j--;
            }
            if (i < j) {
                Space temp = spaces[i];
                spaces[i] = spaces[j];
                spaces[j] = temp;
                if (i == cur) {
                    cur = j;
                } else if (j == cur) {
                    cur = i;
                }
            }
        }
        sort(spaces, start, cur);
        sort(spaces, cur + 1, end);
    }


    public static void sort(Space[] spaces) {
        Quicksort.sort(spaces, 0, spaces.length - 1);
    }



}
