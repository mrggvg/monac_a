# About Target Machine

Example how one might handle stack frame, and reserving space for locals.

```
func:
	POP C 	    ; retaddr
  	
  	; prologue
    PUSH D		; save old BP using D for it
    MOV D, SP	; D = BP (base of this frame)
    SUB SP, 4	; locals, 4 bytes reserved
    
    PUSH C		; retaddr back on top
    
   	; body
    
    POP C		; retaddr

    ; epilogue    
    MOV SP, D	; dealocate locals (SP back to frame base)
    POP D		; restore old BP
    
    PUSH C		; retaddr back on top
	RET
```