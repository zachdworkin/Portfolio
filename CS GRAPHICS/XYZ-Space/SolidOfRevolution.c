#include <FPT.h>
#include <string.h>
#include "M3d_matrix_tools.c"
#include "saveToFile.c"

double X[150000],Y[150000],Z[150000];
int numpoints;
int numpolys;
int psize[15000];
int con[15000][10];
double center[3];

void init_XYZ(){
  int i;
  for(i=0;i<150000;i++){
    X[i]=0;
    Y[i]=0;
    Z[i]=0;
  }
}

void findCenter(int n){
  int i;
  double xsum,ysum;
  for(i=0;i<n;i++){
    xsum+=X[i];
    ysum+=Y[i];
  }
  center[0]=xsum/n;
  center[1]=ysum/n;
  center[2]=0;
}

int checkClick(double a[]){
  if(a[0]<50&&a[1]<50){
    return 1;
  }else{
    return 0;
  }
}

int clickPolygon(){
  /*checks if the click is in the square at the bottom left. If it is, it ends
    the polygon counting cycle
  */
  double c[2],a,trans[4][4];
  int i,j,n;
  n=0;
  G_wait_click(c);
  while(checkClick(c)==0){
    X[n]=c[0];
    Y[n]=c[1];
    Z[n]=0;
    G_fill_circle(c[0],c[1],2.5);
    G_wait_click(c);
    n++;
  }
  numpoints=n;
  for(i=0;i<numpoints;i++){//connects the lines
    j=i+1;
    if(j>=numpoints){
      j=i;
    }
    G_line(X[i],Y[i],X[j],Y[j]);
  }
  G_rgb(0,0,0);
  G_fill_rectangle(0,0,51,51);
  findCenter(n);
  M3d_make_translation(trans,-center[0],-center[1],0);
  M3d_mat_mult_points(X,Y,Z, trans, X,Y,Z,n);
  return n;
}

void makePoints(int linepts, int iterations, double rot[4][4]){
  int i,j;
  double P[3], Q[3];
  for(i=0;i<3;i++){
    P[i]=0;
    Q[i]=0;
  }
  for(i=0;i<(numpoints-linepts);i++){
    Q[0]=X[i]; Q[1]=Y[i]; Q[2]=Z[i];
    M3d_mat_mult_pt(P,rot,Q);
    X[i+linepts]=P[0]; Y[i+linepts]=P[1]; Z[i+linepts]=P[2];
  }
}

void makeCon(int linepts, int iterations){
  int i,j,k,a,b,c,d,e,f;
  a=0;
  b=1;
  c=linepts+1;
  d=linepts;
  e=0;
  f=0;
  for(i=0;i<numpolys;i++){
    if(e==(linepts-1)){
      a++;
      b++;
      e=0;
    }
    con[i][0]=a;
    a++;
    con[i][1]=b;
    b++;

    con[i][2]=b+linepts-1;
    con[i][3]=a+linepts-1;
    e++;
    if(a>=numpoints-linepts){
      con[i][2]=con[i][2]-numpoints;
      con[i][3]=con[i][3]-numpoints;
    }
  }
}

int main(){
  int i,j,k,linepts,iterations;
  double angle, rot[4][4],w,v;
  init_XYZ();
  printf("Please enter your rotation angle.\n");
  scanf("%lf",&angle);
  angle=angle*(M_PI/180);//convert to radians
  G_init_graphics(800,800);
  G_rgb(0,0,0);//black
  G_clear();
  G_rgb(0,1,0);
  G_fill_rectangle(0,0,50,50);
  G_rgb(1,0,0);
  linepts=clickPolygon();
  for(i=0;i<linepts;i++){
    if(X[i]<w){w=X[i];}
    if(Y[i]<v){v=Y[i];}
  }
  M3d_make_x_rotation_cs(rot,cos(angle),sin(angle));
  iterations = ((2*M_PI)/angle);
  numpoints=linepts*iterations;
  numpolys=(linepts-1)*iterations;
  for(i=0;i<numpolys;i++){ psize[i]=4;}
  makePoints(linepts,iterations,rot);
  makeCon(linepts,iterations);
  for(i=0;i<numpoints;i++){
    X[i]=X[i]/100;
    Y[i]=Y[i]/100;
    Z[i]=Z[i]/100;
  }
  saveToFile(numpoints,X,Y,Z,numpolys,psize,con);
  G_wait_key();
  G_close();
}
