package customml.nonparametricmethods

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.stats.Gaussian
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
    val smooth = new KNNSmoother(3, xr, d => if (d <= 1.0) 1.0 else 0.0, euclidean)
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
    val smooth = new KNNSmoother(3, xr, d => if (d <= 1.0) 1/(1+d) else 0.0, euclidean)
    smooth.fit(Seq(-4.0, 0.0)) should be (2.5 +- 1e-8)
    smooth.fit(Seq(0.0, 0.0)) should be (-1.0 +- 1e-8)
    smooth.fit(Seq(6.0, 0.0)) should be (2.0 +- 1e-8)
  }

  it should "handle a bigger distribution (could fail very rarely)" in {
    val xr = Seq.fill(10000){ val x = math.random; val y = math.random; Seq(x, y) -> math.sin(x)*math.cos(y)}
    val smooth = new KNNSmoother(10, xr, d => math.exp(-d*d), euclidean)
    for (_ <- 1 to 10) {
      val xs = Seq(math.random()*0.8+0.1, math.random()*0.8+0.1)
      smooth.fit(xs) should be (math.sin(xs(0))*math.cos(xs(1)) +- 0.01)
    }
  }
}