package customml.regression

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import customml.Matrix
import customml.ColumnMatrix

class LinearRegressionSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds

  "Linear Regression" should "fit a line with LUP" in {
    val x = Seq.tabulate(10)(i => i.toDouble)
    val r = x.map(_ + 1)
    implicit val lup = Matrix.solveWithLUPDecomposition _
    val fit = LinearRegression.oneDLinear.oneD(x, r)
    fit should be (new ColumnMatrix(Seq(1.0, 1.0)))
  }

  it should "fit a line with GE" in {
    val x = Seq.tabulate(10)(i => i.toDouble)
    val r = x.map(_ + 1)
    implicit val lup = Matrix.solveWithGaussianElimination _
    val fit = LinearRegression.oneDLinear.oneD(x, r)
    fit should be (new ColumnMatrix(Seq(1.0, 1.0)))
  }

  it should "fit a quadratic with LUP" in {
    val x = Seq.tabulate(11)(i => (i-5).toDouble)
    val r = x.map(c => c*c - 2)
    implicit val lup = Matrix.solveWithLUPDecomposition _
    val fit = LinearRegression.oneDPolynomial(2).oneD(x, r)
    fit should be (new ColumnMatrix(Seq(-2.0, 0.0, 1.0)))
  }

  it should "fit a quadratic with GE" in {
    val x = Seq.tabulate(11)(i => (i-5).toDouble)
    val r = x.map(c => c*c - 2)
    implicit val lup = Matrix.solveWithGaussianElimination _
    val fit = LinearRegression.oneDPolynomial(2).oneD(x, r)
    fit should be (new ColumnMatrix(Seq(-2.0, 0.0, 1.0)))
  }

  it should "fit 3D linear with LUP" in {
    val x = Seq.tabulate(10, 3)((i, j) => math.random)
    val r = x.map(c => 3*c(0) + 5*c(1) - 4*c(2) - 2)
    implicit val lup = Matrix.solveWithLUPDecomposition _
    val fit = LinearRegression.linear(3)(x, r)
    fit should be (new ColumnMatrix(Seq(-2.0, 3.0, 5.0, -4.0)))
  }

  it should "fit 3D linear with GE" in {
    val x = Seq.tabulate(10, 3)((i, j) => math.random)
    val r = x.map(c => 3*c(0) + 5*c(1) - 4*c(2) - 2)
    implicit val lup = Matrix.solveWithGaussianElimination _
    val fit = LinearRegression.linear(3)(x, r)
    fit should be (new ColumnMatrix(Seq(-2.0, 3.0, 5.0, -4.0)))
  }

  it should "fit sinusoids with LUP" in {
    val x = Seq.tabulate(10, 2)((i, j) => math.random)
    val r = x.map(c => 3*math.sin(c(0)) + 5*math.sin(c(1)) - 4*math.cos(c(0)) + 2*math.cos(c(1)) + 2)
    implicit val lup = Matrix.solveWithLUPDecomposition _
    val fit = new LinearRegression(Seq(
      _ => 1.0,
      x => math.sin(x(0)),
      x => math.sin(x(1)),
      x => math.cos(x(0)),
      x => math.cos(x(1))
    ))(x, r)
    fit should be (new ColumnMatrix(Seq(2.0, 3.0, 5.0, -4.0, 2.0)))
  }

  it should "fit sinusoids with GE" in {
    val x = Seq.tabulate(10, 2)((i, j) => math.random)
    val r = x.map(c => 3*math.sin(c(0)) + 5*math.sin(c(1)) - 4*math.cos(c(0)) + 2*math.cos(c(1)) + 2)
    implicit val lup = Matrix.solveWithGaussianElimination _
    val fit = new LinearRegression(Seq(
      _ => 1.0,
      x => math.sin(x(0)),
      x => math.sin(x(1)),
      x => math.cos(x(0)),
      x => math.cos(x(1))
    ))(x, r)
    fit should be (new ColumnMatrix(Seq(2.0, 3.0, 5.0, -4.0, 2.0)))
  }
}
