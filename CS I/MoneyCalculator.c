#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  double v,w,q,d,n,p;
  printf("enter positive number\n");
  scanf("%lf", &v);
  w=v/100;
  w=floor(w);
  v=v-(w*100);
  q=v/25;
  q=floor(q);
  v=v-(q*25);
  d=v/10;
  d=floor(d);
  v=v-(d*10);
  n=v/5;
  n=floor(n);
  v=v-(n*5);
  p=v/1;
  p=floor(p);
  if (w==1){
    printf("1 Dollar\n");
  }
  if (w>1){
    printf("%lf Dollars\n",w);
  }
  if (q==1){
    printf("1 Quarter\n");
  }
  if (q>1){
    printf("%1.0lf Quarters\n",q);
  }
  if (d==1){
    printf("1 Dime\n");
  }
  if (d>1){
    printf("%1.0lf Dimes\n",d);
  }
  if (n==1){
    printf("1 Nickel\n");
  }
  if (n>1){
    printf("%1.0lf Nickels\n",n);
  }
  if (p==1){
    printf("1 Penny\n");
  }
  if (p>1){
    printf("%1.0lf Pennies\n",p);
  }
}
