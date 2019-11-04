#include <stdio.h>
#include <FPT.h>

int main() { 
    G_init_graphics(800,800);
    G_rgb(1,1,1);
    G_clear();
    
    double u,r,x,y;
    for (u = 0; u < 2*M_PI; u+=0.001) {
        for (r = 0; r <= 300; r+=1) {
            x = r*cos(u)+400;
            y = r*sin(u)+400;
            if (fmod(((int)(r/10)),2) == fmod((int)(u/(M_PI/16)),2))
                G_rgb(1,0,0);
            else G_rgb(0,0,0);
                G_point(x,y);
        }
    }
    G_wait_key();
}
