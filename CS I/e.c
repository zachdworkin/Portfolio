#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //for a!
  double a,b,c,count;
  printf("Enter number you want e'd.\n");
  scanf("%lf", &a);
  b=1;
  c=1;
  count=1;
  while(count<=a){
    //c=c*(1/count);//for normal factorial
    c=c*(-1)*(1/count);//for alternating between + and -
    b=c+b;
    count=count+1;
  }
  printf("The factorial of your number is %lf.\n",b);
  
}
