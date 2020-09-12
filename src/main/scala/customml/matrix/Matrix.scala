package customml.matrix

trait Matrix extends ((Int, Int) => Double) {
  def rows: Int
  def columns: Int
  def apply(row: Int, col: Int): Double
  def +(that: Matrix): Matrix = {
    ???
  }
  def -(that: Matrix): Matrix = {
    ???
  }
  def *(that: Matrix): Matrix = {
    ???
  }
  def *(x: Double): Matrix = {
    ???
  }
  def det: Double = {
    ???
  }
  def transpose: Matrix = new TransposeMatrix(this)

  override def toString: String = (0 until rows).map(row => (0 until columns).map(c => apply(row,c)).mkString("|", " ", "|")).mkString("\n")
  override def equals(that: Any) = that match {
    case m: Matrix => rows == m.rows && columns == m.columns && (0 until rows).forall(r => (0 until columns).forall(c => (apply(r, c) - m(r, c)).abs < 1e-8))
    case _ => false
  }
}

class FullMatrix(val values: Seq[Seq[Double]]) extends Matrix {
  def rows = values.length
  def columns = values(0).length
  def apply(row: Int, col: Int): Double = values(row)(col)
}

class IdentityMatrix(n: Int) extends Matrix {
  def rows: Int = n
  def columns: Int = n
  def apply(row: Int, col: Int): Double = if(row == col) 1.0 else 0.0
}

class ColumnMatrix(column: Seq[Double]) extends Matrix {
  def rows: Int = column.length
  def columns: Int = 1
  def apply(row: Int, col: Int): Double = column(row)
}

class TransposeMatrix(matrix: Matrix) extends Matrix {
  def rows: Int = matrix.columns
  def columns: Int = matrix.rows
  def apply(row: Int, col: Int): Double = matrix(col, row)
  override def transpose: Matrix = matrix
}

object Matrix {
  def apply(values: Seq[Seq[Double]]): Matrix = new FullMatrix(values)
  def identity(n: Int) = new IdentityMatrix(n)

  /**
   * I used this for my determinent, you don't have to. If you don't use it, you can delete it.
   */
  class Minor(parent: Matrix, drow: Int, dcol: Int) extends Matrix {
    def rows: Int = ???
    def columns: Int = ???
    override def apply(row: Int, col: Int): Double = {
      ???
    }
  }

  /**
   * Solves the equation Ax = b. Pass in A and b, returns x. This approach isn't very numerically stable, but it is
   * easier to code than the better alternatives.
   * @param a is a NxN matrix.
   * @param b is a Nx1 matrix.
   * @return the Nx1 marix that solves Ax=y. Can include NaNs if the matrix is singular.
   */
  def solveWithGaussianElimination(a: Matrix, b: Matrix): Matrix = {
    val n = a.rows
    // This algorithm mutates. This makes a handy copy of a with an extra column for b
    val ret = Array.tabulate(a.rows, a.columns + 1)((i, j) => if (j < a.columns) a(i, j) else b(i, 0))
    // Your code here
    new ColumnMatrix(???)
  }

  def solveWithLUPDecomposition(a: Matrix, b: Matrix): Matrix = {
    val (lu, p) = lupDecomposition(a)
    lupSolve(lu, p, b)
  }

  // Based on pseudocode from CLRS 2nd edition.
  private def lupDecomposition(a: Matrix): (Matrix, Array[Int]) = {
    val n = a.rows
    val pi = Array.tabulate(n)(i => i)
    val ret = Array.tabulate(a.rows, a.columns)((i, j) => a(i,j))
    for (k <- 0 until n) {
      var p = 0.0
      var kp = 0
      for (i <- k until n) {
        if (ret(i)(k).abs > p) {
          p = ret(i)(k).abs
          kp = i
        }
      }
      if (p == 0.0) throw new IllegalArgumentException("LUP-Decommopsition with a singular matrix: " + a)
      val tmprow = ret(k)
      ret(k) = ret(kp)
      ret(kp) = tmprow
      val tmppi = pi(kp)
      pi(kp) = pi(k)
      pi(k) = tmppi
      for (i <- k+1 until n) {
        ret(i)(k) /= ret(k)(k)
        for (j <- k+1 until n) {
          ret(i)(j) -= ret(i)(k) * ret(k)(j)
        }
      }
    }
    (Matrix(ret.map(_.toSeq)), pi)
  }

  // Based on pseudocode from CLRS 2nd edition.
  private def lupSolve(lu: Matrix, p: Array[Int], b: Matrix): Matrix = {
    val n = lu.rows
    val y = Array.fill(n)(0.0)
    val x = Array.fill(n)(0.0)
    for (i <- 0 until n) {
      y(i) = b(p(i), 0) - (0 until i).map(j => lu(i, j) * y(j)).sum
    }
    for (i <- n-1 to 0 by -1) {
      x(i) = (y(i) - (i+1 until n).map(j => lu(i, j) * x(j)).sum) / lu(i, i)
    }
    new ColumnMatrix(x)
  }
}
