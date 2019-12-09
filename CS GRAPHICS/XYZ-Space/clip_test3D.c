#include <FPT.h>

int  Clip_Polygon_Against_Plane(
			       double a, double b, double c, double d, 
			       double *polyx, double *polyy, double *polyz, int size,
			       double *resx, double *resy, double *resz)

// Clip polygon against the plane ax + by + cz + d = 0,
// where ax + by + cz +d < 0 is considered IN.
// Incoming poly defined in arrays  polyx, polyy  with numverts = size.
// Clipped result values are stored in arrays  resx, resy,
// The numverts of the clipped result is returned as value of the function.

{
  int num,i,j ;
  double x1,y1,z1,x2,y2,z2,x21,y21,z21,den,t,xintsct,yintsct,zintsct ;
  double p1,p2 ;

  num = 0 ;
  for (i = 0 ; i < size ; i++) {
     j = (i + 1) % size ;

     // load up segment to be clipped
     x1 = polyx[i] ; y1 = polyy[i] ; z1 = polyz[i] ;
     x2 = polyx[j] ; y2 = polyy[j] ; z2 = polyz[j] ;

     // clip line segment (x1,y1)-(x2,y2) against line
     p1 = (a*x1 + b*y1 + c*z1 + d) ;
     p2 = (a*x2 + b*y2 + c*z2 + d) ;

     if ((p1 >= 0) && (p2 >= 0)) {
        // out to out, do nothing
     } else if ((p1 < 0) && (p2 < 0)) {
        // good -> good
       resx[num] = x2 ; resy[num] = y2 ; resz[num] = z2 ; num++ ;
     } else {
        // one is in, the other out, so find the intersection

        x21 = x2 - x1 ; y21 = y2 - y1 ; z21 = z2-z1 ;
        den = a*x21 + b*y21 + c*z21;
        if (den == 0) continue ; // do nothing-should never happen
        t = -(a*x1 + b*y1 + c*z1 + d)/den ;
        xintsct = x1 + t*x21 ;
        yintsct = y1 + t*y21 ;
	zintsct = z1 + t*z21 ;

        if (p1 < 0) { 
          // in to out
          resx[num] = xintsct ; resy[num] = yintsct ; resz[num] = zintsct ; num++ ;
        } else  {
          // out to in
          resx[num] = xintsct ; resy[num] = yintsct ; resz[num] = zintsct ; num++ ;
          resx[num] = x2      ; resy[num] = y2      ; resz[num] = z2      ; num++ ;
        }

     }


  } // end for i

  return num ;  // return size of the result poly
}






int  Clip_Polygon_Against_Convex_Window (
      double *px,  double *py, double *pz, int number_points,
      double hither, double yon, double halfA)

{
  double newx[10000],newy[10000], newz[10000],  a[6],b[6],c[6],d[6],  center ;
  int i,k,m ;


  center = (hither+yon) / 2;

  a[0] = 0;                      a[1] = 0;                         a[2] = 1;                      a[3] = 1;                     a[4] = 0;       a[5] = 0;
  b[0] = 1;                      b[1] = 1;                         b[2] = 0;                      b[3] = 0;                     b[4] = 0;       b[5] = 0;
  c[0] = tan(halfA)*yon;    c[1] = -tan(halfA)*yon;      c[2] = tan(halfA)*yon;    c[3] = -tan(halfA)*yon;  c[4] = 1;       c[5] = 1;
  d[0] = 0;                      d[1] = 0;                         d[2] = 0;                      d[3] = 0;                     d[4] = -hither; d[5] = -yon; 


  //clip the 4 sides first
  for(int k = 0; k < 4; k++){

    if(c[k]*center + d[k] > 0){
      a[k] = -a[k]; b[k] = -b[k]; c[k] = -c[k]; d[k] = -d[k];
    }

    number_points = Clip_Polygon_Against_Plane(a[k], b[k], c[k], d[k], px, py, pz, number_points, newx, newy, newz);

    for(int i = 0; i < number_points; i++){
      px[i] = newx[i]; py[i] = newy[i]; pz[i] = newz[i];
    }
  }

  if(c[5]*center + d[5] > 0){
    a[5] = -a[5]; b[5] = -b[5]; c[5] = -c[5]; d[5] = -d[5];
  }
  number_points = Clip_Polygon_Against_Plane(a[5], b[5], c[5], d[5], px, py, pz, number_points, newx, newy, newz);

  for(int i = 0; i < number_points; i++){
    px[i] = newx[i]; py[i] = newy[i]; pz[i] = newz[i];
  }

  if(c[4]*center + d[4] > 0){
    a[4] = -a[4]; b[4] = -b[4]; c[4] = -c[4]; d[4] = -d[4];
  }
  number_points = Clip_Polygon_Against_Plane(a[4], b[4], c[4], d[4], px, py, pz, number_points, newx, newy, newz);

  for(int i = 0; i < number_points; i++){
    px[i] = newx[i]; py[i] = newy[i]; pz[i] = newz[i];
  }

  return number_points;
}
