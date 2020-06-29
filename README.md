# MathApp

## Introduction
MathApp is currently a hobbyist project I'm working on, trying to develop some Java code for interacting with some
of the mathematics I've worked with through recent years.  At this point it is still very very much in its infancy,
and my current goal is developing a working structure for complex numbers and computations on them.  The higher
aspirations of having full Java generic support or type polymorphism is going to be the second phase after primitive
complex mathematical functions, because I am trying to avoid getting stuck on the fine details this early on.

## Current Focus / Planned Goals
Right now I am wanting to build a (hopefully) solid foundation for future mathematics, by writing a sturdy
implementation of Complex numbers.  I want to have generic methods that handle everything possible
at their specific level, while leaving certain methods for lesser classes for concrete implementations.
Currently, the goal is to get some basic math working in both polar and Cartesian represented numbers
with underlying Double fields, which eventually I hope to revert to generic polar and Cartesian
methods, and bring in a String / Stream / List field type.  The math may be entirely unnecessary on an
Android device, but I'm hoping for one project to show type-independent algorithmic implementation of
math on larger numbers than Double or Long.

The first major target is to generate a list of the roots of a complex number, list those in a contemporary
Android UI View, and plot them on a unit circle for visualization.  Next will be implementing some variety
of a mathematical calculation and graphing module.  Then I hope to branch out and also work on differential
equations models with methods comparable to what I learned in Numerical Analysis (Adams-Bashforth and Adams-Moulton
multi-step methods for numerical approximation).  Take these goals with a grain of salt, as the saying goes, because
this will be fully dependent on the availability of free time to work on this.

## License
This project as currently available is published AS IS and with NO WARRANTY of fitness for any purpose.
Reuse of code is freely allowed for non-profit-generating pursuits with attribution, and to conserve future
business opportunities I ask that any profit-generating use be done ONLY WITH WRITTEN PERMISSION from the
project owner.  Some modules of this project may be recalled from publication, in that event any PUBLISHED prior
version will continue to be available under these uses where copies continue to exist, but future maintenance
will not be guaranteed.

Some future plans for project content include a representation of published work, which will be utilized under
permission of the authors of the original publication.  As those features are developed they will be specifically
labeled with attribution and the overall project license will be adapted in consideration of their requests.
(This is the motivation for the written permission for some uses.)

I make no guarantee of maintenance of any code for future API developments, bug fixes, performance issues, readability,
or any other inspiration for maintenance.

## Modifications of License Terms
If you seek any "upgrades" in allowed permissions or offer of maintenance, I am open to offers.  I will certainly
consider these if I can be financially stable doing so.
