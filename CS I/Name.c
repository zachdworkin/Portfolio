#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  char f[100],l[100];
  int n;
  printf("Please enter your first and last name\n");
  scanf("%s",f);
  scanf("%s",l);
  printf("Good Morning %s %s\n",f,l);
  n=0;//initialize
  while(f[n]!='/0'){
    n=n+1;
  }
  printf("Your first name has %d letters in it.\n"),n);
}
