%{
  import java.io.*;
  import java.util.ArrayList;
  import java.util.Stack;
%}
/* ---------- TOKENS ---------- */
%token ID INT FLOAT BOOL NUM LIT VOID MAIN READ WRITE IF ELSE DO
%token WHILE TRUE FALSE
%token EQ LEQ GEQ NEQ
%token AND OR
%token INC DEC
%token PLUSEQ MINUSEQ MULEQ DIVEQ MODEQ
/* ---------- PRECEDÊNCIAS ---------- */
%right '=' PLUSEQ MINUSEQ MULEQ DIVEQ MODEQ
%right '?' ':' /* operador condicional */
%left OR
%left AND
%left '>' '<' EQ LEQ GEQ NEQ
%left '+' '-'
%left '*' '/' '%'
%left '!' INC DEC
/* ---------- TIPOS SEMÂNTICOS ---------- */
%type <sval> ID LIT NUM
%type <ival> type
%%
/* --- PROGRAMA --- */
prog : { geraInicio(); } dList mainF { geraAreaDados(); geraAreaLiterais(); } ;
/* --- FUNÇÃO MAIN --- */
mainF : VOID MAIN '(' ')' { System.out.println("_start:"); }
        '{' lcmd { geraFinal(); } '}' ;
/* --- DECLARAÇÕES --- */
dList : decl dList | ;
decl : type ID ';' {
         TS_entry nodo = ts.pesquisa($2);
         if (nodo != null)
             yyerror("(sem) variavel >" + $2 + "< jah declarada");
         else ts.insert(new TS_entry($2, $1));
       } ;
type : INT { $$ = INT; }
     | FLOAT { $$ = FLOAT; }
     | BOOL { $$ = BOOL; } ;
/* --- COMANDOS --- */
lcmd : lcmd cmd | ;
cmd : exp ';'
    | WRITE '(' LIT ',' exp ')' ';' {
          strTab.add($3);
          System.out.println("\tMOVL $_str_"+strCount+"Len, %EDX");
          System.out.println("\tMOVL $_str_"+strCount+", %ECX");
          System.out.println("\tCALL _writeLit");
          strCount++;
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCALL _write");
          System.out.println("\tCALL _writeln");
      }
    | WRITE '(' LIT ')' ';' {
          strTab.add($3);
          System.out.println("\tMOVL $_str_"+strCount+"Len, %EDX");
          System.out.println("\tMOVL $_str_"+strCount+", %ECX");
          System.out.println("\tCALL _writeLit");
          System.out.println("\tCALL _writeln");
          strCount++;
      }
    | READ '(' ID ')' ';' {
          System.out.println("\tPUSHL $_"+$3);
          System.out.println("\tCALL _read");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL %EAX, (%EDX)");
      }
    | WHILE {
          pRot.push(proxRot); proxRot += 2;
          System.out.printf("rot_%02d:\n", pRot.peek());
      } '(' exp ')' {
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCMPL $0, %EAX");
          System.out.printf("\tJE rot_%02d\n", pRot.peek() + 1);
      } cmd {
          System.out.printf("\tJMP rot_%02d\n", pRot.peek());
          System.out.printf("rot_%02d:\n", pRot.peek() + 1);
          pRot.pop();
      }
    | DO {
          pRot.push(proxRot); proxRot += 1;
          System.out.printf("rot_%02d:\n", pRot.peek());
      } cmd WHILE '(' exp ')' ';' {
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCMPL $0, %EAX");
          System.out.printf("\tJNE rot_%02d\n", pRot.peek());
          pRot.pop();
      }
    | IF '(' exp {
          pRot.push(proxRot); proxRot += 2;
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCMPL $0, %EAX");
          System.out.printf("\tJE rot_%02d\n", pRot.peek());
      } ')' cmd restoIf {
          System.out.printf("rot_%02d:\n", pRot.peek() + 1);
          pRot.pop();
      }
    | '{' lcmd '}'
    ;
restoIf : ELSE {
          System.out.printf("\tJMP rot_%02d\n", pRot.peek() + 1);
          System.out.printf("rot_%02d:\n", pRot.peek());
      } cmd
        | {
          System.out.printf("\tJMP rot_%02d\n", pRot.peek() + 1);
          System.out.printf("rot_%02d:\n", pRot.peek());
      }
      ;
/* --- LVALUE (apenas endereço) --- */
lvalue : ID { System.out.println("\tPUSHL $_"+$1); } ;
/* --- EXPRESSÕES --- */
exp : NUM { System.out.println("\tPUSHL $"+$1); }
    | TRUE { System.out.println("\tPUSHL $1"); }
    | FALSE { System.out.println("\tPUSHL $0"); }
    | ID { System.out.println("\tPUSHL _"+$1); }
    | '(' exp ')'
    | '!' exp { gcExpNot(); }
    /* PRÉ-INCREMENTO */
    | INC ID {
          System.out.println("\tPUSHL $_"+$2);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tADDL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
    /* PRÉ-DECREMENTO */
    | DEC ID {
          System.out.println("\tPUSHL $_"+$2);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tSUBL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
    /* PÓS-INCREMENTO */
    | ID INC {
          System.out.println("\tPUSHL _"+$1);
          System.out.println("\tPUSHL $_"+$1);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tPOPL %EAX");
          System.out.println("\tADDL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tSUBL $1, %EAX");
          System.out.println("\tPUSHL %EAX");
      }
    /* PÓS-DECREMENTO */
    | ID DEC {
          System.out.println("\tPUSHL _"+$1);
          System.out.println("\tPUSHL $_"+$1);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tPOPL %EAX");
          System.out.println("\tSUBL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tADDL $1, %EAX");
          System.out.println("\tPUSHL %EAX");
      }
    /* ATRIBUIÇÃO SIMPLES */
    | lvalue '=' exp {
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
    /* ATRIBUIÇÕES COMPOSTAS */
    | lvalue PLUSEQ exp {
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tADDL (%EDX), %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
    | lvalue MINUSEQ exp {
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EBX");
          System.out.println("\tSUBL %EAX, %EBX");
          System.out.println("\tMOVL %EBX, (%EDX)");
          System.out.println("\tPUSHL %EBX");
      }
    | lvalue MULEQ exp {
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EBX");
          System.out.println("\tIMULL %EAX, %EBX");
          System.out.println("\tMOVL %EBX, (%EDX)");
          System.out.println("\tPUSHL %EBX");
      }
    | lvalue DIVEQ exp {
          System.out.println("\tPOPL %ECX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tMOVL $0, %EDX");
          System.out.println("\tIDIVL %ECX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
    | lvalue MODEQ exp {
          System.out.println("\tPOPL %ECX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tMOVL $0, %EDX");
          System.out.println("\tIDIVL %ECX");
          System.out.println("\tMOVL %EDX, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
    /* OPERADOR CONDICIONAL */
    | exp '?' exp ':' exp {
          System.out.println("\tPOPL %EAX"); // exp_false
          System.out.println("\tPOPL %EBX"); // exp_true
          System.out.println("\tPOPL %ECX"); // cond
          System.out.println("\tCMPL $0, %ECX");
          pRot.push(proxRot); proxRot += 2;
          System.out.printf("\tJE rot_%02d\n", pRot.peek());
          System.out.println("\tMOVL %EBX, %EAX");
          System.out.printf("\tJMP rot_%02d\n", pRot.peek() + 1);
          System.out.printf("rot_%02d:\n", pRot.peek());
          System.out.println("\tMOVL %EAX, %EAX");
          System.out.printf("rot_%02d:\n", pRot.peek() + 1);
          pRot.pop();
          System.out.println("\tPUSHL %EAX");
      }
    /* OPERAÇÕES ARITMÉTICAS */
    | exp '+' exp { gcExpArit('+'); }
    | exp '-' exp { gcExpArit('-'); }
    | exp '*' exp { gcExpArit('*'); }
    | exp '/' exp { gcExpArit('/'); }
    | exp '%' exp { gcExpArit('%'); }
    /* RELACIONAIS */
    | exp '>' exp { gcExpRel('>'); }
    | exp '<' exp { gcExpRel('<'); }
    | exp EQ exp { gcExpRel(EQ); }
    | exp LEQ exp { gcExpRel(LEQ); }
    | exp GEQ exp { gcExpRel(GEQ); }
    | exp NEQ exp { gcExpRel(NEQ); }
    /* LÓGICAS */
    | exp OR exp { gcExpLog(OR); }
    | exp AND exp { gcExpLog(AND); }
    ;
%%
/* ---------- ATRIBUTOS E MÉTODOS JAVA ---------- */
private Yylex lexer;
private TabSimb ts = new TabSimb();
private int strCount = 0;
private ArrayList<String> strTab = new ArrayList<>();
private Stack<Integer> pRot = new Stack<>();
private int proxRot = 1;
private int yylex() {
    int ret;
    try {
        yylval = new ParserVal(0);
        ret = lexer.yylex();
    } catch (IOException e) {
        System.err.println("IO error: " + e);
        ret = -1;
    }
    return ret;
}
public void yyerror(String s) {
    System.err.println("Error: " + s + " linha: " + lexer.getLine());
}
public Parser(Reader r) {
    lexer = new Yylex(r, this);
}
public void setDebug(boolean d) { yydebug = d; }
public void listarTS() { ts.listar(); }
public static void main(String[] args) throws IOException {
    if (args.length > 0) {
        new Parser(new FileReader(args[0])).yyparse();
    } else {
        System.out.println("Uso: java Parser arquivo.cmm > arquivo.s");
    }
}
/* ---------- GERAÇÃO DE CÓDIGO ---------- */
void gcExpArit(int op) {
    System.out.println("\tPOPL %EBX");
    System.out.println("\tPOPL %EAX");
    switch (op) {
        case '+': System.out.println("\tADDL %EBX, %EAX"); break;
        case '-': System.out.println("\tSUBL %EBX, %EAX"); break;
        case '*': System.out.println("\tIMULL %EBX, %EAX"); break;
        case '/': case '%':
            System.out.println("\tMOVL $0, %EDX");
            System.out.println("\tIDIVL %EBX");
            if (op == '%') System.out.println("\tMOVL %EDX, %EAX");
            break;
    }
    System.out.println("\tPUSHL %EAX");
}
public void gcExpRel(int op) {
    System.out.println("\tPOPL %EAX");
    System.out.println("\tPOPL %EDX");
    System.out.println("\tCMPL %EAX, %EDX");
    System.out.println("\tMOVL $0, %EAX");
    switch (op) {
        case '<': System.out.println("\tSETL %AL"); break;
        case '>': System.out.println("\tSETG %AL"); break;
        case Parser.EQ: System.out.println("\tSETE %AL"); break;
        case Parser.GEQ: System.out.println("\tSETGE %AL"); break;
        case Parser.LEQ: System.out.println("\tSETLE %AL"); break;
        case Parser.NEQ: System.out.println("\tSETNE %AL"); break;
    }
    System.out.println("\tPUSHL %EAX");
}
public void gcExpLog(int op) {
    System.out.println("\tPOPL %EDX");
    System.out.println("\tPOPL %EAX");
    System.out.println("\tCMPL $0, %EAX");
    System.out.println("\tMOVL $0, %EAX");
    System.out.println("\tSETNE %AL");
    System.out.println("\tCMPL $0, %EDX");
    System.out.println("\tMOVL $0, %EDX");
    System.out.println("\tSETNE %DL");
    if (op == Parser.OR) System.out.println("\tORL %EDX, %EAX");
    else System.out.println("\tANDL %EDX, %EAX");
    System.out.println("\tPUSHL %EAX");
}
public void gcExpNot() {
    System.out.println("\tPOPL %EAX");
    System.out.println("\tNEGL %EAX");
    System.out.println("\tPUSHL %EAX");
}
private void geraInicio() {
    System.out.println(".text\n.GLOBL _start\n");
}
private void geraFinal() {
    System.out.println("\n\tmov $0, %ebx");
    System.out.println("\tmov $1, %eax");
    System.out.println("\tint $0x80\n");
    // Biblioteca IO
    System.out.println("_writeln:");
    System.out.println("\tMOVL $__fim_msg, %ECX");
    System.out.println("\tDECL %ECX");
    System.out.println("\tMOVB $10, (%ECX)");
    System.out.println("\tMOVL $1, %EDX");
    System.out.println("\tJMP _writeLit");
    System.out.println("_write:");
    System.out.println("\tMOVL $__fim_msg, %ECX");
    System.out.println("\tMOVL $0, %EBX");
    System.out.println("\tCMPL $0, %EAX");
    System.out.println("\tJGE _write3");
    System.out.println("\tNEGL %EAX");
    System.out.println("\tMOVL $1, %EBX");
    System.out.println("_write3:");
    System.out.println("\tPUSHL %EBX");
    System.out.println("\tMOVL $10, %EBX");
    System.out.println("_divide:");
    System.out.println("\tMOVL $0, %EDX");
    System.out.println("\tIDIVL %EBX");
    System.out.println("\tDECL %ECX");
    System.out.println("\tADD $48, %DL");
    System.out.println("\tMOVB %DL, (%ECX)");
    System.out.println("\tCMPL $0, %EAX");
    System.out.println("\tJNE _divide");
    System.out.println("\tPOPL %EBX");
    System.out.println("\tCMPL $0, %EBX");
    System.out.println("\tJE _print");
    System.out.println("\tDECL %ECX");
    System.out.println("\tMOVB $'-', (%ECX)");
    System.out.println("_print:");
    System.out.println("\tMOVL $__fim_msg, %EDX");
    System.out.println("\tSUBL %ECX, %EDX");
    System.out.println("_writeLit:");
    System.out.println("\tMOVL $1, %EBX");
    System.out.println("\tMOVL $4, %EAX");
    System.out.println("\tint $0x80");
    System.out.println("\tRET");
    System.out.println("_read:");
    System.out.println("\tMOVL $15, %EDX");
    System.out.println("\tMOVL $__msg, %ECX");
    System.out.println("\tMOVL $0, %EBX");
    System.out.println("\tMOVL $3, %EAX");
    System.out.println("\tint $0x80");
    System.out.println("\tMOVL $0, %EAX");
    System.out.println("\tMOVL $0, %EBX");
    System.out.println("\tMOVL $0, %EDX");
    System.out.println("\tMOVL $__msg, %ECX");
    System.out.println("\tCMPB $'-', (%ECX)");
    System.out.println("\tJNE _reading");
    System.out.println("\tINCL %ECX");
    System.out.println("\tINC %BL");
    System.out.println("_reading:");
    System.out.println("\tMOVB (%ECX), %DL");
    System.out.println("\tCMP $10, %DL");
    System.out.println("\tJE _fimread");
    System.out.println("\tSUB $48, %DL");
    System.out.println("\tIMULL $10, %EAX");
    System.out.println("\tADDL %EDX, %EAX");
    System.out.println("\tINCL %ECX");
    System.out.println("\tJMP _reading");
    System.out.println("_fimread:");
    System.out.println("\tCMPB $1, %BL");
    System.out.println("\tJNE _fimread2");
    System.out.println("\tNEGL %EAX");
    System.out.println("_fimread2:");
    System.out.println("\tRET");
}
private void geraAreaDados() {
    System.out.println(".data");
    ts.geraGlobais();
}
private void geraAreaLiterais() {
    System.out.println("__msg:\t.zero 30");
    System.out.println("__fim_msg:\t.byte 0\n");
    for (int i = 0; i < strTab.size(); i++) {
        System.out.printf("_str_%d:\t.ascii \"%s\"\n", i, strTab.get(i));
        System.out.printf("_str_%dLen = . - _str_%d\n", i, i);
    }
}