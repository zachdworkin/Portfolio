#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  int check, count;//creates the count and check
  count=0;//initialize
  check=0;//initialize
  char a[100],b[100];//creates two word variables
  printf("Please input two lowercase words.\n");//asks for words
  scanf("%s",a);//scans for the first word
  scanf("%s",b);//scans for the second word
  while((a[count]!='\0')||(b[count]!='\0')){//checks to make sure we keep going
    //as long as we do not hit a null character
    if(a[count]!=b[count]){//checks to see if the letters are the same
      if(a[count]<b[count]){//compares the letters to see which one comes
	//first in the ascii scale
	printf("The word that comes first is %s.\n",a);//prints a's word
	a[count]='\0';//ends the while loop
	b[count]='\0';//ends the while loop
	check=1;//makes the check 1 so it doesnt print twice later
      }else{
	printf("The word that comes first is %s.\n",b);//prints b's word
	a[count]='\0';//ends the while loop
	b[count]='\0';//ends the while loop
	check=1;//makes the check 1 so it doesnt print twice later
      }
    }else{
      count=count+1;//increases count to move to the next letter in both words
    }
  }
  //prints if the words are the same
  if((a[count]==b[count])&&(check!=1)){//checks to make sure we wont print twice
    printf("The word that comes first is %s.\n",a);//prints a word
  }
}
