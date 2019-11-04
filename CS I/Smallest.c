#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //finds the largest number in a list of numbers
  double x,smallest;
  printf("Enter the first score and enter -1 after the last score:\n");
  scanf("%lf",&x);
  smallest=x;
  while(x!=-1){
    if(x<smallest){
      smallest=x;
    }
    printf("Enter the next score:\n");
    scanf("%lf",&x);
  }
  printf("The smallest is %lf\n",smallest);
}
