package io.futexor;

import io.futexor.ir.ast.Node;

public class Printer implements Node.Visitor<String> {

    public String print(Node node) {
        return node.accept(this);
    }

    @Override
    public String visitLiteral(Node.Literal n) {
        return "";
    }

    @Override
    public String visitBinary(Node.Binary n) {
        return "";
    }
}
