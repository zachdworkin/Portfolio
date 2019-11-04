#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //decides if a number is prime or not
  double a,x,y,z,count;
  printf("Input a positive whole number.\n");
  scanf("%lf",&x);
  y=0;
  a=0;
  z=x;
  count=2;
  while(count<x){
    if((fmod(z,count))==0){
      y=count;
      count=x;
    }else{
      count=count+1;
    }
  }
  if(y>0){
    printf("It is not Prime.\n");
  }else{
    printf("It is Prime.\n");
  }
}
