package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class PendulumCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Double> allPeriods = new ArrayList<>();
        System.out.println("Wpisz '0' aby wyjść.");

        // Obliczenie okresów drgań wahadła

        double t10_averagePeriod = 0;
        double uat10_periodUncertainty = 0;
        while (true) {
            System.out.println("\nPodaj wynik pomiaru okresu 'T' wahadła w sekundach:");
            double period_T = scanner.nextDouble();
            if (period_T == 0) {
                break;
            }
            allPeriods.add(period_T);
            t10_averagePeriod = allPeriods.stream().mapToDouble(i -> i).sum();
            t10_averagePeriod = t10_averagePeriod / allPeriods.size();
            System.out.println("Średnia arytmetyczna okresów: " + String.format("%.4f", t10_averagePeriod) + " s.");
            List<Double> sumList = new ArrayList<>();
            for (double measurement : allPeriods) {
                sumList.add((measurement - t10_averagePeriod) * (measurement - t10_averagePeriod));
            }
            double standardDeviation = Math.sqrt(
                    sumList.stream().mapToDouble(i -> i).sum() / (sumList.size() - 1));
            uat10_periodUncertainty = standardDeviation / Math.sqrt(sumList.size());
            System.out.println("Niepewność pomiarowa okresu 'ua(t10)' to: "
                    + String.format("%.4f", uat10_periodUncertainty) + " s.");
        }

        double T_period = t10_averagePeriod / 10;
        double uT_periodUncertainty = uat10_periodUncertainty / 10;
        System.out.println("Okres 'T' wynosi: " + String.format("%.4f", T_period) + " s.");
        System.out.println("Niepewność okresu 'uT' wynosi: " +
                String.format("%.4f", uT_periodUncertainty) + " s.");

        // Obliczenie długości nici

        System.out.println("\nPodaj dokładność narzędzia pomiaru długości nici w cm:");
        double toolPrecision = scanner.nextDouble();
        List<Double> allStringLengths = new ArrayList<>();
        System.out.println("Wpisz '0' aby wyjść.");
        double averageStringLength = 0;
        double lengthUncertainty = 0;
        while (true) {
            System.out.println("\nPodaj wynik pomiaru długości nici 'h' w cm:");
            double length = scanner.nextDouble();
            if (length == 0) break;
            allStringLengths.add(length);
            averageStringLength = allStringLengths.stream().mapToDouble(i -> i).sum();
            averageStringLength = averageStringLength / allStringLengths.size();
            System.out.println("Średnia arytmetyczna pomiarów: " + String.format("%.4f", averageStringLength) + " cm.");
            double maxH = allStringLengths.stream().mapToDouble(i -> i).max().orElseThrow(NoSuchElementException::new);
            double minH = allStringLengths.stream().mapToDouble(i -> i).min().orElseThrow(NoSuchElementException::new);
            double deltaH = maxH - minH;
            double uncertainty1 = deltaH / sqrt(3); // niepewność standardowa
            double uncertainty2 = 0.5 * toolPrecision / sqrt(3); // niepewność narzędzia pomiaru
            lengthUncertainty = sqrt(uncertainty1 * uncertainty1 + uncertainty2 * uncertainty2);
            System.out.println("Niepewność pomiaru długości nici 'u(h)': " + String.format("%.4f", lengthUncertainty) + " cm.");
        }

        // Obliczenie średnicy kulki

        System.out.println("\nPodaj dokładność narzędzia pomiaru kulki w mm:");
        toolPrecision = scanner.nextDouble();
        List<Double> allSphereDiametres = new ArrayList<>();
        System.out.println("Wpisz '0' aby wyjść.");
        double averageSphereDiameter = 0;
        double diameterUncertainty = 0;
        while (true) {
            System.out.println("\nPodaj wynik pomiaru średnicy kulki 'd' w mm:");
            double length = scanner.nextDouble();
            if (length == 0) break;
            allSphereDiametres.add(length);
            averageSphereDiameter = allSphereDiametres.stream().mapToDouble(i -> i).sum();
            averageSphereDiameter = averageSphereDiameter / allSphereDiametres.size();
            System.out.println("Średnia arytmetyczna pomiarów: " + String.format("%.4f", averageSphereDiameter) + " mm.");
            double maxH = allSphereDiametres.stream().mapToDouble(i -> i).max().orElseThrow(NoSuchElementException::new);
            double minH = allSphereDiametres.stream().mapToDouble(i -> i).min().orElseThrow(NoSuchElementException::new);
            double deltaH = maxH - minH;
//            System.out.println("deltaH: " + deltaH);
            double uncertainty1 = deltaH * 10 / sqrt(3); // niepewność standardowa
//            System.out.println("uncertainty1: " + String.format("%.4f", uncertainty1));
            double uncertainty2 = toolPrecision / sqrt(3); // niepewność narzędzia pomiaru
//            System.out.println("uncertainty2: " + String.format("%.4f", uncertainty2));
            diameterUncertainty = sqrt((uncertainty1 * uncertainty1) + (uncertainty2 * uncertainty2));
            System.out.println("Niepewność pomiaru średnicy kulki 'u(d)': " + String.format("%.4f", diameterUncertainty) + " mm.");
        }

        // Obliczenie całkowitej długości wahadła

        double totalLength = averageStringLength + (0.5 * (averageSphereDiameter / 10));
        System.out.println("Długość całkowita to: " + String.format("%.4f", totalLength));

        double totalLengthUncertainty = sqrt(lengthUncertainty * lengthUncertainty
                + diameterUncertainty * diameterUncertainty);
        System.out.println("Niepewność całkowita pomiaru to: " +
                String.format("%.4f", totalLengthUncertainty) + "cm.");

        // Obliczenie przyspieszenia ziemskiego

        System.out.println("\nPrzyspieszenie ziemskie 'g'" +
                "na podstawie wzoru g = (4pi^2*l)/(T^2).");
        totalLength = totalLength * 0.01; //konwersja z cm na m
        totalLengthUncertainty = totalLengthUncertainty * 0.01;
        double acceleration_g = (4 * 3.14159 * 3.14159 * totalLength) / (T_period * T_period);
        System.out.println("Wynik 'g' wynosi: " + String.format("%.4f", acceleration_g) + " m/s^2");

        double accelUncertainty = acceleration_g * Math.sqrt(
                (totalLengthUncertainty / totalLength) * (totalLengthUncertainty / totalLength) +
                        (2 * uT_periodUncertainty / T_period) * (2 * uT_periodUncertainty / T_period));
        System.out.println("Niepewność przyspieszenia wynosi: " +
                String.format("%.2f", accelUncertainty) + " m/s^2");

    }
}
