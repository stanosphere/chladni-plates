# chladni-plates

When you vibrate a free rigid surface like a plate of metal such that it resonates it will be divided into regions that vibrate in opposite directions.
We call the boundaries of these regions "nodal lines", these are the bits of the plates that don't move up or down.
A _Chladni figure_ is just a picture of these nodal lines for a free (edges unclamped) plate.


A nice way to visualise this is if you have a metal plate, some sand, and like a giant violin bow.
Sprinkle the sand on the plate and use the bow to "play" the plate like you would a violin.
When you find a resonant frequency at which to "play" the plate the sand will naturally move to the nodal lines because those are the bits of the plate that don't move.
I remember seeing this done in first year physics when I was a young student.
Anyway here's a [video](https://www.youtube.com/watch?v=wvJAgrUBF4w&ab_channel=brusspup) of a more professional version where they have a tone generator instead of a giant violin bow.

The idea behind this repo is to draw these because I think they're pretty!
Like I wouldn't be surprised if I've made a mistake trying to rob the solution for the 4th order partial differential equation I'm basing the pictures on!
But as long as they're pretty I don't really care that much <3 <3 <3

## Run

Assuming you have scala and sbt installed:

```commandline
sbt compile 
sbt run
```

And then just follow the text prompts!
If the output looks a bit funny I'd suggest trying a smaller font in your terminal :p

## TODO
- add ability to make a nice PNG (or something) instead of just spitting out to console
- make the PNG lovely and colourful: like I could colour it by just the value of `w`
- contour plot or 3D plot might be fun too

## Story Time

One day Napoleon Bonaparte decided he would like to know about the maths behind these Chladni figures so he offered a prize for the best explanation.
Mathematical genius [Sophie Germain](https://core.ac.uk/download/pdf/36683994.pdf) outlined a correct (if slightly flawed) approach and won. 
Building on this Kirchoff wrote down the partial differential equation that describes a vibrating elastic plate.
Turns out it's just the biharmonic operator (apply the usual harmonic operator, the Laplacian, twice).

<img src="https://render.githubusercontent.com/render/math?math=\Large\nabla^4w(x,y)=f(x,y)"/>

A partial differential equation like this is a bit pointless without boundary conditions.
So we'll need to use boundary conditions that correspond to a square plate with free edges.
Once we've expressed the boundary conditions to figure out the resonant modes we're gonna want to solve the corresponding eigenvalue problem for the above PDE.
Fortunately if you read section 5 of this [paper](https://www.unige.ch/~gander/Preprints/Ritz.pdf) it'll show you how to do all this!
In the final year of his life, 1909, Swiss physicist Walther Ritz developed a technique to solve this BY HAND!!! 
In fact I believe his work on PDEs formed the basis for an incredibly powerful and widely used technique known as the Finite Element Method (FEM).
An example of FEM is https://fenicsproject.org/ which is what I used to model the electrical conductivity of the Earth's mantle a while back.

Anyway I'm digressing.
Basically I just robbed the maple code in the Gander paper that's based on Ritz's solution to Kirchoff's equation which he derived inspired by Germain's work for a competition set by Napoleon to draw pretty pictures.


