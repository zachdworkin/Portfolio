#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //finds the largest number in a list of numbers
  double x,largest;
  largest=0;//initialize
  x=0;//initialize
  printf("Enter the first score and enter -1 after the last score:\n");
  scanf("%lf",&x);
  while(x!=-1){
    if(x>largest){
      largest=x;//changes largest to the latest input of x if x was greater than the old largeset.
    }
    printf("Enter the next score:\n");
    scanf("%lf",&x);
  }
  printf("The largest is %lf\n",largest);
}
