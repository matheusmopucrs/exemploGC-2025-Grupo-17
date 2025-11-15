.text
.GLOBL _start

_start:
	PUSHL $_a
	PUSHL $_b
	PUSHL $_c
	PUSHL $10
	POPL %EAX
	POPL %EDX
	MOVL %EAX, (%EDX)
	PUSHL %EAX
	POPL %EAX
	POPL %EDX
	MOVL %EAX, (%EDX)
	PUSHL %EAX
	POPL %EAX
	POPL %EDX
	MOVL %EAX, (%EDX)
	PUSHL %EAX
	PUSHL $_b
	POPL %EDX
	MOVL (%EDX), %EAX
	ADDL $1, %EAX
	MOVL %EAX, (%EDX)
	PUSHL %EAX
	PUSHL _c
	PUSHL $_c
	POPL %EDX
	POPL %EAX
	SUBL $1, %EAX
	MOVL %EAX, (%EDX)
	ADDL $1, %EAX
	PUSHL %EAX
	PUSHL $_me
	PUSHL _a
	PUSHL _b
	POPL %EAX
	POPL %EDX
	CMPL %EAX, %EDX
	MOVL $0, %EAX
	SETLE %AL
	PUSHL %EAX
	PUSHL _a
	PUSHL _c
	POPL %EAX
	POPL %EDX
	CMPL %EAX, %EDX
	MOVL $0, %EAX
	SETLE %AL
	PUSHL %EAX
	POPL %EDX
	POPL %EAX
	CMPL $0, %EAX
	MOVL $0, %EAX
	SETNE %AL
	CMPL $0, %EDX
	MOVL $0, %EDX
	SETNE %DL
	ANDL %EDX, %EAX
	PUSHL %EAX
	PUSHL _a
	PUSHL _b
	PUSHL _c
	POPL %EAX
	POPL %EDX
	CMPL %EAX, %EDX
	MOVL $0, %EAX
	SETLE %AL
	PUSHL %EAX
	PUSHL _b
	PUSHL _c
	POPL %EAX
	POPL %EBX
	POPL %ECX
	CMPL $0, %ECX
	JE rot_01
	MOVL %EBX, %EAX
	JMP rot_02
rot_01:
	MOVL %EAX, %EAX
rot_02:
	PUSHL %EAX
	POPL %EAX
	POPL %EBX
	POPL %ECX
	CMPL $0, %ECX
	JE rot_03
	MOVL %EBX, %EAX
	JMP rot_04
rot_03:
	MOVL %EAX, %EAX
rot_04:
	PUSHL %EAX
	POPL %EAX
	POPL %EDX
	MOVL %EAX, (%EDX)
	PUSHL %EAX
	PUSHL $_ma
	PUSHL _a
	PUSHL _b
	POPL %EAX
	POPL %EDX
	CMPL %EAX, %EDX
	MOVL $0, %EAX
	SETGE %AL
	PUSHL %EAX
	PUSHL _a
	PUSHL _c
	POPL %EAX
	POPL %EDX
	CMPL %EAX, %EDX
	MOVL $0, %EAX
	SETGE %AL
	PUSHL %EAX
	POPL %EDX
	POPL %EAX
	CMPL $0, %EAX
	MOVL $0, %EAX
	SETNE %AL
	CMPL $0, %EDX
	MOVL $0, %EDX
	SETNE %DL
	ANDL %EDX, %EAX
	PUSHL %EAX
	PUSHL _a
	PUSHL _b
	PUSHL _c
	POPL %EAX
	POPL %EDX
	CMPL %EAX, %EDX
	MOVL $0, %EAX
	SETGE %AL
	PUSHL %EAX
	PUSHL _b
	PUSHL _c
	POPL %EAX
	POPL %EBX
	POPL %ECX
	CMPL $0, %ECX
	JE rot_05
	MOVL %EBX, %EAX
	JMP rot_06
rot_05:
	MOVL %EAX, %EAX
rot_06:
	PUSHL %EAX
	POPL %EAX
	POPL %EBX
	POPL %ECX
	CMPL $0, %ECX
	JE rot_07
	MOVL %EBX, %EAX
	JMP rot_08
rot_07:
	MOVL %EAX, %EAX
rot_08:
	PUSHL %EAX
	POPL %EAX
	POPL %EDX
	MOVL %EAX, (%EDX)
	PUSHL %EAX
	PUSHL _a
	MOVL $_str_0Len, %EDX
	MOVL $_str_0, %ECX
	CALL _writeLit
	POPL %EAX
	CALL _write
	CALL _writeln
	PUSHL _b
	MOVL $_str_1Len, %EDX
	MOVL $_str_1, %ECX
	CALL _writeLit
	POPL %EAX
	CALL _write
	CALL _writeln
	PUSHL _c
	MOVL $_str_2Len, %EDX
	MOVL $_str_2, %ECX
	CALL _writeLit
	POPL %EAX
	CALL _write
	CALL _writeln
	PUSHL _me
	MOVL $_str_3Len, %EDX
	MOVL $_str_3, %ECX
	CALL _writeLit
	POPL %EAX
	CALL _write
	CALL _writeln
	PUSHL _ma
	MOVL $_str_4Len, %EDX
	MOVL $_str_4, %ECX
	CALL _writeLit
	POPL %EAX
	CALL _write
	CALL _writeln

	mov $0, %ebx
	mov $1, %eax
	int $0x80

_writeln:
	MOVL $__fim_msg, %ECX
	DECL %ECX
	MOVB $10, (%ECX)
	MOVL $1, %EDX
	JMP _writeLit
_write:
	MOVL $__fim_msg, %ECX
	MOVL $0, %EBX
	CMPL $0, %EAX
	JGE _write3
	NEGL %EAX
	MOVL $1, %EBX
_write3:
	PUSHL %EBX
	MOVL $10, %EBX
_divide:
	MOVL $0, %EDX
	IDIVL %EBX
	DECL %ECX
	ADD $48, %DL
	MOVB %DL, (%ECX)
	CMPL $0, %EAX
	JNE _divide
	POPL %EBX
	CMPL $0, %EBX
	JE _print
	DECL %ECX
	MOVB $'-', (%ECX)
_print:
	MOVL $__fim_msg, %EDX
	SUBL %ECX, %EDX
_writeLit:
	MOVL $1, %EBX
	MOVL $4, %EAX
	int $0x80
	RET
_read:
	MOVL $15, %EDX
	MOVL $__msg, %ECX
	MOVL $0, %EBX
	MOVL $3, %EAX
	int $0x80
	MOVL $0, %EAX
	MOVL $0, %EBX
	MOVL $0, %EDX
	MOVL $__msg, %ECX
	CMPB $'-', (%ECX)
	JNE _reading
	INCL %ECX
	INC %BL
_reading:
	MOVB (%ECX), %DL
	CMP $10, %DL
	JE _fimread
	SUB $48, %DL
	IMULL $10, %EAX
	ADDL %EDX, %EAX
	INCL %ECX
	JMP _reading
_fimread:
	CMPB $1, %BL
	JNE _fimread2
	NEGL %EAX
_fimread2:
	RET
.data
_a:	.zero 4
_b:	.zero 4
_c:	.zero 4
_ma:	.zero 4
_me:	.zero 4
__msg:	.zero 30
__fim_msg:	.byte 0

_str_0:	.ascii " a =  "
_str_0Len = . - _str_0
_str_1:	.ascii " b =  "
_str_1Len = . - _str_1
_str_2:	.ascii " c =  "
_str_2Len = . - _str_2
_str_3:	.ascii " me =  "
_str_3Len = . - _str_3
_str_4:	.ascii " ma =  "
_str_4Len = . - _str_4
