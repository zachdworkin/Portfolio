#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  double a,b,pi,count;
  printf("Enter a Positive Odd Whole Number\n");
  scanf("%lf",&a);
  count=a;
  pi=0;
  b=1;
  if(a!=1){
    while(count!=1){
      b=2+((a*a)/b);
      a=a-2;
      count=count-2;
    }
  }
  pi=4/(1+(1/b));
  printf("Pi = %lf\n",pi);
}
