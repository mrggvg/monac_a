package io.futexor;

import io.futexor.ir.ast.Node;

public class Printer implements Node.Visitor<String> {

    public String print(Node node) {
        return node.accept(this);
    }

    @Override
    public String visitLiteral(Node.Literal n) {
        return n.value + "";
    }

    @Override
    public String visitBinary(Node.Binary n) {
        String left = n.left.accept(this);
        String right = n.right.accept(this);
        return "(" + left + " " + n.op + " " + right + ")";
    }
}
