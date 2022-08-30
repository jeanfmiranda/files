package application;

import entities.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner (System.in);

        List<Product> list = new ArrayList<>();

        System.out.print("Enter the file path: ");
        String sourceFileStr = sc.nextLine();

        File sourceFile = new File(sourceFileStr);

        String sourceFolder = sourceFile.getParent();

        boolean success = new File(sourceFolder + "\\out").mkdir();

        String targetFileStr = sourceFolder + "\\out\\summary.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFile))){
            String line = br.readLine();
            while (line != null) {
                String[] item = line.split(",");
                String name = item[0];
                double price = Double.parseDouble(item[1]);
                int quantity = Integer.parseInt(item[2]);

                Product p = new Product(name, price, quantity);
                list.add(p);

                line = br.readLine();
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFileStr))) {
                for (Product item : list) {
                    bw.write(item.getName() + "," + String.format("%.2f", item.totalPrice()));
                    bw.newLine();
                }
                System.out.println("SUCCESS!");
            }

            catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }

        }
        catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        sc.close();
    }
}