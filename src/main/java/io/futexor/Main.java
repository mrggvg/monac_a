package io.futexor;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner("((1 + 2) * (3 + 4)) % 5");
        ArrayList<Token> tokens = scanner.scan();

        for (var t : tokens) System.out.println(t);

    }
}