package io.futexor;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner("((1 + 2) * (3 + 4)) % 5");
        ArrayList<Word> words = scanner.scan();

        for (var t : words) System.out.println(t);

        Parser parser = new Parser(words);
        var tree = parser.parse();

        System.out.println();
        System.out.println();
        System.out.println("Parse Tree");
        System.out.println();

        Printer printer = new Printer();
        String printedTree = printer.print(tree);

        System.out.println(printedTree);

    }

}