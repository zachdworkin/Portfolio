#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  double a,x[100],SD,mean,mean2,Sum,Sum2,Diff;
  int b,c,count,count2;
  a=0;//initialize
  b=0;//initialize
  c=0;//initialize
  count=0;//initialize
  count2=0;//initialize
  Sum=0;//initialize
  Sum2=0;//initialize
  mean=0;//initialize
  mean2=0;//initialize
  SD=0;//initialize
  while(count<99){
    x[count]=0;//clears the entire array
    count=count+1;
  }
  count=0;//resets count
  printf("Input array of positive numbers and put -1 after the last one.\n");
  while(a!=-1){
    scanf("%lf",&a);//inputs for the array
    x[count]=a;//puts the inputs into the array
    count=count+1;
    b=count-1;//counts the number of useful inputs
  }
  double y[b],z[b];//creates arrays for the actual values and the variance
  //values
  while(c<b){
    y[c]=0;//clears all values in the y array
    z[c]=0;//clears all values in the z array
    c=c+1;
  }
  while(count2<count){
    y[count2]=x[count2];//sets all the y arrays equal to the useful values in
    //the x one
    count2=count2+1;\
  }
  count=0;//resets the count
  while(count<b){
    Sum=y[count]+Sum;//computes the Sum of all values in the y array
    count=count+1;
  }
  mean=Sum/b;//finds the first mean
  count=0;//resets count
  while(count<b){
    Diff=((y[count]-mean)*(y[count]-mean));//finds all the variance values
    //from the first mean
    z[count]=Diff;//puts the variance values into the z array
    count=count+1;
  }
  count=0;//resets the count
  while(count<b){
    Sum2=z[count]+Sum2;//computes the sum of the variance values
    count=count+1;
  }
  mean2=Sum2/b;//finds the mean of the variance values
  SD=sqrt(mean2);//computes the StdDev
  printf("The mean is %lf.\n",mean);
  printf("The Sum is %lf.\n",Sum);
  printf("The Population Standard Deviation is %lf.\n",SD);
}
