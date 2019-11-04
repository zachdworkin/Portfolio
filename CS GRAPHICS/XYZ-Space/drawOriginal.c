#include <FPT.h>
#include "M2d_matrix_tools.c"

int main(){
  int i,j,k,numpoints=5;;
  double x[5]={2,6,8,6,2};
  double y[5]={0,0,0,-2,-2};
  double a[5]={320,360,380,360,320};
  double b[5]={300,300,300,280,280};
  double trans[3][3], scl[3][3], rot[3][3], matrix[3][3];
  M2d_make_identity(matrix);
  M2d_make_identity(rot);
  M2d_make_identity(scl);
  M2d_make_identity(trans);
  G_init_graphics(600,600);
  G_rgb(0,0,0);
  G_fill_rectangle(0,299,600,2);//x-axis
  G_fill_rectangle(299,0,2,600);//y-axis
  for(i=0;i<600;i+=10){
    G_line(i,0,i,600);//x grids
    G_line(0,i,600,i);//y grids
  }
  G_fill_polygon(a,b,numpoints);
  M2d_make_translation(trans,0,2);
  M2d_make_scaling(scl,.5,.5);
  M2d_make_rotation_cs(rot,-1,0);
  M2d_mat_mult(matrix,scl,trans);
  M2d_mat_mult(matrix,rot,matrix);
  M2d_make_translation(trans,-2,-1);
  M2d_mat_mult(matrix,trans,matrix);
  M2d_make_scaling(scl,10,10);
  M2d_mat_mult(matrix,scl,matrix);
  M2d_make_translation(trans,300,300);
  M2d_mat_mult(matrix,trans,matrix);
  M2d_mat_mult_points(x,y,matrix,x,y,numpoints);
  /*
  for(i=0;i<5;i++){
    printf("%lf,%lf\n",x[i],y[i]);
  }
  */
  G_rgb(1,0,0);
  G_fill_polygon(x,y,numpoints);
  G_wait_key();
  G_display_image();
  G_close();
}
