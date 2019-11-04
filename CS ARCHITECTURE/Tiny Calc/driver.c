#include <stdio.h>
#include "tinycalc.h"
//use this to compile the code type ./a.out to run it
// gcc -std=c99 driver.c tinycalc.c
/* put your application code in this file. */

int main(void) {

  printf("\nWelcome to TinyCalc!\n\n Enter an operation <+, - , *, /, ^>");
  printf(" followed by operand\n\n Enter 'q' or 'Q' to quit.\n\n");
  printf(" Enter 'm' or 'M' followed by location (0-4) to load a previous\n");
  printf(" result from memory.");
  printf("\n>");

  char operator;
  double operand;
  double result=0;
  tc_memory_t mem;
  int i = 0;
  while (i < TC_MEM_SZ) {//clears the memory array by setting everything to zero
    mem.vals[i]=0.0;     //this way we won't return a value from a previous compilation or a random value from memory
    i++;
  }
  while(read_command(&operator, &operand) != TC_COMMAND_QUIT){
    if((operator=='m'||operator=='M')&& operand>=0){//checks for if they wanted a memory value
      printf("%lf\n",mem_read(mem,operand));//prints the memory value
      result=mem_read(mem,operand);//set the result to what we got from memory so that they can use that value for their next calculation
    }else if(operator!='m'||operator!='M'){
      execute_calculation(operator,operand,&result);//does the calculation
      mem_save(&mem, result);//saves the result from the calculation in memory
      printf("%lf\n",result);//print out the result for the user to see
      operator='*';
      operand=1;
    }
  }
  return(0);
}
