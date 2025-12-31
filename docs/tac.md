# Mona TAC IR Specification (v0.1)

This is so far best one that I created for my purposes.

| Instruction | Address 1 | Address 2 | Address 3 | Description                       |
|-------------|-----------|-----------|-----------|-----------------------------------|
|             |           |           |           |                                   |
| `add`       |           |           |           |                                   |
| `sub`       |           |           |           |                                   |
|             |           |           |           |                                   |
| `mul`       |           |           |           |                                   |
| `div`       |           |           |           |                                   |
| `mod`       |           |           |           |                                   |
|             |           |           |           |                                   |
| `eq`        | `val`     | `val`     | `tmp`     | Equal                             |
| `ne`        | val       | val       | tmp       | Not equal                         |
| `lt`        | val       | val       | tmp       | Less than                         |
| `le`        | val       | val       | tmp       | Less or equal                     |
| `gt`        | val       | val       | tmp       | Greater than                      |
| `ge`        | val       | val       | tmp       | Greater or equal                  |
|             |           |           |           |                                   |
| `br`        | `val`     | `label`   | `label`   | Conditional branch                |
| `br`        | `label`   |           |           | Unconditional branch              |
|             |           |           |           |                                   |
| `param`     | `val`     |           |           | Push call argument                |
| `call`      | `func`    | `argc`    | `tmp`     | Call function, store return value |
| `ret`       | `val`     |           |           | Return value                      |
|             |           |           |           |                                   |
| `label`     | `label`   |           |           | Define label                      |

---

### `br` instruction

The ‘br’ instruction is used to cause control flow to transfer to a different
basic block in the current function. There are two forms of this instruction,
corresponding to a conditional branch and an unconditional branch.

#### Example

| i  | a1    | a2     | a3      |
|----|-------|--------|---------|
| br | value | L_true | L_false |
| br | L     |        |         |

---

Example how would recursive factorial function look like.

| op      | a1          | a2   | a3   |
|---------|-------------|------|------|
| `le`    | `n`         | `1`  | `t0` |
| `br`    | `t0`        | `L0` | `L1` |
| `label` | `L0`        |      |      |
| `ret`   | `1`         |      |      |
| `label` | `L1`        |      |      |
| `sub`   | `n`         | `1`  | `t1` |
| `param` | `t1`        |      |      |
| `call`  | `factorial` | `1`  | `t2` |
| `mul`   | `n`         | `t2` | `t3` |
| `ret`   | `t3`        |      |      |

