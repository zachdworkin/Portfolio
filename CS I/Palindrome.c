#include <stdio.h>
#include <math.h>
#include <stdlib.h>
int main()
{
  //this program determines wheter or not a word is a palindrome
  int count,last,check;//creates variables
  count=0;//initialize
  check=0;//initialize
  last=0;//initialize
  char w[100];//creates the word variable
  printf("Please enter a lowercase word.\n");//asks for a word
  scanf("%s",w);//scans for the word
  while(w[count]!='\0'){//finds the last character
    count=count+1;//increases count
  }
  last=count-1;//creates the last value of the word
  count=0;//resets the count
  while(count<=last){//checks to see if the letters are the same as the
    //backwards count
    if(w[count]==w[last]){//compares to see if the letters are the same
      count=count+1;//increases the front
      last=last-1;//decreases the back
    }else{
      count=last;//ends the while loop
      printf("No.\n");//prints no
      check=1;//makes it so it will not print yes later
    }
  }
  if(check!=1){//checks to see if the check passed
    printf("Yes.\n");//prints yes
  }
}
