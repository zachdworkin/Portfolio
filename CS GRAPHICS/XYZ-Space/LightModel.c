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

double findDist(double V[3]){
  return sqrt((V[0]*V[0])+(V[1]*V[1])+(V[2]*V[2]));
}

void addVectors(double A[3],double B[3], double C[3]){
  double P[3];
  P[0]=C[0]+B[0];
  P[1]=C[1]+B[1];
  P[2]=C[2]+B[2];
  
  A[0]=P[0];
  A[1]=P[1];
  A[2]=P[2];
}

void negateVector(double A[3], double B[3]){
  A[0]=-B[0];
  A[1]=-B[1];
  A[2]=-B[2];
}

void scaleVector(double A[3],double B[3],double k){
  A[0]=k*B[0];
  A[1]=k*B[1];
  A[2]=k*B[2];
}

void makeReflectionVector(double Ru[3], double Lu[3], double Nu[3],double alpha){
  double nLu[3],Su[3];
  negateVector(nLu,Lu);
  scaleVector(Su,Nu,2*alpha);
  addVectors(Ru,nLu,Su);
}
