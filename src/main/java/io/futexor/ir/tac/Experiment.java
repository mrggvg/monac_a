package io.futexor.ir.tac;

import java.util.ArrayList;

public class Experiment {
    public static void main(String[] args) {


        ArrayList<Quad> quads = new ArrayList<>();
        quads.add(new Quad(Op.LE, "n", "1", "t0"));
        quads.add(new Quad(Op.BR, "t0", "L0", "L1"));
        quads.add(new Quad(Op.LABEL, "L0", null, null));
        quads.add(new Quad(Op.RET, "1", null, null));
        quads.add(new Quad(Op.LABEL, "L1", null, null));
        quads.add(new Quad(Op.SUB, "n", "1", "t1"));
        quads.add(new Quad(Op.PARAM, "t1", null, null));
        quads.add(new Quad(Op.CALL, "fact", "1", "t2"));
        quads.add(new Quad(Op.MUL, "n", "t2", "t3"));
        quads.add(new Quad(Op.RET, "t3", null, null));


        StringBuilder out = new StringBuilder();

        quads.forEach(q -> {

            // D for ret address
            // A for n

            switch (q.op()) {
                case LE -> {
                    out
                            .append("POP A").append("\n")
                            .append("PUSH A").append("\n")
                            .append("CMP A, 1").append("\n");
                }
                case BR -> {
                    out
                            .append("JNA ").append(q.a2()).append("\n")
                            .append("JMP ").append(q.a3()).append("\n");
                }
                case LABEL -> {
                    out.append(q.a1()).append(":\n");
                }
                case RET -> {
                    out
                            .append("PUSH A").append("\n")
                            .append("PUSH D").append("\n")
                            .append("RET").append("\n");
                }
                case SUB -> {
                    out
                            .append("POP A").append("\n")
                            .append("SUB A,").append(q.a2()).append("\n")
                            .append("PUSH A").append("\n");
                }
            }

        });

        System.out.println(out);

    }
}

enum Op {
    ADD, SUB, MUL, DIV, MOD,
    EQ, NE, LT, LE, GT, GE,
    BR, PARAM, CALL, RET, LABEL,
}

record Quad(Op op, String a1, String a2, String a3) {

    @Override
    public String toString() {
        String adr1 = a1 == null ? "" : a1;
        String adr2 = a2 == null ? "" : a2;
        String adr3 = a3 == null ? "" : a3;
        return String.format("| %-8s | %-8s | %-8s | %-8s |", op, adr1, adr2, adr3);
    }
}