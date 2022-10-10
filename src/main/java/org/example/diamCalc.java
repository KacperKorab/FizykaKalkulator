package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class diamCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dokładność narzędzia pomiaru w mm:");
        double toolPrecision = scanner.nextDouble();
        List<Double> allLengths = new ArrayList<>();
        System.out.println("Wpisz '0' aby wyjść.");
        while (true) {
            System.out.println("\nPodaj wynik pomiaru średnicy kulki 'd' w mm:");
            double length = scanner.nextDouble();
            if (length == 0) break;
            allLengths.add(length);
            double averageLength = allLengths.stream().mapToDouble(i -> i).sum();
            averageLength = averageLength / allLengths.size();
            System.out.println("Średnia arytmetyczna pomiarów: " + String.format("%.4f", averageLength) + " mm.");
            double maxH = allLengths.stream().mapToDouble(i -> i).max().orElseThrow(NoSuchElementException::new);
            double minH = allLengths.stream().mapToDouble(i -> i).min().orElseThrow(NoSuchElementException::new);
            double deltaH = maxH - minH;
            System.out.println("deltaH: " + deltaH);
            double uncertainty1 = deltaH / sqrt(3); // niepewność standardowa
            System.out.println("uncertainty1: " + String.format("%.4f", uncertainty1));
            double uncertainty2 = toolPrecision / sqrt(3); // niepewność narzędzia pomiaru
            System.out.println("uncertainty2: " + String.format("%.4f", uncertainty2));
            double lengthUncertainty = sqrt((uncertainty1 * uncertainty1) + (uncertainty2 * uncertainty2));
            System.out.println("Niepewność pomiaru średnicy kulki 'u(d)': " + String.format("%.4f", lengthUncertainty) + " mm.");
        }
    }
}
