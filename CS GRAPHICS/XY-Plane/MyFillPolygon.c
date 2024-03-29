#include <FPT.h>

int swidth, sheight ;

void printArray(double x[], int n){
  int i;
  for(i=0;i<n;i++){
    printf("%3.0lf,",x[i]);
  }
  printf("\n");
}

void intersection(double A[2], double B[2], double C[2], double D[2], double ins[2]){
  double u;
  u=((A[0]*C[1] + C[0]*D[1] + D[0]*A[1]) - (A[1]*C[0] + C[1]*D[0] + D[1]*A[0])) / ((A[0]*C[1] + C[0]*B[1] + B[0]*D[1] + D[0]*A[1]) - (A[1]*C[0] + C[1]*B[0] + B[1]*D[0] + D[1]*A[0]));

  ins[0]=A[0]+u*(B[0]-A[0]);
  ins[1]=A[1]+u*(B[1]-A[1]);
}


int intersects(double x[],double y[], double a[], int n, double q){
  int i,j,k;
  double m;
  j=0;//counts the number of intersects in the a array
  for(i=0;i<n;i++){
    k = i+1 ; if (k == n) { k = 0 ; }//if you hit the end of the array, wrap around and connect the ends
    if((q-0.1)==y[i]){
      a[j]=x[i];
      j++;
    }else{
      m=x[i]+(((q-y[i])*(x[k]-x[i]))/(y[k]-y[i]));
      if(m>x[i]&&m<x[k]){
	a[j]=m;
	j++;
      }
      if(m<x[i]&&m>x[k]){
	a[j]=m;
	j++;
      }
    }
  }
  return j;
}

void sort(double x[],int n){
  int c, d, position, swap;
  for (c = 0; c < (n - 1); c++){
    position = c;
    for (d = c + 1; d < n; d++){
      if (x[position] > x[d])
        position = d;
    }
    if (position != c){
      swap = x[c];
      x[c] = x[position];
      x[position] = swap;
    }
  }
}


void myFillPolygon(double x[], double y[], int n){
  //x coord array, y coord array, intersection array, number of points in xy polygon
  double i;
  int k,l;
  int j;//number of intersects
  double a[500];
  for(l=0;l<500;l++){
    a[l]=(-1);
  }
  for(i=0.1;i<800;i=i+1){//draws every pixel
    j=intersects(x,y,a,n,i);
    if(j>1){
      sort(a,j);
      for(k=0;k<j;k=k+2){//draws the intersect lines
	if(a[k+1]!=(-1)){
	  G_line(a[k],i,a[k+1],i);
	  G_wait_key();
	}
      }
    }
  }
}



int click_and_save (double x[], double y[])
{
  int n ;
  double P[2] ;

  G_rgb(0,1,0.5) ;
  G_fill_rectangle(0,0,swidth,20) ;

  G_rgb(1,0,0) ;
  G_wait_click(P);

  n = 0 ;
  while (P[1] > 20) {
    x[n] = P[0] ;
    y[n] = P[1] ;
    G_circle(x[n],y[n],2) ;
    if (n > 0) { G_line(x[n-1],y[n-1], x[n],y[n]) ;}
    n++ ;
    G_wait_click(P) ;
  }

  if (n >= 1) { G_line(x[0],y[0], x[n-1],y[n-1]) ; }
  
  return n ;
}



double polygon_perimeter (double x[], double y[], int n)
{
  double a,b,c,p ;
  int i,j ;
  
  p = 0.0 ;
  for (i = 0 ; i < n ; i++) {
    j = i+1 ; if (j == n) { j = 0 ; }
    a = x[j] - x[i] ; b = y[j] - y[i] ;
    c = sqrt(a*a + b*b) ;
    p += c ;
  }
  
  return p ;
}




int main()
{
  double xp[1000],yp[1000],p1 ;
  int m ;
  double xq[500], yq[500],p2 ;
  int n ;
  double P[2] ;
  int i;

  
  swidth = 700 ; sheight = 700 ;
  G_init_graphics(swidth, sheight) ;
  G_rgb(0,0,0) ;
  G_clear() ;

  G_rgb(1,0,0) ;
  m = click_and_save(xp,yp) ;
  //myFillPolygon(xp,yp,m) ;
 
  G_rgb(1,0,0) ;
  n = click_and_save(xq,yq) ;

  p1 = polygon_perimeter (xp,yp,m) ;
  p2 = polygon_perimeter (xq,yq,n) ;
  printf("p1 = %lf  p2 = %lf\n",p1,p2) ;
  //sort(xp,m);
  G_rgb(0,1,0) ;
  if (p1 > p2) {
    myFillPolygon(xp,yp,m) ;
  } else if (p2 > p1) {
    myFillPolygon(xq,yq,n) ;
  } else {
    myFillPolygon(xp,yp,m) ;
    myFillPolygon(xq,yq,n) ;    
  }
  G_wait_key() ;
}
