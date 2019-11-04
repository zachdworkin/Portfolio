#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //multiplication by repeated addition by Zach
  double x,y,a,b;
  printf("Enter two integers you want multipled.\n");
  scanf("%lf %lf", &a,&b);
  x=0;
  y=0;
  if (a<0){
    while(y>a){
      x=(x-b);
      y=(y-1);
      }
  }
  if (a>0){
    while(y<a){
      x=(x+b);
      y=(y+1);
    }
  }
  printf("%lf x %lf = %lf\n",a,b,x);
  /*
  while(y<(fabs(a))){
    if(a<0){
      x=x-b;
    }else{
      x=x+b;
    }
    y=y+1;
  }
  */
}
