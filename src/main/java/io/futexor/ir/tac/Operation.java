package io.futexor.ir.tac;

public enum Operation {

    // (operation, argument1, argument2, target)


    // binary operations
    add, sub, mul, div, mod,
    // tac e.g.: (add, id, 3, t1)  id - shorthand for identifier


    // ------------------- bellow just some ideas for later

    uminus, // (uminus, 3, null, t2)


    // if (x < y) { x = 2; }







    copy,
    label,
    if_else,
    halt


}
