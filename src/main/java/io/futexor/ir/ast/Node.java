package io.futexor.ir.ast;

public interface Node {
    <R> R accept(Visitor<R> v);

    interface Visitor<R> {
        R visitLiteral(Literal n);

        R visitBinary(Binary n);
    }

    enum Operator {ADD, SUB, MUL, DIV, MOD}


    class Literal implements Node {
        public final int value;

        public Literal(int value) {
            this.value = value;
        }

        @Override
        public <R> R accept(Visitor<R> v) {
            return v.visitLiteral(this);
        }
    }

    class Binary implements Node {
        public final Operator op;
        public final Node left;
        public final Node right;

        public Binary(Operator op, Node left, Node right) {
            this.op = op;
            this.left = left;
            this.right = right;
        }

        @Override
        public <R> R accept(Visitor<R> v) {
            return v.visitBinary(this);
        }
    }

}
