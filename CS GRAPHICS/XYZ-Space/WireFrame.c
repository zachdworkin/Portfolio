#include <FPT.h>
#include <string.h>
#include "M3d_matrix_tools.c"
int numobjects;
int numpoints[10];
double x[10][15000],y[10][15000],z[10][15000];
int numpolys[10];
int psize[10][14000];
int con[10][14000][8];
double xbar[10][15000],ybar[10][15000];
double H,ha;

// ./a.out box123.xyz sphere.xyz deathstr.xyz stegsaur.xyz torus2.xyz jvase.xyz zylonjag.xyz ellipsoid.xyz helicop.xyz cylinder.xyz

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
  x[k][numpoints[k]+1]=sumx/numpoints[k];
  y[k][numpoints[k]+1]=sumy/numpoints[k];
  z[k][numpoints[k]+1]=sumz/numpoints[k];
}

void moveToScreen(int k){
  int i,j;
  H=tan(ha*(M_PI/180));
  for(i=0;i<numpoints[k];i++){
    xbar[k][i]=((x[k][i]/z[k][i])*(400/H))+400;
    ybar[k][i]=((y[k][i]/z[k][i])*(400/H))+400;
  }
}

void draw(int k){
  int i,j;
  double a[15000], b[15000];
  moveToScreen(k);
  for(i=0;i<numpolys[k];i++){
      for(j=0;j<psize[k][i];j++){
	a[j]=xbar[k][con[k][i][j]];
	b[j]=ybar[k][con[k][i][j]];
      }
      G_rgb(0,1,0);
      G_polygon(a,b,psize[k][i]);
    }
}

int main(int argc, char **argv){
  int i,j,k;//k is the object number
  FILE *fp;
  int v,key,n;
  int sign = 1 ;
  int action = 't' ;
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
  }
  /*************************
   *Drawing Portion of Main*
   *************************/
  double transa[4][4],transb[4][4],matrixx[4][4],matrixy[4][4],matrixz[4][4],rotx[4][4],roty[4][4],rotz[4][4],transx[4][4],transy[4][4],transz[4][4],trans[4][4],matrix[4][4],rot[4][4];
  double cs,sn;
  int onum,q;
  printf("Please enter your viewing angle\n");
  scanf("%lf",&ha);
  G_init_graphics(800,800);
  G_rgb(0,0,0);
  //ha=40;
  cs=cos(.01);
  sn=sin(.01);
  //old dowhile goes here
   //  Half_angle_degrees ... I think you have 40 degrees as the default
  onum = 0 ; // onum marks the current object
  n = numpoints[onum] ; // other else ifs need this

  while (1) {

    q = G_wait_key() ;
    
    if (q == 'q') {
      exit(0) ;

    } else if (q == 'c') {//zoom in or out
      sign = -sign ;
      cs=cos(sign*.01);
      sn=sin(sign*.01);

    } else if (q == 't') {//translate
      action = q ;

    } else if (q == 'r') {//rotate
      action = q ;

    } else if (('0' <= q) && (q <= '9')) {
      k = q - '0' ;  
      if (k < numobjects) { onum = k ; }

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
      M3d_make_translation(transb,x[onum][numpoints[onum]],y[onum][numpoints[onum]+1],z[onum][numpoints[onum]]);
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
      // the numpoints[onum]+1 is because we have stored the center
      // of the object at the arrays' end
    G_rgb(0,0,0) ; 
    G_clear() ;
    G_rgb(0,0,1) ;
    //    draw_all_objects() ;
    draw(onum) ;
  }
}
