package io.futexor;

import io.futexor.ir.ast.Node;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner("1 - (1 / 2) * 2");
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

        System.out.println();
        System.out.println(new AsmGenerator().gen(tree));

    }

}

class AsmGenerator implements Node.Visitor<String> {

    StringBuilder code = new StringBuilder();

    public String gen(Node node) {
        node.accept(this);
        return code.toString();
    }

    @Override
    public String visitLiteral(Node.Literal n) {
        code.append("PUSH ").append(n.value).append("\n");
        return "";
    }

    @Override
    public String visitBinary(Node.Binary n) {

        n.left.accept(this);
        n.right.accept(this);

        switch (n.op) {
            case ADD -> code
                    .append("POP A").append("\n")
                    .append("POP B").append("\n")
                    .append("ADD A, B").append("\n")
                    .append("PUSH A").append("\n");
            case SUB -> code
                    .append("POP A").append("\n")
                    .append("POP B").append("\n")
                    .append("SUB B, A").append("\n")
                    .append("PUSH B").append("\n");

            case MUL -> code
                    .append("POP A").append("\n")
                    .append("POP B").append("\n")
                    .append("MUL B").append("\n")
                    .append("PUSH A").append("\n");
            case DIV -> code
                    .append("POP B").append("\n")
                    .append("POP A").append("\n")
                    .append("DIV B").append("\n")
                    .append("PUSH A").append("\n");

            // a mod b = a − (a / b) ⋅ b
            case MOD -> {

            }
        }

        return "";
    }

}