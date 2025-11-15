.text

#	 nome COMPLETO e matricula dos componentes do grupo...
#

.GLOBL _start


_start:
	PUSHL $_i
	PUSHL $0
	POPL %EAX
	POPL %EDX
	MOVL %EAX, (%EDX)
	PUSHL %EAX
rot_01:
	PUSHL $1
	POPL %EAX
	CMPL $0, %EAX
	JE rot_02
