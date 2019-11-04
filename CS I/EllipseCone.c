#include <FPT.h>
//This program draws an ellpise that creates an upsidown cone through holding down a key. By: Zach Dworkin
void draw(double n)
{
  double x[100],y[100],z[100],d,m;
  int count,count2,l,j;
  //clears the arrays
  for(count=0;count<100;count++){
    x[count]=0;
    y[count]=0;
    z[count]=0;
  }
  d=(2*M_PI)/n;
  m=0;
  for(count=0;count<n;count++){
    x[count]=300+(200*cos(m));
    y[count]=450+(100*sin(m));
    z[count]=150+(100*sin(m));
    //these extra arrays make it so that if it goes over 48, it goes back to 0
    x[count+48]=300+(200*cos(m));
    y[count+48]=450+(100*sin(m));
    z[count+48]=150+(100*sin(m));
    m=m+d;
  }
  G_rgb(1,0,0);//red
  G_polygon(x,y,n);
  G_polygon(x,z,n);
  G_rgb(0,0,0);//black
  count=0;
  j=(n/2)+1;
  while(count<=n){//does the while loop for the whole thing
    //while(count<j){//does the while loop for half
    l=0;
    for(count2=0;count2<48;count2++){
      l=count+count2;
      G_line(x[count2],y[count2],x[l],z[l]);
      //G_wait_key();//makes it so that you can watch each line
    }
    G_wait_key();//makes it so that it goes by entire ellipse at once
    //clears the screen so u can watch the next ellipse
    //count++;//part of the next line
    //if(count!=(j)){
    if(count!=n){
      G_rgb(1,1,1);
      G_fill_rectangle(0,0,600,600);
      G_rgb(1,0,0);//red
      G_polygon(x,y,n);
      G_polygon(x,z,n);
      G_rgb(0,0,0);//black
      }
    count++;//part of the if(count!=n)
  }

}
int main()
{
  double p[2];
  int v;
  printf("Please enter the number of vertices.\n");
  scanf("%d",&v);
  G_init_graphics(601,601);
  while(0==0){
    G_rgb(0,0,0);//black
    draw(v);
  }
  //makes it so that the program won't close if you hold down a key
  G_wait_click(p);
}
