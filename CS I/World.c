#include <stdio.h>
#include <math.h>
#include <stdlib.h>
char w[50][50];
void CreateDeadWorld()
{
  int r,c;
  r=0;
  while(r<50){
    c=0;
    while(c<50){
      w[r][c]='.';
      c=c+1;
    }
    r=r+1;
  }
}
void CreateLivingCells()
{
  int r,c;
  r=0;
  c=0;
  while((r>=0)&&(r<50)&&(c>=0)&&(c<50)){
    printf("Input row and column of life form.\n");
    scanf("%d %d",&r,&c);
    w[r][c]='*';
  }

}
void PrintTheWorld()
{
  int r,c;
  r=0;
  while(r<50){
    printf("{");
    c=0;
    while(c<50){
      printf("%c",w[r][c]);
      c=c+1;
    }
    r=r+1;
    printf("}\n");
  }
  printf("Type a number to continue\n");
  scanf("%d",&r);
}
int numneighbors(int r,int c)
{
  int n;
  char q;
  n=0;
  //if((r!=0)&&(c!=0)){
    q=w[r-1][c-1];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
  //if(r!=0){
    q=w[r-1][c];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
  //if((r!=0)&&(c!=49)){
    q=w[r-1][c+1];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
    //if(c!=0){
    q=w[r][c-1];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
    //if(c!=49){
    q=w[r][c+1];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
    //if((r!=49)&&(c!=0)){
    q=w[r+1][c-1];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
    //if(r!=49){
    q=w[r+1][c];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
    //if((r!=49)&&(c!=49)){
    q=w[r+1][c+1];
    if((q=='*')||(q=='d')){
      n=n+1;
      //}
  }
    //if(n!=0){
    //printf("%d,%d...%d\n",r,c,n);
    //}
  return n;
}
void ObeyTheRulesMarkDeathAndLife()
{
  int r,c,n;
  r=1;
  while(r<49){
    c=1;
    while(c<49){
      n=numneighbors(r,c);
      //mark for death
      if((w[r][c]=='*')&&((n>3)||(n<2))){
	w[r][c]='d';
      }      
      c=c+1;
    }
    //mark for life
    c=1;
    while(c<49){
      n=numneighbors(r,c);
      if((w[r][c]=='.')&&(numneighbors(r,c)==3)){
	  w[r][c]='l';
      }
      c=c+1;
    }
    r=r+1;
  }
}
void ImplimentChanges()
{
  int r,c,n;
  r=0;
  while(r<50){
    c=0;
    while(c<50){
      n=numneighbors(r,c);
      //marked for life
      if((w[r][c]=='l')){
	  w[r][c]='*';
      }
      if((w[r][c]=='l')&&(n!=3)){
	  w[r][c]='.';
      }
      c=c+1;
    }
    c=0;
    while(c<50){
      n=numneighbors(r,c);
      //marked for death
      if((w[r][c]=='d')&&((n<2)||(n>3))){
   	  w[r][c]='.';
      }
      if((w[r][c]=='d')&&((n!=2)||(n!=3))){
   	  w[r][c]='.';
      }
      c=c+1;
    }
    r=r+1;
  }
}
int main()
{
  //rules
  //a living cell will only continue to live if it has 2 or more living neighbords
  //a dead cell will come alive it there are exactly three living neighbors
  //a cell will die if it has 4 or more neighbors
  CreateDeadWorld(w);
  CreateLivingCells(w);
  while(0==0){
    PrintTheWorld(w);
    ObeyTheRulesMarkDeathAndLife(w);
    ImplimentChanges(w);
  }
  return 0;
}
