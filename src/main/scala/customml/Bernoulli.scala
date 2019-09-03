package customml

import scala.util.Random

/**
 * Bernoulli distribution. Add parameters to the class as needed.
 */
class Bernoulli(p: Double, rand: Random = Random) extends Distribution {
  def next(): Double = ???
  def mean(): Double = ???
  def variance(): Double = ???
  
  def p(x: Double): Double = ???
}

object Bernoulli {
  def apply(data: Seq[Double], rand: Random = Random): Bernoulli = ???
}