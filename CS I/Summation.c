#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //sum=1+2+3+4+...+n
  //assuming n is positive whole number
  double sum,count,x,n;
  printf("Enter the number you want the Sumation of.\n");
  scanf("%lf",&n);
  x=0;
  count=1;//starts at 1 so the computer does not take time to do 0+0
  sum=0;
  while (count<=n){
    sum=sum+count;
    count=count+1;
  }
  printf("The sumation of %lf is %lf.\n",n,sum);
}
