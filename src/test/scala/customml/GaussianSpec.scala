package customml

import org.scalatest._
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class GaussianSpec extends FlatSpec with Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  
  "Gaussian" should "have mean of 0 for input Seq(-1, 1)" in {
    Gaussian(Seq(-1, 1)).mean() should be (0.0 +- 1e-8)
  }

  it should "have variance of 1 for input Seq(-1, 1)" in {
    Gaussian(Seq(-1, 1)).variance() should be (1.0 +- 1e-8)
  }
  
  it should "find the proper mean and variance for input data (may fail occasionally)" in {
    val xs = (1 to 10000).map(_ => util.Random.nextGaussian()*0.5+3)
    val g = Gaussian(xs)
    (g.mean()*10).round/10.0 should be (3.0 +- 1e-8)
    (g.variance()*100).round/100.0 should be (0.25 +- 1e-8)
  }
  
  it should "generate a proper distribution (may fail occasionally)" in {
    val g = new Gaussian(2.0, 0.5)
    val xs = g.pullN(100000)
    val g2 = Gaussian(xs)
    (g2.mean()*10).round/10.0 should be (2.0 +- 1e-8)
    (g2.variance()*10).round/10.0 should be (0.5 +- 1e-8)
  }
}