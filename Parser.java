//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "exemploGC.y"
  import java.io.*;
  import java.util.ArrayList;
  import java.util.Stack;
//#line 21 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short INT=258;
public final static short FLOAT=259;
public final static short BOOL=260;
public final static short NUM=261;
public final static short LIT=262;
public final static short VOID=263;
public final static short MAIN=264;
public final static short READ=265;
public final static short WRITE=266;
public final static short IF=267;
public final static short ELSE=268;
public final static short DO=269;
public final static short WHILE=270;
public final static short TRUE=271;
public final static short FALSE=272;
public final static short EQ=273;
public final static short LEQ=274;
public final static short GEQ=275;
public final static short NEQ=276;
public final static short AND=277;
public final static short OR=278;
public final static short INC=279;
public final static short DEC=280;
public final static short PLUSEQ=281;
public final static short MINUSEQ=282;
public final static short MULEQ=283;
public final static short DIVEQ=284;
public final static short MODEQ=285;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    3,    0,    5,    7,    4,    2,    2,    8,    1,    1,
    1,    6,    6,    9,    9,    9,    9,   11,   12,    9,
   13,    9,   14,    9,    9,   16,   15,   15,   17,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   10,   10,
   10,   10,   10,   10,   10,   10,   10,   10,   10,
};
final static short yylen[] = {                            2,
    0,    3,    0,    0,    9,    2,    0,    3,    1,    1,
    1,    2,    0,    2,    7,    5,    5,    0,    0,    7,
    0,    8,    0,    7,    3,    0,    3,    0,    1,    1,
    1,    1,    1,    3,    2,    2,    2,    2,    2,    3,
    3,    3,    3,    3,    3,    5,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,
};
final static short yydefred[] = {                         1,
    0,    0,    9,   10,   11,    0,    0,    0,    0,    0,
    2,    6,    8,    0,    0,    3,    0,   13,    0,    0,
   30,    0,    0,    0,   21,   18,   31,   32,    0,    0,
    0,    0,   13,    0,   12,    0,    0,   38,   39,    0,
    0,    0,    0,    0,   36,   37,   35,    0,    0,    5,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   34,   25,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   49,
   50,   51,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   17,   16,    0,    0,    0,
   19,    0,    0,    0,    0,    0,   15,   26,   24,    0,
   20,    0,   22,   27,
};
final static short yydgoto[] = {                          1,
    6,    7,    2,   11,   17,   19,   34,    8,   35,   36,
   44,  116,   43,  102,  119,  122,   37,
};
final static short yysindex[] = {                         0,
    0, -169,    0,    0,    0, -254, -256, -169,  -47, -248,
    0,    0,    0,  -22,   -4,    0,  -84,    0,   26, -231,
    0,    2,    5,   12,    0,    0,    0,    0, -197, -194,
   42,   42,    0,  -57,    0,   34,  -52,    0,    0, -187,
 -190,   42,   26,   33,    0,    0,    0,   58,   -6,    0,
   42,   42,   42,   42,   42,   42,   42,   42,   42,   42,
   42,   42,   42,   42,    0,   42,   42,   42,   42,   42,
   42,   44,  -27,  137, -183,   42,    0,    0,   41,   41,
   41,   41,  165,  151,   65,   41,   41,    4,    4,    0,
    0,    0,  137,  137,  137,  137,  137,  137,   39,   45,
   42,   51,   66,   95,   42,    0,    0,  109,   26,   42,
    0,  137,   50, -157,  123,   26,    0,    0,    0,   54,
    0,   26,    0,    0,
};
final static short yyrindex[] = {                         0,
    0, -149,    0,    0,    0,    0,    0, -149,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -10,  -37,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   75,    0,    0,    0,    0,  194,  296,
  316,  394,  -28,    6,    0,  401,  417,  303,  388,    0,
    0,    0,  -39,  -30,  -26,   -5,   -3,   -1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    3,    0,   10,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  114,    0,    0,    0,   91,    0,    0,  -42,  442,
    0,    0,    0,    0,    0,    0,    0,
};
final static int YYTABLESIZE=695;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         33,
   75,   41,    9,   33,   33,   33,   10,   33,   71,   33,
   42,   13,   59,  100,   43,   14,  101,   15,   41,   41,
   33,   33,   33,   29,   33,   33,   31,   42,   42,   59,
   59,   43,   43,   32,   59,   44,   16,   45,   18,   40,
   64,   40,   28,   46,   41,   62,   58,   38,   39,   28,
   63,   42,   44,   44,   45,   45,   40,   40,   31,   45,
   46,   46,   46,   58,   58,   32,  114,   50,   58,   72,
   64,   73,   76,  121,   31,   62,   60,   64,   61,  124,
   63,   32,   62,   60,   99,   61,  103,   63,    3,    4,
    5,  109,   65,   59,   64,   58,   57,  106,   77,   62,
   60,   64,   61,  107,   63,  110,   62,   60,  117,   61,
  118,   63,  123,    7,    4,   23,   33,   59,   78,   58,
   57,   12,  105,   49,   59,    0,   58,   57,    0,    0,
    0,   64,   28,    0,   28,  111,   62,   60,    0,   61,
    0,   63,    0,    0,    0,   64,    0,    0,   33,  113,
   62,   60,    0,   61,   59,   63,   58,   57,    0,   64,
    0,    0,    0,  120,   62,   60,    0,   61,   59,   63,
   58,   57,    0,   64,    0,    0,    0,    0,   62,   60,
    0,   61,   59,   63,   58,   57,    0,   64,    0,    0,
    0,    0,   62,   60,    0,   61,   59,   63,   58,   57,
    0,   64,    0,    0,    0,    0,   62,   60,    0,   61,
   59,   63,   58,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   59,    0,   58,    0,   66,   67,
   68,   69,   70,    0,   54,   33,   33,   33,   33,   33,
   33,    0,    0,   29,   29,   29,   29,   29,   59,   59,
   20,   54,   54,   54,   21,   54,   54,    0,   22,   23,
   24,    0,   25,   26,   27,   28,   28,    0,    0,    0,
   28,    0,   29,   30,   28,   28,   28,    0,   28,   28,
   28,   28,   20,   58,    0,    0,   21,    0,   28,   28,
   22,   23,   24,    0,   25,   26,   27,   28,   20,    0,
    0,    0,   21,    0,   29,   30,   51,   52,   53,   54,
   55,   56,   27,   28,    0,    0,    0,    0,    0,    0,
   29,   30,    0,    0,    0,    0,    0,    0,    0,    0,
   51,   52,   53,   54,   55,   56,   55,   51,   52,   53,
   54,   55,   56,   47,    0,   47,    0,   47,    0,    0,
    0,    0,    0,   55,   55,   55,   56,   55,   55,    0,
   47,   47,   47,    0,   47,   47,    0,   51,   52,   53,
   54,   55,   56,   56,   56,   56,    0,   56,   56,    0,
    0,   51,   52,   53,   54,   55,   56,    0,    0,    0,
    0,    0,    0,    0,    0,   51,   52,   53,   54,   55,
   56,    0,    0,    0,    0,    0,    0,    0,    0,   51,
   52,   53,   54,   55,   56,    0,    0,    0,    0,    0,
    0,    0,    0,   51,   52,   53,   54,   55,   48,    0,
   48,    0,   48,    0,   57,    0,    0,   51,   52,   53,
   54,   52,    0,    0,    0,   48,   48,   48,    0,   48,
   48,   57,   57,   57,    0,   57,   57,   53,   52,   52,
   52,    0,   52,   52,    0,    0,   54,   54,   54,   54,
   54,   54,   47,   48,   53,   53,   53,    0,   53,   53,
    0,    0,    0,   74,    0,    0,    0,    0,    0,    0,
    0,    0,   79,   80,   81,   82,   83,   84,   85,   86,
   87,   88,   89,   90,   91,   92,    0,   93,   94,   95,
   96,   97,   98,    0,    0,    0,    0,  104,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  108,    0,    0,    0,  112,    0,    0,    0,
    0,  115,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   55,   55,
   55,   55,   55,   55,    0,   47,   47,   47,   47,   47,
   47,    0,    0,    0,    0,    0,    0,    0,   56,   56,
   56,   56,   56,   56,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   48,   48,   48,   48,   48,   48,   57,   57,   57,   57,
   57,   57,    0,   52,   52,   52,   52,   52,   52,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   53,
   53,   53,   53,   53,   53,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         37,
   43,   41,  257,   41,   42,   43,  263,   45,   61,   47,
   41,   59,   41,   41,   41,  264,   44,   40,   58,   59,
   58,   59,   60,   61,   62,   63,   33,   58,   59,   58,
   59,   58,   59,   40,   63,   41,   41,   41,  123,   41,
   37,   40,   33,   41,   40,   42,   41,  279,  280,   40,
   47,   40,   58,   59,   58,   59,   58,   59,   33,  257,
   58,   59,  257,   58,   59,   40,  109,  125,   63,  257,
   37,  262,   40,  116,   33,   42,   43,   37,   45,  122,
   47,   40,   42,   43,   41,   45,  270,   47,  258,  259,
  260,   41,   59,   60,   37,   62,   63,   59,   41,   42,
   43,   37,   45,   59,   47,   40,   42,   43,   59,   45,
  268,   47,   59,  263,  125,   41,  123,   60,  125,   62,
   63,    8,   58,   33,   60,   -1,   62,   63,   -1,   -1,
   -1,   37,  123,   -1,  125,   41,   42,   43,   -1,   45,
   -1,   47,   -1,   -1,   -1,   37,   -1,   -1,  123,   41,
   42,   43,   -1,   45,   60,   47,   62,   63,   -1,   37,
   -1,   -1,   -1,   41,   42,   43,   -1,   45,   60,   47,
   62,   63,   -1,   37,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   60,   47,   62,   63,   -1,   37,   -1,   -1,
   -1,   -1,   42,   43,   -1,   45,   60,   47,   62,   63,
   -1,   37,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   60,   47,   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   60,   -1,   62,   -1,  281,  282,
  283,  284,  285,   -1,   41,  273,  274,  275,  276,  277,
  278,   -1,   -1,  281,  282,  283,  284,  285,  277,  278,
  257,   58,   59,   60,  261,   62,   63,   -1,  265,  266,
  267,   -1,  269,  270,  271,  272,  257,   -1,   -1,   -1,
  261,   -1,  279,  280,  265,  266,  267,   -1,  269,  270,
  271,  272,  257,  278,   -1,   -1,  261,   -1,  279,  280,
  265,  266,  267,   -1,  269,  270,  271,  272,  257,   -1,
   -1,   -1,  261,   -1,  279,  280,  273,  274,  275,  276,
  277,  278,  271,  272,   -1,   -1,   -1,   -1,   -1,   -1,
  279,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,  276,  277,  278,   41,  273,  274,  275,
  276,  277,  278,   41,   -1,   43,   -1,   45,   -1,   -1,
   -1,   -1,   -1,   58,   59,   60,   41,   62,   63,   -1,
   58,   59,   60,   -1,   62,   63,   -1,  273,  274,  275,
  276,  277,  278,   58,   59,   60,   -1,   62,   63,   -1,
   -1,  273,  274,  275,  276,  277,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  273,  274,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,
  274,  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,  276,  277,   41,   -1,
   43,   -1,   45,   -1,   41,   -1,   -1,  273,  274,  275,
  276,   41,   -1,   -1,   -1,   58,   59,   60,   -1,   62,
   63,   58,   59,   60,   -1,   62,   63,   41,   58,   59,
   60,   -1,   62,   63,   -1,   -1,  273,  274,  275,  276,
  277,  278,   31,   32,   58,   59,   60,   -1,   62,   63,
   -1,   -1,   -1,   42,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   51,   52,   53,   54,   55,   56,   57,   58,
   59,   60,   61,   62,   63,   64,   -1,   66,   67,   68,
   69,   70,   71,   -1,   -1,   -1,   -1,   76,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  101,   -1,   -1,   -1,  105,   -1,   -1,   -1,
   -1,  110,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,  276,  277,  278,   -1,  273,  274,  275,  276,  277,
  278,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,  276,  277,  278,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,  276,  277,  278,  273,  274,  275,  276,
  277,  278,   -1,  273,  274,  275,  276,  277,  278,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,
  274,  275,  276,  277,  278,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=285;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,"'%'",null,null,"'('","')'","'*'","'+'",
"','","'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'","'?'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,"ID","INT","FLOAT","BOOL","NUM","LIT",
"VOID","MAIN","READ","WRITE","IF","ELSE","DO","WHILE","TRUE","FALSE","EQ","LEQ",
"GEQ","NEQ","AND","OR","INC","DEC","PLUSEQ","MINUSEQ","MULEQ","DIVEQ","MODEQ",
};
final static String yyrule[] = {
"$accept : prog",
"$$1 :",
"prog : $$1 dList mainF",
"$$2 :",
"$$3 :",
"mainF : VOID MAIN '(' ')' $$2 '{' lcmd $$3 '}'",
"dList : decl dList",
"dList :",
"decl : type ID ';'",
"type : INT",
"type : FLOAT",
"type : BOOL",
"lcmd : lcmd cmd",
"lcmd :",
"cmd : exp ';'",
"cmd : WRITE '(' LIT ',' exp ')' ';'",
"cmd : WRITE '(' LIT ')' ';'",
"cmd : READ '(' ID ')' ';'",
"$$4 :",
"$$5 :",
"cmd : WHILE $$4 '(' exp ')' $$5 cmd",
"$$6 :",
"cmd : DO $$6 cmd WHILE '(' exp ')' ';'",
"$$7 :",
"cmd : IF '(' exp $$7 ')' cmd restoIf",
"cmd : '{' lcmd '}'",
"$$8 :",
"restoIf : ELSE $$8 cmd",
"restoIf :",
"lvalue : ID",
"exp : NUM",
"exp : TRUE",
"exp : FALSE",
"exp : ID",
"exp : '(' exp ')'",
"exp : '!' exp",
"exp : INC ID",
"exp : DEC ID",
"exp : ID INC",
"exp : ID DEC",
"exp : lvalue '=' exp",
"exp : lvalue PLUSEQ exp",
"exp : lvalue MINUSEQ exp",
"exp : lvalue MULEQ exp",
"exp : lvalue DIVEQ exp",
"exp : lvalue MODEQ exp",
"exp : exp '?' exp ':' exp",
"exp : exp '+' exp",
"exp : exp '-' exp",
"exp : exp '*' exp",
"exp : exp '/' exp",
"exp : exp '%' exp",
"exp : exp '>' exp",
"exp : exp '<' exp",
"exp : exp EQ exp",
"exp : exp LEQ exp",
"exp : exp GEQ exp",
"exp : exp NEQ exp",
"exp : exp OR exp",
"exp : exp AND exp",
};

//#line 243 "exemploGC.y"
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
//#line 598 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 27 "exemploGC.y"
{ geraInicio(); }
break;
case 2:
//#line 27 "exemploGC.y"
{ geraAreaDados(); geraAreaLiterais(); }
break;
case 3:
//#line 29 "exemploGC.y"
{ System.out.println("_start:"); }
break;
case 4:
//#line 30 "exemploGC.y"
{ geraFinal(); }
break;
case 8:
//#line 33 "exemploGC.y"
{
         TS_entry nodo = ts.pesquisa(val_peek(1).sval);
         if (nodo != null)
             yyerror("(sem) variavel >" + val_peek(1).sval + "< jah declarada");
         else ts.insert(new TS_entry(val_peek(1).sval, val_peek(2).ival));
       }
break;
case 9:
//#line 39 "exemploGC.y"
{ yyval.ival = INT; }
break;
case 10:
//#line 40 "exemploGC.y"
{ yyval.ival = FLOAT; }
break;
case 11:
//#line 41 "exemploGC.y"
{ yyval.ival = BOOL; }
break;
case 15:
//#line 45 "exemploGC.y"
{
          strTab.add(val_peek(4).sval);
          System.out.println("\tMOVL $_str_"+strCount+"Len, %EDX");
          System.out.println("\tMOVL $_str_"+strCount+", %ECX");
          System.out.println("\tCALL _writeLit");
          strCount++;
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCALL _write");
          System.out.println("\tCALL _writeln");
      }
break;
case 16:
//#line 55 "exemploGC.y"
{
          strTab.add(val_peek(2).sval);
          System.out.println("\tMOVL $_str_"+strCount+"Len, %EDX");
          System.out.println("\tMOVL $_str_"+strCount+", %ECX");
          System.out.println("\tCALL _writeLit");
          System.out.println("\tCALL _writeln");
          strCount++;
      }
break;
case 17:
//#line 63 "exemploGC.y"
{
          System.out.println("\tPUSHL $_"+val_peek(2).sval);
          System.out.println("\tCALL _read");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL %EAX, (%EDX)");
      }
break;
case 18:
//#line 69 "exemploGC.y"
{
          pRot.push(proxRot); proxRot += 2;
          System.out.printf("rot_%02d:\n", pRot.peek());
      }
break;
case 19:
//#line 72 "exemploGC.y"
{
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCMPL $0, %EAX");
          System.out.printf("\tJE rot_%02d\n", pRot.peek() + 1);
      }
break;
case 20:
//#line 76 "exemploGC.y"
{
          System.out.printf("\tJMP rot_%02d\n", pRot.peek());
          System.out.printf("rot_%02d:\n", pRot.peek() + 1);
          pRot.pop();
      }
break;
case 21:
//#line 81 "exemploGC.y"
{
          pRot.push(proxRot); proxRot += 1;
          System.out.printf("rot_%02d:\n", pRot.peek());
      }
break;
case 22:
//#line 84 "exemploGC.y"
{
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCMPL $0, %EAX");
          System.out.printf("\tJNE rot_%02d\n", pRot.peek());
          pRot.pop();
      }
break;
case 23:
//#line 90 "exemploGC.y"
{
          pRot.push(proxRot); proxRot += 2;
          System.out.println("\tPOPL %EAX");
          System.out.println("\tCMPL $0, %EAX");
          System.out.printf("\tJE rot_%02d\n", pRot.peek());
      }
break;
case 24:
//#line 95 "exemploGC.y"
{
          System.out.printf("rot_%02d:\n", pRot.peek() + 1);
          pRot.pop();
      }
break;
case 26:
//#line 101 "exemploGC.y"
{
          System.out.printf("\tJMP rot_%02d\n", pRot.peek() + 1);
          System.out.printf("rot_%02d:\n", pRot.peek());
      }
break;
case 28:
//#line 105 "exemploGC.y"
{
          System.out.printf("\tJMP rot_%02d\n", pRot.peek() + 1);
          System.out.printf("rot_%02d:\n", pRot.peek());
      }
break;
case 29:
//#line 111 "exemploGC.y"
{ System.out.println("\tPUSHL $_"+val_peek(0).sval); }
break;
case 30:
//#line 113 "exemploGC.y"
{ System.out.println("\tPUSHL $"+val_peek(0).sval); }
break;
case 31:
//#line 114 "exemploGC.y"
{ System.out.println("\tPUSHL $1"); }
break;
case 32:
//#line 115 "exemploGC.y"
{ System.out.println("\tPUSHL $0"); }
break;
case 33:
//#line 116 "exemploGC.y"
{ System.out.println("\tPUSHL _"+val_peek(0).sval); }
break;
case 35:
//#line 118 "exemploGC.y"
{ gcExpNot(); }
break;
case 36:
//#line 120 "exemploGC.y"
{
          System.out.println("\tPUSHL $_"+val_peek(0).sval);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tADDL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 37:
//#line 129 "exemploGC.y"
{
          System.out.println("\tPUSHL $_"+val_peek(0).sval);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tSUBL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 38:
//#line 138 "exemploGC.y"
{
          System.out.println("\tPUSHL _"+val_peek(1).sval);
          System.out.println("\tPUSHL $_"+val_peek(1).sval);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tPOPL %EAX");
          System.out.println("\tADDL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tSUBL $1, %EAX");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 39:
//#line 149 "exemploGC.y"
{
          System.out.println("\tPUSHL _"+val_peek(1).sval);
          System.out.println("\tPUSHL $_"+val_peek(1).sval);
          System.out.println("\tPOPL %EDX");
          System.out.println("\tPOPL %EAX");
          System.out.println("\tSUBL $1, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tADDL $1, %EAX");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 40:
//#line 160 "exemploGC.y"
{
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 41:
//#line 167 "exemploGC.y"
{
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tADDL (%EDX), %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 42:
//#line 174 "exemploGC.y"
{
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EBX");
          System.out.println("\tSUBL %EAX, %EBX");
          System.out.println("\tMOVL %EBX, (%EDX)");
          System.out.println("\tPUSHL %EBX");
      }
break;
case 43:
//#line 182 "exemploGC.y"
{
          System.out.println("\tPOPL %EAX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EBX");
          System.out.println("\tIMULL %EAX, %EBX");
          System.out.println("\tMOVL %EBX, (%EDX)");
          System.out.println("\tPUSHL %EBX");
      }
break;
case 44:
//#line 190 "exemploGC.y"
{
          System.out.println("\tPOPL %ECX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tMOVL $0, %EDX");
          System.out.println("\tIDIVL %ECX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 45:
//#line 199 "exemploGC.y"
{
          System.out.println("\tPOPL %ECX");
          System.out.println("\tPOPL %EDX");
          System.out.println("\tMOVL (%EDX), %EAX");
          System.out.println("\tMOVL $0, %EDX");
          System.out.println("\tIDIVL %ECX");
          System.out.println("\tMOVL %EDX, %EAX");
          System.out.println("\tMOVL %EAX, (%EDX)");
          System.out.println("\tPUSHL %EAX");
      }
break;
case 46:
//#line 210 "exemploGC.y"
{
          System.out.println("\tPOPL %EAX"); /* exp_false*/
          System.out.println("\tPOPL %EBX"); /* exp_true*/
          System.out.println("\tPOPL %ECX"); /* cond*/
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
break;
case 47:
//#line 226 "exemploGC.y"
{ gcExpArit('+'); }
break;
case 48:
//#line 227 "exemploGC.y"
{ gcExpArit('-'); }
break;
case 49:
//#line 228 "exemploGC.y"
{ gcExpArit('*'); }
break;
case 50:
//#line 229 "exemploGC.y"
{ gcExpArit('/'); }
break;
case 51:
//#line 230 "exemploGC.y"
{ gcExpArit('%'); }
break;
case 52:
//#line 232 "exemploGC.y"
{ gcExpRel('>'); }
break;
case 53:
//#line 233 "exemploGC.y"
{ gcExpRel('<'); }
break;
case 54:
//#line 234 "exemploGC.y"
{ gcExpRel(EQ); }
break;
case 55:
//#line 235 "exemploGC.y"
{ gcExpRel(LEQ); }
break;
case 56:
//#line 236 "exemploGC.y"
{ gcExpRel(GEQ); }
break;
case 57:
//#line 237 "exemploGC.y"
{ gcExpRel(NEQ); }
break;
case 58:
//#line 239 "exemploGC.y"
{ gcExpLog(OR); }
break;
case 59:
//#line 240 "exemploGC.y"
{ gcExpLog(AND); }
break;
//#line 1094 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
