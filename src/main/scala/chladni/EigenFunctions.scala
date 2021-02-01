package chladni

import chladni.utils.MathsHelpers.{isEven, square}

import scala.math.{cos, cosh, sin, sinh, sqrt, Pi}

object EigenFunctions {
  // an eigenfunction of the biharmonic operator for a square plate with open edges
  // I have chosen this form since it yields more interesting results than just using `u(m)(x) * u(n)(y)` for example
  // Indeed any linear combination `u(m)(x) * u(n)(y)` must also be an eigenfunction
  // It just so happens that using this as a basis function gives you nice patterns!
  def w(m: Int, n: Int)(x: Double, y: Double): Double = u(m)(x) * u(n)(y) + u(n)(x) * u(m)(y)

  /* An eigenfunction of the free one dimensional bar
   * i.e. u_m'''' = km^4 * u_m
   * with B.C.s u_m''' = u_m'' = 0 at x = {-1, 1}
   */
  def u(m: Int)(x: Double): Double = {
    if (m == 0) 1 / sqrt(2)
    else if (m == 1) sqrt(3 / 2) * x
    else if (isEven(m))
      (cosh(k(m)) * cos(k(m) * x) + cos(k(m)) * cosh(k(m) * x)) / sqrt(square(cosh(k(m))) + square(cos(k(m))))
    else
      (sinh(k(m)) * sin(k(m) * x) + sin(k(m)) * sinh(k(m) * x)) / sqrt(square(sinh(k(m))) - square(sin(k(m))))
  }

  /* An eigenvalue for the free one dimensional bar
   * the official maple code to calculate k_m:
   * fsolve(tan(x) + tanh(x) = 0, x = m * Pi / 2 - Pi / 4)
   * fsolve(tan(x) - tanh(x) = 0, x = (m-1/2) * Pi / 2)
   * But since for x > 3, tanh(x) > 0.99 I'm just gonna use an approximation. Sue me
   */
  private def k(m: Int): Double =
    if (isEven(m)) m * Pi / 2 - Pi / 4
    else (m - 1 / 2) * Pi / 2
}
