#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  double a;
  printf("enter any number\n");
  scanf("%lf", &a);
  a=fabs(a);
  printf("here is the absoulte value of your number %lf\n", a);
}
