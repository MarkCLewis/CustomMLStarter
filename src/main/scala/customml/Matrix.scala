package customml

trait MatrixSuper {
  def rows: Int
  def columns: Int
  def apply(row: Int, col: Int): Double
}

class Matrix(private val values: Seq[Seq[Double]]) extends MatrixSuper {
  def rows: Int = ???
  def columns: Int = ???
  def apply(row: Int, col: Int): Double = ???

  def +(that: Matrix): Matrix = {
    require(rows == that.rows && columns == that.columns)
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
  override def toString: String = values.map(_.mkString("|", " ", "|")).mkString("\n")
}

object Matrix {
  def apply(values: Seq[Seq[Double]]): Matrix = new Matrix(values)
}