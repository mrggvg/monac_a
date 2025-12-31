package io.futexor;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner("100 + 3 * (3 - 4)");
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

        Generator gen = new Generator();
        gen.gen(tree);


        System.out.println();

        for (var q : gen.quads) {
            System.out.println(q);
        }

    }

}