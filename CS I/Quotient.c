#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{

  double a,b,c,q,r;
  scanf("%lf %lf",&a,&b);
  c=a/b;
  q=floor(c);
  printf("c=%lf\n",c);
  printf("quotient=%lf\n",q);
  r=a-(b*q);
  printf("remainder = %lf",r);
  
  
}
