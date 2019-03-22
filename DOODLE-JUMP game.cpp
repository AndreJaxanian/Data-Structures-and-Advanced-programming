
#include <iostream>
#include "hash.h"
SDL_Surface* screen;
SDL_Surface* back_ground;
SDL_Surface* racket;
SDL_Surface* ball;
SDL_Surface* square;
SDL_Surface* win;
SDL_Surface* lose;
void LoadImages();
int main() 
{

	InitEngine(screen,"Racket",800,800);
	LoadImages();

	while(!ExitRequested)
	{
		Update();
		SDL_BlitSurface(back_ground,screen,0,0);
		SDL_BlitSurface(racket,screen,400,750);
		SDL_BlitSurface(square,screen,200,200);
		SDL_BlitSurface(ball,screen,600,200);
		
		SDL_UpdateScreen();
		SDL_Delay(30);
	}

	return 0;
}
void LoadImages()
{
	back_ground = ImgLoader("assets/bck_X.png",255,255,255);
	racket= ImgLoader("assets/racket.png",255,255,255); 
	ball= ImgLoader("assets/ball.png",255,255,255); 
	square= ImgLoader("assets/square.png",255,255,255); 
	win=ImgLoader("assets/WIN.png",255,255,255); 
	lose=ImgLoader("assets/GameOver.png",255,255,255); 
}
