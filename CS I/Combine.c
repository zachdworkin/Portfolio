#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  int count1,count2;//creates the count and check
  count1=0;//initialize
  count2=0;//initialize
  char a[100],b[100],c[100];//creates two word variables
  //clears all values in the word arrays
  while(count1<=99){
    a[count1]='\0';
    b[count1]='\0';
    c[count1]='\0';
    count1=count1+1;
  }
  count1=0;//resets count
  printf("Please input two lowercase words you would like combined.\n");//asks for words
  scanf("%s",a);//scans for the first word
  scanf("%s",b);//scans for the second word
  while(a[count1]!='\0'){
    c[count1]=a[count1];//sets the first word into the c word
    count1=count1+1;//increases count
  }
  while(b[count2]!='\0'){
    c[count1]=b[count2];
    count1=count1+1;//increases count
    count2=count2+1;//increases count
  }
  printf("%s.\n",c);//prints the combined word
}
