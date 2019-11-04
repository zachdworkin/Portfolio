#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //finds the midrange in a list of numbers
  double x,smallest,largest,midrange,count;
  midrange=0;
  count=0;
  printf("Enter the first score and enter -1 after the last score:\n");
  scanf("%lf",&x);
  smallest=x;
  largest=x;
  while(x!=-1){
    if(x<smallest){
      smallest=x;
    }
    if(x>largest){
      largest=x;
    }
    printf("Enter the next score:\n");
    scanf("%lf",&x);
    count=count+1;
  }
  midrange=(largest+smallest)/2;
  printf("The smallest is %lf.\nThe largest is %lf.\nThe midrange is %lf\n",smallest,largest,midrange);
}
