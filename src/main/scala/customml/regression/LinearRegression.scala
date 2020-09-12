package customml.regression

import customml.Matrix
import customml.ColumnMatrix

/**
 * This represents a linear regression for some specified functions. You apply this to the data
 * that you have and it gives you back a matrix with the coefficients.
 */
class LinearRegression(functions: Seq[Seq[Double] => Double]) {
  /**
   * This method actually does the linear regression. The implicit solver will either be the LUP
   * solver or the Gaussian Elimination solver.
   */
  def apply(x: Seq[Seq[Double]], r: Seq[Double])(implicit solver: (Matrix, Matrix) => Matrix): Matrix = {
    ???
  }

  /**
   * A simple wrapper for the 1-D special case.
   */
  def oneD(x: Seq[Double], r: Seq[Double])(implicit solver: (Matrix, Matrix) => Matrix): Matrix = {
    apply(x.map(c => Seq(c)), r)
  }
}

/**
 * This object contains some helper regressions that you might use commonly. I include them both to make tests easier to write
 * and to allow you to see how the LinearRegression class can be instantated.
 */
object LinearRegression {
  lazy val oneDLinear = new LinearRegression(Seq(_ => 1.0, _(0)))

  def oneDPolynomial(degree: Int) = new LinearRegression((0 to degree).map(p => (x: Seq[Double]) => math.pow(x(0), p)))

  def linear(d: Int) = new LinearRegression(((_: Seq[Double]) => 1.0) +: (0 until d).map(i => (x: Seq[Double]) => x(i)))
}