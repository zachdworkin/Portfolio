#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  char w[100],l[100];
  int count;
  count=0;
  printf("Please enter a word.\n");
  scanf("%s",w);
  while(w[count]!='\0'){
    if((w[count]<='Z')&&(w[count]>='A'){
	l[count]=w[count]+('a'-'A');
    }else{
      l[count]=w[count];
    }
    count=count+1;  
  }
  printf("%s\n",l);
}
