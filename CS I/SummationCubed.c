#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //sum=1+2+3+4+...+n
  //assuming n is positive whole number
  double sum,count,n;
  printf("Enter the number you want the sumation cubed of.\n");
  scanf("%lf",&n);
  count=1;//starts at 1 so the computer does not take time to do 0+0
  sum=0;
  while (count<=n){
    if ((fmod(count,2)==0)){
      sum=sum-(count*count*count);
    }else{
      sum=sum+(count*count*count);
    }
    count=count+1;
  }
  printf("The sumation cubed of %lf is %lf.\n",n,sum);
}
