#include <FPT.h>
#include <string.h>
#include "M2d_matrix_tools.c"

//./a.out mystery.xy treehousefrac.xy better_house.xy perfect_star.xy tree.xy bighouse.xy rocket.xy diag.xy dog.xy man.xy


int numobjects;
int numpoints[10];
double x[10][1500],y[10][1500];
int numpolys[10];
int psize[10][1000];
int con[10][1000][20];
double red[10][1000],grn[10][1000],blu[10][1000];
double center[10][2];
double lo[10][2], hi[10][2];
/*****************
 *draws a polygon*
 ****************/
void drawPolygon(int k){
  int i,j;
  double a[100], b[100];
  for(i=0;i<numpolys[k];i++){
      for(j=0;j<psize[k][i];j++){
	a[j]=x[k][con[k][i][j]];
	b[j]=y[k][con[k][i][j]];
      }
      G_rgb(red[k][i],grn[k][i],blu[k][i]);
      G_fill_polygon(a,b,psize[k][i]);
    }
}
/******************************************************************
 *finds the center of an object using a bounding box and saves the*
 *center in the center array;
 ******************************************************************/
void findCenter(int k){
  double xlo, xhi, ylo, yhi;
  int i;
  double j,h;
  xlo = xhi = x[k][0];
  ylo = yhi = y[k][0];
  for(i=0;i<numpoints[k];i++){
    j=x[k][i];
    h=y[k][i];
    if(j<xlo){xlo=j;}
    if(j>xhi){xhi=j;}
    if(h<ylo){ylo=h;}
    if(h>yhi){yhi=h;}
  }
  lo[k][0]=xlo;
  hi[k][0]=xhi;
  lo[k][1]=ylo;
  hi[k][1]=yhi;
  center[k][0]=xlo+((xhi-xlo)/2);
  center[k][1]=ylo+((yhi-ylo)/2);
}

int main(int argc, char **argv){
  int i,j,k;//k is the object number
  FILE *fp;
  numobjects=argc-1;
  for(k=0;k<numobjects;k++){
    fp=fopen(argv[k+1], "r");
    if(fp==NULL){
      printf("Can't open file %s\n",argv[k+1]);
      exit(0);
    }
    fscanf(fp,"%d",&numpoints[k]);//scans out the number of points
    for(i=0;i<numpoints[k];i++){//saves the points into x,y arrays
      fscanf(fp,"%lf %lf",&x[k][i], &y[k][i]);
    }
    fscanf(fp,"%d",&numpolys[k]);//scans out the number of polygons those points form
    for(i=0;i<numpolys[k];i++){//fills the psize and conuctivity arrays
      fscanf(fp,"%d",&psize[k][i]);
      for(j=0;j<psize[k][i];j++){
	fscanf(fp,"%d",&con[k][i][j]);
      }
    }
    for(i=0;i<numpolys[k];i++){//fills the color arrays
      fscanf(fp, "%lf %lf %lf", &red[k][i], &grn[k][i], &blu[k][i]);
    }


  }
  /*************************
   *Drawing Portion of Main*
   *************************/
  G_init_graphics(600,600);
  double a[100], b[100],extra,trans[3][3],rot[3][3],scl[3][3],matrix[3][3],scalar[2];
  int v,key;
  for(v=0;v<numobjects;v++){//centers and scales objects before the user first looks at them
    findCenter(v);
    M2d_make_translation(trans,(0-center[v][0]),(0-center[v][1]));//translate(v,0,0);
    M2d_mat_mult_pt(center[v],trans,center[v]);
    scalar[0]=(450/(hi[v][0]-lo[v][0]));
    scalar[1]=(450/(hi[v][1]-lo[v][1]));
    if(scalar[0]<=scalar[1]){//scale(v);
      M2d_make_scaling(scl,scalar[0],scalar[0]);
    }else{
      M2d_make_scaling(scl,scalar[1],scalar[1]);
    }
    M2d_mat_mult(matrix,scl,trans);
    M2d_make_translation(trans,300,300);//translate(v,300,300);
    M2d_mat_mult_pt(center[v],trans,center[v]);
    M2d_mat_mult(matrix,trans,matrix);
    M2d_mat_mult_points(x[v],y[v],matrix,x[v],y[v],numpoints[v]);
  }
  k=0;
  do{
    G_rgb(0,0,0);
    G_clear();
    drawPolygon(k);
    M2d_make_translation(trans,-300,-300);
    M2d_make_rotation_degrees(rot,1);
    M2d_mat_mult(matrix,rot,trans);
    M2d_make_translation(trans,300,300);
    M2d_mat_mult(matrix,trans,matrix);
    M2d_mat_mult_points(x[k],y[k],matrix,x[k],y[k],numpoints[k]);
    key = G_wait_key() ;
    if(key=='q'){break;}
    else if ('0' <= key && key <= '9') {
      v = key -48 ;//subtract 48 b/c of ASCII
      if (v < numobjects) { k = v ; }//check if the number is one of the objects
    }
  } while (0==0) ;
  G_close();
}
