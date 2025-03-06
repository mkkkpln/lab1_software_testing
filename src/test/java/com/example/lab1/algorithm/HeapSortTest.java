package com.example.lab1.algorithm;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class HeapSortTest {

    @Test
    @DisplayName("Сортировка массива со случайными числами")
    void testSortRandomArray() {
        int[] arr = {12, 11, 13, 5, 6, 7};
        int[] expected = {5, 6, 7, 11, 12, 13};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Сортировка пустого массива")
    void testEmptyArray() {
        int[] arr = {};
        int[] expected = {};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Сортировка массива из одного элемента")
    void testSingleElementArray() {
        int[] arr = {42};
        int[] expected = {42};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Сортировка уже отсортированного массива")
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Сортировка массива, отсортированного в обратном порядке")
    void testReverseSortedArray() {
        int[] arr = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Сортировка массива с дубликатами")
    void testArrayWithDuplicates() {
        int[] arr = {4, 5, 3, 4, 2, 1, 3};
        int[] expected = {1, 2, 3, 3, 4, 4, 5};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Сортировка массива с отрицательными числами")
    void testArrayWithNegativeNumbers() {
        int[] arr = {-3, -1, -2, -4, -5};
        int[] expected = {-5, -4, -3, -2, -1};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }

    @Test
    @DisplayName("Сортировка массива с отрицательными, положительными числами и нулем")
    void testArrayWithMixedNumbers() {
        int[] arr = {-3, 7, 0, -2, 5, 1};
        int[] expected = {-3, -2, 0, 1, 5, 7};
        HeapSort heapSort = new HeapSort();

        heapSort.sort(arr);

        assertArrayEquals(expected, arr);
    }
}