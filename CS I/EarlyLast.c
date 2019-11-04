#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //check if the last number inputed is inputted earlier
  double x,count,last;
  x=0;//initialize
  count=0;//initialize
  printf("Enter the first positive number and enter -1 after the last number:\n");
  scanf("%lf",&x);
  last=x;
  while(x!=-1){
      if(x==first){
      count=count+1;
    }
    printf("Enter the next number:\n");
    scanf("%lf",&x);
  }
  if(count>1){
    printf("Yes\n");
  }else{
    printf("No\n");
  }
}
