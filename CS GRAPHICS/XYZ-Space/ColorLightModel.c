#include <FPT.h>
#include <string.h>
#include "M3d_matrix_tools.c"
#include "dot_cross.c"
#include "vectorTools.c"
/*
 * Light Model Lab
 */
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

// ./a.out box123.xyz sphere.xyz deathstr.xyz stegsaur.xyz torus2.xyz jvase.xyz zylonjag.xyz ellipsoid.xyz helicop.xyz cylinder.xyz
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
  color[0][0]=1; color[0][1]=1; color[0][2]=1;//white
  color[1][0]=1; color[1][1]=0; color[1][2]=0;//red
  color[2][0]=0; color[2][1]=1; color[2][2]=0;//green
  color[3][0]=0; color[3][1]=0; color[3][2]=1;//blue
  color[4][0]=1; color[4][1]=0; color[4][2]=1;//purple
  color[5][0]=1; color[5][1]=1; color[5][2]=0;//yellow
  color[6][0]=0; color[6][1]=1; color[6][2]=1;//cyan
  color[7][0]=.5; color[7][1]=1; color[7][2]=.5;//light green
  color[8][0]=1; color[8][1]=.5; color[8][2]=.5;//pink
  color[9][0]=.5; color[9][1]=.5; color[9][2]=1;//purple-blue
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
 * old draw for drawing one object at once
 */
void draw(int onum){
  int i,j,p;
  double a[15000], b[15000];
  G_rgb(0,1,0);
  moveToScreen(onum);
  for(i=0;i<numpolys[onum];i++){
    for(j=0;j<psize[onum][i];j++){
      a[j]=xbar[onum][con[onum][i][j]];
      b[j]=ybar[onum][con[onum][i][j]];
    }
    G_rgb(color[onum][0],color[onum][1],color[onum][2]);
    G_fill_polygon(a,b,psize[onum][i]);
    G_rgb(0,0,0);
    G_polygon(a,b,psize[onum][i]);
  } 
}

/*
 * PARAM ii = index number for shape array
 * PARAM wire = determination of whether or not to draw the wire frame
 */
void drawPoly(int ii, int wire[10],int col[10]){
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
  
  if(col[onum]>0){
    G_rgb(CV[0],CV[1],CV[2]);
  }else{
    G_rgb(intensity,intensity,intensity);
  }
  G_fill_polygon(a,b,size);
  if(wire[onum]>0){
    G_rgb(0,0,0);
    G_polygon(a,b,size);
  }
}

/*
 * PARAM hide = determination if the object should be seen on the screen
 * PARAM wire = determination if the objects wire frame should be seen
 * PARAM np = number of points in the shape array
 */
void drawAllObjects(int hide[10],int wire[10],int col[10], int np){
  int i=0;
  for(i=0;i<numobjects;i++){
    moveToScreen(i);
  }
  //moveLightToScreen();
  for(i=np-1;i>= 0;i--){
    if(hide[shape[i].objnum]>0){
      drawPoly(i,wire,col);
    }    
  }
  //drawLight();
}

int main(int argc, char **argv){
  int i,j,k;//k is the object number
  FILE *fp;
  int v,key,n;
  int sign = 1 ;
  int wire[10];
  int action = 't' ;
  double transa[4][4],transb[4][4],matrixx[4][4],matrixy[4][4],matrixz[4][4],rotx[4][4],roty[4][4],rotz[4][4],transx[4][4],transy[4][4],transz[4][4],trans[4][4],matrix[4][4],rot[4][4];
  double cs,sn,intensity;
  int onum,q,np;
  int change=1;
  int col[10];
  int hide[10];
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
  //print_array();
  /*************************
   *Drawing Portion of Main*
   *************************/
  for(i=0;i<10;i++){
    hide[i]=1;
    wire[i]=-1;
    col[i]=-1;
  }
  printf("Please enter your viewing angle\n");
  scanf("%lf",&ha);
  printf("Please enter your light sorce location x,y,z\n");
  scanf("%lf %lf %lf",&light[0], &light[1], &light[2]);
  printf("Please enter your ambient light value, your diffuse max value, and your specular power value.\n");
  scanf("%lf %lf %d", &ambient, &diffuseMax, &specPow);
  //ambient=0.2; diffuseMax=0.5; specPow=50;
  G_init_graphics(800,800);
  G_rgb(0,0,0);
  G_clear();
  cs=cos(.01);
  sn=sin(.01);
  onum = 0 ; // onum marks the current object
  n = numpoints[onum] ; // other else ifs need this
  drawAllObjects(hide,wire,col,np);
  while (1) {

    q = G_wait_key() ;
    
    if (q == 'q') {
      exit(0) ;

    }else if (q == 's'){//switch backface
      change=-change;

    }else if (q == 'a'){//add color
      col[onum]=-col[onum];
      
    } else if (q == 'c') {//zoom in or out
      sign = -sign ;
      cs=cos(sign*.01);
      sn=sin(sign*.01);

    } else if (q == 't') {//translate
      action = q ;

    }else if(q=='h'){
      hide[onum] = -hide[onum];

    }else if(q=='w'){
      wire[onum]=-wire[onum];

    }else if(q=='l'){//move lightsorce
      action = q ;
     
    } else if (q == 'r') {//rotate
      action = q ;

    }else if (('0' <= q) && (q <= '9')) {
      k = q - '0' ;  
      if (k < numobjects) { onum = k ; }
      
    }else if ((q == 'x' ) && (action == 'l')){
        M3d_make_translation(transx,sign,0,0);
        M3d_mat_mult_pt(light,transx,light);
      
    }else if ((q == 'y' ) && (action == 'l')){
       M3d_make_translation(transy,0,sign,0);
       M3d_mat_mult_pt(light, transy, light);
      
    }else if ((q == 'z' ) && (action == 'l')){
        M3d_make_translation(transz,0,0,sign);
        M3d_mat_mult_pt(light, transz, light);
      
    } else if ((q == 'x') && (action == 't')) {
        M3d_make_translation(transx,sign,0,0);
        M3d_mat_mult_points (x[onum],y[onum],z[onum],  transx,x[onum],y[onum],z[onum],numpoints[onum]+1) ;

    } else if ((q == 'y') && (action == 't')) {
       M3d_make_translation(transy,0,sign,0);
       M3d_mat_mult_points (x[onum],y[onum],z[onum],  transy,x[onum],y[onum],z[onum],numpoints[onum]+1) ;

    } else if ((q == 'z') && (action == 't')) {
        M3d_make_translation(transz,0,0,sign);
        M3d_mat_mult_points (x[onum],y[onum],z[onum],transz,x[onum],y[onum],z[onum],numpoints[onum]+1) ;

    } else if ((q == 'x') && (action == 'r')) {
      M3d_make_translation(transa,-x[onum][numpoints[onum]],-y[onum][numpoints[onum]],-z[onum][numpoints[onum]]);
      M3d_make_x_rotation_cs(rotx,cs,sn);
      M3d_mat_mult(matrixx,rotx,transa);
      M3d_make_translation(transb,x[onum][numpoints[onum]],y[onum][numpoints[onum]],z[onum][numpoints[onum]]);
      M3d_mat_mult(matrixx,transb,matrixx);
      M3d_mat_mult_points (x[onum],y[onum],z[onum],  matrixx,x[onum],y[onum],z[onum],numpoints[onum]+1) ;

    } else if ((q == 'y') && (action == 'r')) {
      M3d_make_translation(transa,-x[onum][numpoints[onum]],-y[onum][numpoints[onum]],-z[onum][numpoints[onum]]);
      M3d_make_y_rotation_cs(roty,cs,sn);
      M3d_mat_mult(matrixy,roty,transa);
      M3d_make_translation(transb,x[onum][numpoints[onum]],y[onum][numpoints[onum]],z[onum][numpoints[onum]]);
      M3d_mat_mult(matrixy,transb,matrixy);
      M3d_mat_mult_points (x[onum],y[onum],z[onum],  matrixy,x[onum],y[onum],z[onum],numpoints[onum]+1) ;

    } else if ((q == 'z') && (action == 'r')) {
      M3d_make_translation(transa,-x[onum][numpoints[onum]],-y[onum][numpoints[onum]],-z[onum][numpoints[onum]]);
      M3d_make_z_rotation_cs(rotz,cs,sn);
      M3d_mat_mult(matrixz,rotz,transa);
      M3d_make_translation(transb,x[onum][numpoints[onum]],y[onum][numpoints[onum]],z[onum][numpoints[onum]]);
      M3d_mat_mult(matrixz,transb,matrixz);
      M3d_mat_mult_points (x[onum],y[onum],z[onum],  matrixz,x[onum],y[onum],z[onum],numpoints[onum]+1) ;

    } else {
      printf("no action\n") ;
      
    }
    G_rgb(0,0,0) ; 
    G_clear() ;
    G_rgb(intensity,intensity,intensity) ;
    np=makeObjects();
    qsort(shape, np, sizeof(POLYGON),compare);
    drawAllObjects(hide,wire,col,np) ;
    //draw(onum) ;
  }
}
