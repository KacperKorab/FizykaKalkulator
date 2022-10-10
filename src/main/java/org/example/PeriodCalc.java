package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PeriodCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Double> allPeriods = new ArrayList<>();
        System.out.println("Wpisz '0' aby wyjść.");
        while (true) {
            System.out.println("\nPodaj wynik pomiaru okresu 'T' wahadła w sekundach:");
            double period_T = scanner.nextDouble();
            if (period_T == 0) break;
            allPeriods.add(period_T);
            double averagePeriod = allPeriods.stream().mapToDouble(i -> i).sum();
            averagePeriod = averagePeriod / allPeriods.size();
            System.out.println("Średnia arytmetyczna pomiarów: " + String.format("%.2f", averagePeriod) + " s.");
            List<Double> sumList = new ArrayList<>();
            for (double measurement : allPeriods){
                sumList.add((measurement-averagePeriod)*(measurement-averagePeriod));
            }
            double uncertain_ST0 = Math.sqrt(
                    sumList.stream().mapToDouble(i -> i).sum()/(sumList.size()-1));
//            System.out.println("ST0: " + String.format("%.6f", uncertain_ST0) + " s.");
            double uT0 = uncertain_ST0/Math.sqrt(sumList.size());
            System.out.println("Niepewność pomiarowa typu a 'u(t)': " + String.format("%.6f", uT0) + " s.");
        }
    }
}
