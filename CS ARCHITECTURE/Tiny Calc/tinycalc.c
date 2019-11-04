#include "tinycalc.h"
#include <stdio.h>
/* put your function implementations in here. */
double pow(double n,double i){
  if(i==0){//base case
    return 1;
  }else{
    return n*pow(n,(i-1));//return old times new-1
  }
}

int check_command(char op) {
  if((op=='+')||(op=='-')||(op=='/')||(op=='*')||(op=='^')||(op=='m')||(op=='M')||(op=='q')||(op=='Q')){//if the command is one of the ones I am looking for
    return TC_COMMAND_OK;//then it is okay
  }
  return TC_COMMAND_INVALID;//otherwise I don't want it
}

int read_command(char *op, double *num) {
  char c;
  while(scanf("%c",&c)==0){
  }
  if(check_command(c)){
    //check for q first before doing anything else
    if(c=='Q' || c=='q'){//check to see if they want you to quit before doing any operations
      return TC_COMMAND_QUIT;
    }else{
      *op = c;//takes operator arguement pointer and sets the first input equal to it
    }
  }else{//if it is not a valid command start, over!
    return read_command(&*op,&*num);
  }
  //scan for the value
  double d;
  scanf("%lf",&d);
  *num=d;//do not have to check for anything because it will just read the first double that is input
  return TC_COMMAND_OK;//returns that the command is okay if it passes everything else
}
void execute_calculation(char f_operator, double f_operand, double *p_result) {
  if(f_operator=='+'){//if its addition, do addition
    *p_result=f_operand+*p_result;
  }
  if(f_operator=='-'){//if its subtraction, do subtraction
    *p_result=*p_result-f_operand;
  }
  if(f_operator=='/'){//if its division, do division
    *p_result=*p_result/f_operand;
  }
  if(f_operator=='*'){//if its multiplication, do multiplication
    *p_result=f_operand**p_result;
  }
  if(f_operator=='^'){//if its a carrot, feed it to a pig named pow :P
    *p_result=pow(*p_result,f_operand);
  }
}

double mem_read(tc_memory_t mem, int v) {
  if (v >= TC_MEM_SZ || v<0){//if they ask for a value outside of my memory range, return the most recent value from memory
    v = 0;
  }
  int f=(mem.most_recent-v+5)%5;//fixes the wrap around issue
  return mem.vals[f];//return the value at the index they asked for
}

void mem_save(tc_memory_t *mem, double v) {
  mem->most_recent = (mem->most_recent+1)%TC_MEM_SZ;//fixes wrap around issues
  mem->vals[mem->most_recent] = v;//sets the value of most recent to the value that they just found from the caluculation
}


