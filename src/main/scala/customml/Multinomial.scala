package customml

import scala.util.Random

class Multinomial(binomials: Seq[Binomial]) extends DistributionK {
  def next(): Seq[Double] = ???
  def mean(): Seq[Double] = ???
  def variance(): Seq[Double] = ???
}

object Multinomial {
  def apply(n: Int, data: Seq[Seq[Double]], rand: Random = Random): Multinomial = ???
}