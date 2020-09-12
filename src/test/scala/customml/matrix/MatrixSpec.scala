package customml.matrix

import org.scalatest.flatspec._
import org.scalatest.matchers._
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class MatrixSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  
  "Matrix" should "report the proper number of rows and columns" in {
    val m = Matrix(Seq(Seq(1, 2, 3), Seq(4, 5, 6)))
    m.rows should be(2)
    m.columns should be(3)
  }

  it should "add two matrices" in {
    val m = Matrix(Seq(Seq(1, 2, 3), Seq(4, 5, 6))) + Matrix(Seq(Seq(7, 5, 3), Seq(9, 3, 5)))
    m(0, 0) should be(8.0)
    m(0, 1) should be(7.0)
    m(0, 2) should be(6.0)
    m(1, 0) should be(13.0)
    m(1, 1) should be(8.0)
    m(1, 2) should be(11.0)
  }

  it should "subtract two matrices" in {
    val m = Matrix(Seq(Seq(7, 5, 3), Seq(9, 3, 5))) - Matrix(Seq(Seq(1, 2, 3), Seq(4, 5, 6)))
    m(0, 0) should be(6.0)
    m(0, 1) should be(3.0)
    m(0, 2) should be(0.0)
    m(1, 0) should be(5.0)
    m(1, 1) should be(-2.0)
    m(1, 2) should be(-1.0)
  }

  it should "scale a matrix" in {
    val m = Matrix(Seq(Seq(1, 2, 3), Seq(4, 5, 6))) * 2
    m(0, 0) should be(2.0)
    m(0, 1) should be(4.0)
    m(0, 2) should be(6.0)
    m(1, 0) should be(8.0)
    m(1, 1) should be(10.0)
    m(1, 2) should be(12.0)
  }

  it should "multiply two matrices" in {
    val m = Matrix(Seq(Seq(1, 2, 3), Seq(4, 5, 6))) * Matrix(Seq(Seq(7, 5), Seq(9, 3), Seq(1, 2)))
    m(0, 0) should be(7.0 + 18.0 + 3.0)
    m(0, 1) should be(5.0 + 6.0 + 6.0)
    m(1, 0) should be(28.0 + 45.0 + 6.0)
    m(1, 1) should be(20.0 + 15.0 + 12.0)
  }
  
  it should "calculate a 2x2 determinant" in {
    Matrix(Seq(Seq(1, 2), Seq(4, 5))).det should be (-3.0)
  }
  
  it should "calculate a 3x3 determinant" in {
    Matrix(Seq(Seq(1, 2, 3), Seq(4, 5, 2), Seq(4, 2, 1))).det should be (1.0 - (-8.0) + (-36.0))
  }

  it should "do LUP to solve" in {
    val a = Matrix(Seq(
      Seq(1.0, 2.0, 0.0), 
      Seq(3.0, 4.0, 4.0),
      Seq(5.0, 6.0, 3.0)))
    val b = new ColumnMatrix(Array(3.0, 7.0, 8.0))
    val x = Matrix.solveWithLUPDecomposition(a, b)
    x should be (new ColumnMatrix(Seq(-1.4, 2.2, 0.6)))
    a*x should be (b)
  }

  it should "do Gaussian Elimination to solve" in {
    val a = Matrix(Seq(
      Seq(1.0, 2.0, 0.0), 
      Seq(3.0, 4.0, 4.0),
      Seq(5.0, 6.0, 3.0)))
    val b = new ColumnMatrix(Array(3.0, 7.0, 8.0))
    val x = Matrix.solveWithGaussianElimination(a, b)
    x should be (new ColumnMatrix(Seq(-1.4, 2.2, 0.6)))
    a*x should be (b)
  }
}