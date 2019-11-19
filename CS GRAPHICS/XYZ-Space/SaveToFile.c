#include <FPT.h>

void saveToFile(int numpoints, double x[150000], double y[150000], double z[150000], int numpolys, int psize[15000], int con[15000][10]){
  int i,j;
  FILE *fout ;
  fout = fopen("my.xyz" , "w") ;
  fprintf(fout, "%d\n" , numpoints);
  for(i=0;i<numpoints;i++){
    fprintf(fout, "%lf %lf %lf\n",x[i],y[i],z[i]);
  }
  fprintf(fout,"\n");
  fprintf(fout, "%d\n" , numpolys);
  for(i=0;i<numpolys;i++){
    fprintf(fout, "%d ",psize[i]);
    for(j=0;j<psize[i];j++){
      fprintf(fout, "%d ",con[i][j]);
    }
    fprintf(fout,"\n");
  }
}
