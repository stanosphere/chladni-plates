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

I've added 100 images of some of the lower eigenmodes under `/pictures/low-eigenmodes`
## Run

At the moment this is just a really simple command line thing that will first spit out some beautiful ascii art.

It will then ask if you want to save a PNG representation of the plot which will be much higher res.

Assuming you have scala and sbt installed:

```commandline
sbt compile 
sbt run
```

And then just follow the text prompts!
If the ascii output looks a bit funny I'd suggest trying a smaller font in your terminal :p

### Sample Output

Here's what a console output looks like

<img src="https://github.com/stanosphere/chladni-plates/blob/main/pictures/screenshot-of-console.png" width="400">

And here's a what a png output looks like (it has different parameters to the console one above)

<img src="https://github.com/stanosphere/chladni-plates/blob/main/pictures/5-7-hiish-res.png" width="400">

And here's a random one where I've used a seed of the first few digits of pi: 314159

<img src="https://github.com/stanosphere/chladni-plates/blob/main/pictures/314159.png" width="400">

### Current Running modes

#### Eigenmode
This will draw an eigenfunction of the 2D biharmonic operator.
I've chosen eigenfunctions of the form `u_m(x) * u_n(y) + u_n(x) * u_m(y) ` since these give rather pretty symmetrical results.

#### Random
Since the biharmonic operator is a linear operator any linear combination of eigenfunctions is also an eigenfunction.
So I thought I'd just use [scalacheck](https://www.scalacheck.org/index.html)'s rather extensive generator library to do this.
All I do is ask for a seed and then have scala check generate a superposition of 10 eigenfunctions with each term of the form `c * u_m(x) * u_n(y)`.
- c is between -1 and 1 (inclusive)
- m is between 1 and 10 (inclusive)
- n is between 1 and 10 (inclusive)

## TODO
- add ability to make a nice PNG (or something) instead of just spitting out to console
- make the PNG lovely and colourful: like I could colour it by just the value of `w`
- contour plot or 3D plot might be fun too
- I've made a particular choice of basis function that yields interesting patterns.
I should probably define the sort of accompanying basis function `w-` and introduce a way of taking linear combinations
Because briefly experimenting you can get other nice patterns
- I've been playing around with parameters and i think it would be fun to add a random image generator for this sort of thing

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


