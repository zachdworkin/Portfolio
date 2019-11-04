#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  double Lo,m,Hi,c,x;
  printf("Enter a positive whole number\n");
  scanf("%lf",&x);
  Lo=0;
  Hi=x;
  m=1;
  c=1;
  while((c>(x+.000001))||(c<(x-.000001))){
    m=((Lo+Hi)/2);
    c=m*m;
    if(c>x){
      Hi=Hi-(Hi-m);
    }
    if(c<x){
      Lo=Lo+(m-Lo);
    }
  }
  printf("The Square Root of %lf is %lf\n",x,m);
}
