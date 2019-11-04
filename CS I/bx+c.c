#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{

  double b,c,x;
  scanf("%lf %lf", &b, &c);
  if ((b==0)&&(c==0)){
      printf("anything\n");
    }else if ((b==0)&&(c!=0)){
    printf("nothing\n");
  }else{
    x=-c/b;
    printf("%lf\n",x);
  }
  
}
