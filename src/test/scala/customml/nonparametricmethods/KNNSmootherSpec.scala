package customml.nonparametricmethods

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import customml.UtilityFunctions._

class KNNSmootherSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds

  "k-nn smoother" should "do simple regression" in {
    val xr = Seq(
      (Seq(1.0, 1.0), 1.0),
      (Seq(1.1, 1.0), 1.1),
      (Seq(1.0, 1.1), 1.2),
      (Seq(-1.0, 1.0), 0.0),
      (Seq(-1.1, 1.0), 0.1),
      (Seq(-1.0, 1.1), 0.2),
      (Seq(1.0, -1.0), -1.0),
      (Seq(1.1, -1.0), 0.0),
      (Seq(1.0, -1.1), 1.0)
    )
    val smooth = new KNNSmoother(3, xr, _ => 1.0, euclidean)
    smooth.fit(Seq(1.05, 1.05)) should be (1.1 +- 1e-8)
    smooth.fit(Seq(-1.05, 1.05)) should be (0.1 +- 1e-8)
    smooth.fit(Seq(1.05, -1.05)) should be (0.0 +- 1e-8)
  }

  it should "do smoothed regression" in {
    val xr = Seq(
      (Seq(-5.0, 0.0), 1.0),
      (Seq(-4.0, 0.0), 4.0),
      (Seq(-3.0, 0.0), 1.0),
      (Seq(-1.0, 0.0), 1.0),
      (Seq(0.0, 0.0), -3.0),
      (Seq(1.0, 0.0), 1.0),
      (Seq(5.0, 0.0), 4.0),
      (Seq(6.0, 0.0), 2.0),
      (Seq(7.0, 0.0), 0.0),
    )
    val smooth = new KNNSmoother(3, xr, d => 1/(1+d), euclidean)
    smooth.fit(Seq(-4.0, 0.0)) should be (2.5 +- 1e-8)
    smooth.fit(Seq(0.0, 0.0)) should be (-1.0 +- 1e-8)
    smooth.fit(Seq(6.0, 0.0)) should be (2.0 +- 1e-8)
  }

  it should "handle a bigger distribution" in {
    val xr = Seq.fill(100)(Seq(math.random, math.random) -> math.random)
    val smooth = new KNNSmoother(1, xr, d => 1/math.exp(-d*d), euclidean)
    for (_ <- 1 to 10) {
      val xs = Seq(math.random(), math.random())
      smooth.fit(xs) should be (xr.minBy(p => euclidean(p._1, xs))._2)
    }
  }
}