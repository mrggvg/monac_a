package io.futexor.ir.tac;

public class Quadruple {

    private final Operation op;
    private final String arg1, arg2, target;

    public Quadruple(Operation op, String arg1, String arg2, String target) {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.target = target;
    }

}
