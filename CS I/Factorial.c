#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  double a,b,count;
  printf("Enter number you want factorialized.\n");
  scanf("%lf", &a);
  b=1;
  count=1;
  while(count<=a){
    b=b*count;
    count=count+1;
  }
  printf("The factorial of your number is %lf.\n",b);
  
}
