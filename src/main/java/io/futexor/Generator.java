package io.futexor;

import io.futexor.ir.ast.Node;

import java.util.ArrayList;

public class Generator implements Node.Visitor<String> {


    public class Quad {
        String target, op, arg1, arg2;
        public Quad(String target, String op, String arg1, String arg2) {
            this.target = target;
            this.op = op;
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        @Override
        public String toString() {
            String a1 = arg1 == null ? "" : arg1;
            String a2 = arg2 == null ? "" : arg2;
            String tg = target == null ? "" : target;
            String opx = op == null ? "" : op;

            return String.format("| %-8s | %-8s | %-8s | %-8s |", tg, opx, a1, a2);
        }
    }

    int t = 1;
    ArrayList<Quad> quads = new ArrayList<>();

    public void gen(Node node) {
        node.accept(this);
    }


    private String newTemp() {
        return "t" + t++;
    }



    @Override
    public String visitLiteral(Node.Literal n) {
        String temp = newTemp();
        quads.add(new Quad(temp, "load", n.value + "", ""));
        return temp;
    }

    @Override
    public String visitBinary(Node.Binary n) {
        String left = n.left.accept(this);
        String right = n.right.accept(this);

        String temp = newTemp();
        String op = n.op + "";
        quads.add(new Quad(temp, op, left, right));
        return temp;
    }



}
