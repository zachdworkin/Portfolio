#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //inputs x "10,10,10,10,2,5,-5,.1,0"
  //inputs n "1,2,3,4,5,2,3,-2,0"
  //outputs "10,100,1000,100000,32,25,-125,100,undefined"
  double x,n,count,base;
  printf("Enter two numbers. The first one is the base, the second is the power.\n");
  scanf("%lf %lf",&x,&n);
  base=x;
  count=1;
  if(((x==0)&&(n<=0))){
    printf("The answer is undefined\n");//for all undefined cases
  }
  if(((n==0)&&(x!=0))){
    printf("The answer is 1.\n");//for all cases where the you raise the base to a power of 1
    count=count+1;//count must be raised so the next if statements fail.
  }
    if((n>0)){
      while(count<n){
	x=x*base;//for all cases where you raise the base to a positive power
	count=count+1;
      }
      printf("the answer is %lf.\n",x);
  }
    if(((x!=0)&&(n<0))){
      while(count>n){
	x=x/base;//for all cases where you r aise the base to a negative power
	count=count-1;
      }
      printf("the answer is %lf.\n",x);
  }
}
