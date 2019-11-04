#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //finds the mean of a list of numbers
  double x,mean,count;
  mean=0;
  x=0;
  count=0;
  printf("Enter the first score and enter -1 after the last score:\n");
  scanf("%lf",&x);
  while(x!=-1){
    mean=mean+x;
    printf("Enter the next score:\n");
    scanf("%lf",&x);
    count=count+1;
  }
  mean=mean/count;
  printf("The mean is %lf\n",mean);
  
}
