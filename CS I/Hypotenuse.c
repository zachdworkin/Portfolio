#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{

  double a,b,c;
  printf("enter base and height of a right triangle\n");
  scanf("%lf %lf",&a,&b);
  c=sqrt((a*a)+(b*b));
  printf("the hypotenuse = %lf\n",c);
  
  
}
