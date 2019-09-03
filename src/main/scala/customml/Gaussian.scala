package customml

import scala.util.Random

class Gaussian(mu: Double, v: Double, rand: Random = Random) extends Distribution {
  def next(): Double = ???
  def mean(): Double = ???
  def variance(): Double = ???
  def p(x: Double): Double = ???
}

object Gaussian {
  def apply(data: Seq[Double]): Gaussian = ???
}