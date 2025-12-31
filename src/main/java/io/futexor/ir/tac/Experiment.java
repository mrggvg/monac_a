package io.futexor.ir.tac;

import java.util.ArrayList;

public class Experiment {
    public static void main(String[] args) {


        ArrayList<Quad> quads = new ArrayList<>();
        quads.add(new Quad(Op.LE, "n", "1", "t0"));

        quads.add(new Quad(Op.CMP, "t0", "", "L0"));

        quads.add(new Quad(Op.JMP, null, null, "L1"));
        quads.add(new Quad(Op.LABEL, "L0", null, null));
        quads.add(new Quad(Op.RET, "1", null, null));
        quads.add(new Quad(Op.LABEL, "L1", null, null));
        quads.add(new Quad(Op.SUB, "n", "1", "t1"));
        quads.add(new Quad(Op.PARAM, "t1", null, null));
        quads.add(new Quad(Op.CALL, "fact", "1", "t2"));
        quads.add(new Quad(Op.MUL, "n", "t2", "t3"));
        quads.add(new Quad(Op.RET, "t3", null, null));



        for (var q : quads) System.out.println(q);

    }
}

enum Op {
    // binary operators
    ADD, SUB, MUL, DIV, MOD,
    EQ, NE, LT, LE, GT, GE, // these also produce value, not jump!

    // control flow
    JMP, LABEL, RET, CMP,

    // === branching ===
    BR,
    // conditional:     (BR, value, label, label)
    // unconditional:   (BR, label)


    PARAM, // to pass parameter to function
    CALL,  // to call the function


    // assignment operators
    ASSIGN,

    LOAD,   // copy value in reg
    STORE,  // store value from reg
}

// x = x + 3 * (3 - 4) % 3;

record Quad(Op op, String a1, String a2, String dst) {
    @Override
    public String toString() {
        return String.format("| %-8s | %-8s | %-8s | %-8s |", op, a1, a2, dst);
    }
}