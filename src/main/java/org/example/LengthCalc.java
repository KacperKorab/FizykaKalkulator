package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Math.sqrt;

public class LengthCalc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj dokładność narzędzia pomiaru długości nici w cm:");
        double toolPrecision = scanner.nextDouble();
        List<Double> allLengths = new ArrayList<>();
        System.out.println("Wpisz '0' aby wyjść.");
        while (true) {
            System.out.println("\nPodaj wynik pomiaru długości nici 'h' w cm:");
            double length = scanner.nextDouble();
            if (length == 0) break;
            allLengths.add(length);
            double averageLength = allLengths.stream().mapToDouble(i -> i).sum();
            averageLength = averageLength / allLengths.size();
            System.out.println("Średnia arytmetyczna pomiarów: " + String.format("%.4f", averageLength) + " cm.");
            double maxH = allLengths.stream().mapToDouble(i -> i).max().orElseThrow(NoSuchElementException::new);
            double minH = allLengths.stream().mapToDouble(i -> i).min().orElseThrow(NoSuchElementException::new);
            double deltaH = maxH - minH;
            double uncertainty1 = deltaH / sqrt(3); // niepewność standardowa
            double uncertainty2 = 0.5 * toolPrecision / sqrt(3); // niepewność narzędzia pomiaru
            double lengthUncertainty = sqrt(uncertainty1 * uncertainty1 + uncertainty2 * uncertainty2);
            System.out.println("Niepewność pomiaru długości nici 'u(h)': " + String.format("%.4f", lengthUncertainty) + " cm.");
        }
    }
}
