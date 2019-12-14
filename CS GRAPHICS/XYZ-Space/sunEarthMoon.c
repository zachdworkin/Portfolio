#include <FPT.h>
#include <string.h>
typedef
struct{
  int objnum;
  int polynum;
  double dist;
}POLYGON;

int numobjects;
int numpoints[10];
double x[10][15000],y[10][15000],z[10][15000];
int numpolys[10];
int psize[10][14000];
int con[10][14000][8];
double xbar[10][15000],ybar[10][15000];
double H,ha;
double color[10][3],light[3],ambient,diffuseMax;
int specPow;
POLYGON shape[15000];
/*
 * used for quick sort (qsort) to compare two things
 */
int compare (const void *p, const void *q)
{
  POLYGON *a, *b ;

  a = (POLYGON*)p ;
  b = (POLYGON*)q ;

  if  (((*a).dist) < ((*b).dist)) return -1 ;
  else if (((*a).dist) > ((*b).dist)) return 1 ;
  else return 0 ;
}
/*

 ( x')          (x)
 ( y')  =   M * (y)  
 ( z')  =   M * (z)
 ( 1 )          (1)

instead of (x',y',z',1) = (x,y,z'1) * M  

*/



int M3d_print_mat (double a[4][4])
{
  int r,c ;
  for (r = 0 ; r < 4 ; r++ ) {
      for (c = 0 ; c < 4 ; c++ ) {
           printf(" %12.4lf ",a[r][c]) ;
      }
      printf("\n") ;
  }

  return 1 ;
} 





int M3d_copy_mat (double a[4][4], double b[4][4])
// a = b
{
  int r,c ;
  for (r = 0 ; r < 4 ; r++ ) {
      for (c = 0 ; c < 4 ; c++ ) {
           a[r][c] = b[r][c] ;
      }
  }

  return 1 ;
} 





int M3d_make_identity (double a[4][4])
// a = I
{
  int r,c ;
  for (r = 0 ; r < 4 ; r++ ) {
      for (c = 0 ; c < 4 ; c++ ) {
           if (r == c) a[r][c] = 1.0 ;
               else    a[r][c] = 0.0 ;
      }
  }

  return 1 ;
} 





int M3d_make_translation (double a[4][4], double dx, double dy, double dz)
{
  M3d_make_identity(a) ;
  a[0][3] =  dx ;  a[1][3] = dy ; a[2][3] = dz ;  
  return 1 ;
}





int M3d_make_scaling (double a[4][4], double sx, double sy, double sz)
{
  M3d_make_identity(a) ;
  a[0][0] =  sx ;  a[1][1] = sy ;  a[2][2] = sz ;
  return 1 ;
}

int M3d_make_x_rotation_cs (double a[4][4], double cs, double sn)
// this one assumes cosine and sine are already known
{
  M3d_make_identity(a) ;

  a[1][1] =   cs ;  a[1][2] = -sn ;
  a[2][1] =   sn ;  a[2][2] =  cs ;

  return 1 ;
}

int M3d_make_y_rotation_cs (double a[4][4], double cs, double sn)
// this one assumes cosine and sine are already known
{
  M3d_make_identity(a) ;

  a[0][0] =   cs ;  a[0][2] = sn ;
  a[2][0] =  -sn ;  a[2][2] = cs ;

  return 1 ;
}

int M3d_make_z_rotation_cs (double a[4][4], double cs, double sn)
// this one assumes cosine and sine are already known
{
  M3d_make_identity(a) ;

  a[0][0] =   cs ;  a[0][1] = -sn ;
  a[1][0] =   sn ;  a[1][1] =  cs ;

  return 1 ;
}





int M3d_mat_mult (double res[4][4], double a[4][4], double b[4][4])
// res = a * b
// this is SAFE, i.e. the user can make a call such as 
// M3d_mat_mult(p,  p,q) or M3d_mat_mult(p,  q,p) or  M3d_mat_mult(p, p,p)
{
  double sum ;
  int k ;
  int r,c ;
  double tmp[4][4] ;

  for (r = 0 ; r < 4 ; r++ ) {
      for (c = 0 ; c < 4 ; c++ ) {
           sum = 0.0 ;
           for (k = 0 ; k < 4 ; k++) {
                 sum = sum + a[r][k]*b[k][c] ;
           }
           tmp[r][c] = sum ;
      }
  }


  M3d_copy_mat (res,tmp) ;

  return 1 ;
}





int M3d_mat_mult_pt (double P[3],   double m[4][4], double Q[3])
// P = m*Q
// SAFE, user may make a call like M2d_mat_mult_pt (W, m,W) ;
{
  double u,v,t ;

  u = m[0][0]*Q[0] + m[0][1]*Q[1] + m[0][2]*Q[2] + m[0][3] ;
  v = m[1][0]*Q[0] + m[1][1]*Q[1] + m[1][2]*Q[2] + m[1][3] ;
  t = m[2][0]*Q[0] + m[2][1]*Q[1] + m[2][2]*Q[2] + m[2][3] ;

  P[0] = u ;
  P[1] = v ;
  P[2] = t ;
  
  return 1 ;
}





int M3d_mat_mult_points (double *X, double *Y, double *Z,
                         double m[4][4],
                         double *x, double *y, double *z, int numpoints)
// |X0 X1 X2 ...|       |x0 x1 x2 ...|
// |Y0 Y1 Y2 ...| = m * |y0 y1 y2 ...|
// |Z0 Z1 Z2 ...|       |Z0 Z1 Z2 ...|
// | 1  1  1 ...|       | 1  1  1 ...|

// SAFE, user may make a call like M3d_mat_mult_points (x,y,z, m, x,y,z, n) ;
{
  double u,v,t ;
  int i ;

  for (i = 0 ; i < numpoints ; i++) {
    u = m[0][0]*x[i] + m[0][1]*y[i] + m[0][2]*z[i] + m[0][3] ;
    v = m[1][0]*x[i] + m[1][1]*y[i] + m[1][2]*z[i] + m[1][3] ;
    t = m[2][0]*x[i] + m[2][1]*y[i] + m[2][2]*z[i] + m[2][3] ;

    X[i] = u ;
    Y[i] = v ;
    Z[i] = t ;
  }

  return 1 ;
}
double dot(double A[3], double B[3]){
  double dot;
  dot=A[0]*B[0] + A[1]*B[1] + A[2]*B[2];
  return dot;
}

void cross(double C[3],double A[3], double B[3]){
  C[0]=(A[1]*B[2])-(A[2]*B[1]);
  C[1]=-((A[0]*B[2])-(A[2]*B[0]));
  C[2]=(A[0]*B[1])-(A[1]*B[0]);
}


/**
 *finds the distance of a vector needed in calculating its unit vector
 */
double findDist(double V[3]){
  return sqrt((V[0]*V[0])+(V[1]*V[1])+(V[2]*V[2]));
}


/**
 * adds two vecotrs B and C and returns the new vector in A
 */
void addVectors(double A[3],double B[3], double C[3]){
  double P[3];
  P[0]=C[0]+B[0];
  P[1]=C[1]+B[1];
  P[2]=C[2]+B[2];
  
  A[0]=P[0];
  A[1]=P[1];
  A[2]=P[2];
}

/**
 * negates vector B and saves it in A
 */
void negateVector(double A[3], double B[3]){
  A[0]=-B[0];
  A[1]=-B[1];
  A[2]=-B[2];
}

/**
 *scales Vector B by k and saves it into A
 */
void scaleVector(double A[3],double B[3],double k){
  A[0]=k*B[0];
  A[1]=k*B[1];
  A[2]=k*B[2];
}

/**
 * makes a reflection vector of Lu and saves/returns it as Ru
 * PARAM Lu (unit light vector)
 * PARAM Nu (unit normal vector) 
 * PARAM alpha (the amount needed to scale Nu to get to Ru)
 */
void makeReflectionVector(double Ru[3], double Lu[3], double Nu[3],double alpha){
  double nLu[3],Su[3];
  negateVector(nLu,Lu);
  scaleVector(Su,Nu,2*alpha);
  addVectors(Ru,nLu,Su);
}

/*
 *prints the array of shapes
 */
void print_array()
{
  int i ;
  for (i = 0 ; i < 10354 ; i++) {
    printf("%d %d %lf\n",shape[i].objnum, shape[i].polynum, shape[i].dist) ;
  }
  printf("\n") ;
}
/*
 *makes the color arrays so each of the 10 potential objects will be a different color
 */
void makeColors(){
  color[2][0]=1; color[2][1]=1; color[2][2]=1;//white
  color[1][0]=0; color[1][1]=0; color[1][2]=1;//blue
  color[0][0]=1; color[0][1]=1; color[0][2]=0;//yellow
}

/*
 *find the center of an object using its center of mass
 */
void findCenter(int k){
  int i;
  double sumx,sumy,sumz;
  sumx=0;
  sumy=0;
  sumz=0;
  for(i=0;i<numpoints[k];i++){
    sumx+=x[k][i];
    sumy+=y[k][i];
    sumz+=z[k][i];
  }
  x[k][numpoints[k]]=sumx/numpoints[k];
  y[k][numpoints[k]]=sumy/numpoints[k];
  z[k][numpoints[k]]=sumz/numpoints[k];
}

/*
 * creates the shape array and fills out every object information
 */
int makeObjects(){
  int i,j,k,r,t;
  r=0; t=0;
  for(i=0;i<numobjects;i++){
    for(j=0;j<numpolys[i];j++){
      if(z[i][con[i][j][0]]>0){
	shape[r].objnum=i;
	shape[r].polynum=j;
	shape[r].dist = z[i][con[i][j][0]];
	r++;
      }
    }
	
  }
  return r;
}

void normalVector(double G[3], int onum, int polynum){
  double dot,AB[3],AC[3];
  AB[0]=x[onum][con[onum][polynum][1]]-x[onum][con[onum][polynum][0]];
  AB[1]=y[onum][con[onum][polynum][1]]-y[onum][con[onum][polynum][0]];
  AB[2]=z[onum][con[onum][polynum][1]]-z[onum][con[onum][polynum][0]];
  
  AC[0]=x[onum][con[onum][polynum][2]]-x[onum][con[onum][polynum][0]];
  AC[1]=y[onum][con[onum][polynum][2]]-y[onum][con[onum][polynum][0]];
  AC[2]=z[onum][con[onum][polynum][2]]-z[onum][con[onum][polynum][0]];
  cross(G,AB,AC);
}

double power(double A, int B){
  int i;
  double ans;
  ans=1;
  for(i=0;i<B;i++){
    ans=ans*A;
  }
  return ans;
}

double findIntensity(double N[3],int onum, int polynum){
  double Nu[3],Lu[3],Eu[3],Ru[3],E[3],dist,alpha,beta,angle,intensity;
  E[0]=-x[onum][con[onum][polynum][0]];
  E[1]=-y[onum][con[onum][polynum][0]];
  E[2]=-z[onum][con[onum][polynum][0]];
  
  dist=findDist(N);
  Nu[0]=N[0]/dist; Nu[1]=N[1]/dist; Nu[2]=N[2]/dist;
  
  dist=findDist(light);
  Lu[0]=light[0]/dist; Lu[1]=light[1]/dist; Lu[2]=light[2]/dist;
  
  dist=findDist(E);
  Eu[0]=E[0]/dist; Eu[1]=E[1]/dist; Eu[2]=E[2]/dist;
  
  if((dot(Nu,Eu)*dot(Lu,Nu))<0){
    return ambient;
  }
  
  alpha=dot(Lu,Nu);
  if(alpha < 0  && dot(Eu,Nu)<0){
    negateVector(Nu,Nu);
  }

  
  alpha=dot(Lu,Nu); // recalculate
  
  makeReflectionVector(Ru,Lu,Nu,alpha);
  
  beta=dot(Ru,Eu);
  
  intensity=ambient;

  
  if (alpha < 0) { alpha = 0 ; }
  if (beta < 0) { beta = 0 ; }

  intensity = ambient + diffuseMax*alpha + (1-ambient-diffuseMax)*power(beta,specPow) ;

  return intensity;
} 

/*
 * makes it so you can see the objects on the 2D window
 */
void moveToScreen(int k){
  int i,j;
  H=tan(ha*(M_PI/180));
  for(i=0;i<numpoints[k];i++){
    xbar[k][i]=((x[k][i]/z[k][i])*(400/H))+400;
    ybar[k][i]=((y[k][i]/z[k][i])*(400/H))+400;
  }
}

/*
 * PARAM ii = index number for shape array
 */
void drawPoly(int ii){
  int i,p,size,onum;
  double a[10],b[10],dist,N[3],intensity,fr,fg,fb,f,ad,CV[3];
  onum=shape[ii].objnum;
  p=shape[ii].polynum;
  size=psize[onum][p];
  dist=shape[ii].dist;
  G_rgb(color[onum][0],color[onum][1],color[onum][2]);
  for(i=0;i<size;i++){
    a[i]=xbar[onum][con[onum][p][i]];
    b[i]=ybar[onum][con[onum][p][i]];
  }
  normalVector(N,onum,p);
  intensity=findIntensity(N,onum,p);

  ad=ambient+diffuseMax;
  if(intensity>=ad){
    f=(intensity-ad) / (1-ad);
    CV[0]=color[onum][0]+((1-color[onum][0])*f);
    CV[1]=color[onum][1]+((1-color[onum][1])*f);
    CV[2]=color[onum][2]+((1-color[onum][2])*f);
  }else{
    f=intensity/ad;
    CV[0]=color[onum][0]*f;
    CV[1]=color[onum][1]*f;
    CV[2]=color[onum][2]*f;
  }
  G_rgb(CV[0],CV[1],CV[2]);
  G_fill_polygon(a,b,size);
}

/*
 * PARAM hide = determination if the object should be seen on the screen
 * PARAM wire = determination if the objects wire frame should be seen
 * PARAM np = number of points in the shape array
 */
void drawAllObjects(int np){
  int i=0;
  for(i=0;i<numobjects;i++){
    moveToScreen(i);
  }
  //moveLightToScreen();
  for(i=np-1;i>= 0;i--){
      drawPoly(i);  
  }
  //drawLight();
}

void scale(int onum, double sx, double sy, double sz){
  double sunScale[4][4],sunTrans[4][4],sunM[4][4];
  M3d_make_scaling(sunScale,sx,sy,sz);
  M3d_make_translation(sunTrans,-x[onum][numpoints[onum]],-y[onum][numpoints[onum]],-z[onum][numpoints[onum]]);
  M3d_mat_mult(sunM,sunScale,sunTrans);
  M3d_make_translation(sunTrans,x[onum][numpoints[onum]],y[onum][numpoints[onum]],z[onum][numpoints[onum]]);
  M3d_mat_mult(sunM,sunTrans,sunM);
  M3d_mat_mult_points(x[onum],y[onum],z[onum],sunM,x[onum],y[onum],z[onum],numpoints[onum]+1);
}

void moveCelestialBeingsToScreen(int onum, double sz){
  double sunTrans[4][4],sunM[4][4],sunRotZ[4][4],sunTransZ[4][4];
  M3d_make_translation(sunTransZ,0,0,sz);
  M3d_make_z_rotation_cs(sunRotZ,cos(90*M_PI/180),sin(90*M_PI/180));
  M3d_make_translation(sunTrans,-x[onum][numpoints[onum]],-y[onum][numpoints[onum]],-z[onum][numpoints[onum]]);
  M3d_mat_mult(sunM,sunTrans,sunTransZ);
  M3d_mat_mult(sunM,sunRotZ,sunM);
  M3d_make_translation(sunTrans,x[onum][numpoints[onum]],y[onum][numpoints[onum]],z[onum][numpoints[onum]]);
  M3d_mat_mult(sunM,sunTrans,sunM);
  M3d_mat_mult_points(x[onum],y[onum],z[onum],sunM,x[onum],y[onum],z[onum],numpoints[onum]+1);
}

void makeSunMove(double sunM[4][4]){
  double sunRotY[4][4], sunTrans[4][4];
  M3d_make_y_rotation_cs(sunRotY,cos((360/300)*M_PI/180),sin((360/300)*M_PI/180));
  M3d_make_translation(sunTrans,-x[0][numpoints[0]],-y[0][numpoints[0]],-z[0][numpoints[0]]);
  M3d_mat_mult(sunM,sunRotY,sunTrans);
  M3d_make_translation(sunTrans,x[0][numpoints[0]],y[0][numpoints[0]],z[0][numpoints[0]]);
  M3d_mat_mult(sunM,sunTrans,sunM);
}

void makeEarthMove(double earthM[4][4]){
  double earthRotY[4][4],earthTrans[4][4],EC[3];
  M3d_make_y_rotation_cs(earthRotY,cos((360/300)*M_PI/180),sin((360/300)*M_PI/180));
  M3d_make_translation(earthTrans,-x[0][numpoints[0]],-y[0][numpoints[0]],-z[0][numpoints[0]]);
  M3d_mat_mult(earthM,earthRotY,earthTrans);
  M3d_make_translation(earthTrans,x[0][numpoints[0]],y[0][numpoints[0]],z[0][numpoints[0]]);
  M3d_mat_mult(earthM,earthTrans,earthM);
}

void makeMoonMove(double moonM[4][4]){
  double moonRotY[4][4],moonTrans[4][4];
  M3d_make_y_rotation_cs(moonRotY,cos((360/50)*M_PI/180),sin((360/50)*M_PI/180));
  M3d_make_translation(moonTrans,-x[1][numpoints[1]],-y[1][numpoints[1]],-z[1][numpoints[1]]);
  M3d_mat_mult(moonM,moonRotY,moonTrans);
  M3d_make_translation(moonTrans,x[1][numpoints[1]],y[1][numpoints[1]],z[1][numpoints[1]]);
  M3d_mat_mult(moonM,moonTrans,moonM);
}

int main(int argc, char **argv){
  int i,j,k;//k is the object number
  FILE *fp;
  double transa[4][4],transb[4][4],matrixx[4][4],matrixy[4][4],matrixz[4][4],rotx[4][4],roty[4][4],rotz[4][4],transx[4][4],transy[4][4],transz[4][4],trans[4][4],matrix[4][4],rot[4][4];
  double sunTrans[4][4],sunRotY[4][4],sunRotZ[4][4],sunM[4][4],earthM[4][4],moonM[4][4];
  double cs,sn,intensity;
  int onum,q,np;
  int n;
  numobjects=argc-1;
  for(k=0;k<numobjects;k++){
    fp=fopen(argv[k+1], "r");
    if(fp==NULL){
      printf("Can't open file %s\n",argv[k+1]);
      exit(0);
    }
    fscanf(fp,"%d",&numpoints[k]);//scans out the number of points
    for(i=0;i<numpoints[k];i++){//saves the points into x,y,z arrays
      fscanf(fp,"%lf %lf %lf",&x[k][i], &y[k][i], &z[k][i]);
    }
    fscanf(fp,"%d",&numpolys[k]);//scans out the number of polygons those points form
    for(i=0;i<numpolys[k];i++){//fills the psize and conuctivity arrays
      fscanf(fp,"%d",&psize[k][i]);
      for(j=0;j<psize[k][i];j++){
	fscanf(fp,"%d",&con[k][i][j]);
      }
    }
    findCenter(k);
    double du[4][4] ;
    M3d_make_translation(du    ,-x[k][numpoints[k]],
				-y[k][numpoints[k]],
				-z[k][numpoints[k]]) ;
					 
    M3d_mat_mult_points (x[k],y[k],z[k],  du,  x[k],y[k],z[k],
			 numpoints[k]+1) ;
  }
  makeColors();
  np=makeObjects();
  qsort (shape, np, sizeof(POLYGON), compare) ;
  /*************************
   *Drawing Portion of Main*
   *************************/
  ha=17;
  light[0] = 0 ; light[1] = 0 ; light[2] = -20 ;
  ambient = 0.2 ; diffuseMax = 0.5 ; specPow = 50;
  G_init_graphics(800,800);
  G_rgb(0,0,0);
  G_clear();
  cs=cos(.01);
  sn=sin(.01);
  onum = 0 ; // onum marks the current object
  n = numpoints[onum] ; // other else ifs need this
  q='a';
  scale(1,.5,.5,.5);
  scale(2,.25,.25,.25);
  moveCelestialBeingsToScreen(0,20);
  moveCelestialBeingsToScreen(1,16.5);
  moveCelestialBeingsToScreen(2,15);
  makeSunMove(sunM);
  makeEarthMove(earthM);
  makeMoonMove(moonM);
  while (1) {
    if(q=='q'){
      break;
    }
    G_rgb(0,0,0) ; 
    G_clear() ;
    G_rgb(intensity,intensity,intensity) ;
    np=makeObjects();
    qsort(shape, np, sizeof(POLYGON),compare);
    drawAllObjects(np) ;
    q = G_wait_key() ;
    M3d_mat_mult_points(x[0],y[0],z[0],sunM,x[0],y[0],z[0],numpoints[0]+1);
    M3d_mat_mult_points(x[1],y[1],z[1],earthM,x[1],y[1],z[1],numpoints[1]+1);
    M3d_mat_mult_points(x[2],y[2],z[2],earthM,x[2],y[2],z[2],numpoints[2]+1);
    M3d_mat_mult_points(x[2],y[2],z[2],moonM,x[2],y[2],z[2],numpoints[2]+1);
    makeMoonMove(moonM);
  }
  G_close();
  return 0;
}
