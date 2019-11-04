#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  double a,b,c,d;
  scanf("%lf %lf %lf %lf", &a,&b,&c,&d);
  if((a==b)||(a==c)||(a==d)||(b==c)||(b==d)||(c==d)){
    printf("Not Distinct\n");
  }else{
    printf("Distinct\n");
  }
}
