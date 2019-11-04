#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //the LCM(a,b)=a*b/GCD(a,b)
  double x,y,a,b,Num1,Num2,count1,count2,LCM,GCD;
  x=1;
  y=0;
  GCD=0;
  printf("Enter two positive whole numbers.\n");
  scanf("%lf %lf",&Num1,&Num2);
  count1=Num1-1;
  count2=Num2-1;
  if(Num1==Num2){
    GCD=Num1;
  }
  if(Num1!=Num2){
    if((fmod(Num1,Num2)==0)||(fmod(Num2,Num1)==0)){
      if(Num1>Num2){
        x=Num2;
        y=x;
      }
      if(Num2>Num1){
        x=Num1;
        y=x;
      }
    }
    while(x!=y){
      if((count1>count2)){
        if((fmod(Num1,count1)==0)){
  	  x=count1;
        }
        count1=count1-1;
        }else{
	  if((fmod(Num2,count2)==0)){
	    y=count2;
	  }
	  count2=count2-1;
        }
      }
    GCD=x;
  }
  LCM=(Num1*Num2)/GCD;
  printf("The LCM of %lf and %lf is %lf.\n",Num1,Num2,LCM);
}
