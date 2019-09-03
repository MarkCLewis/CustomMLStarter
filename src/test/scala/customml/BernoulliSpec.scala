package customml

import org.scalatest._
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class BernoulliSpec extends FlatSpec with Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  
  "Bernoulli" should "have a mean of 0.5 with Array(0,1)" in {
    Bernoulli(Array(0, 1)).mean() should be(0.5)
  }

  it should "have a variance of 0.25 with Array(0,1)" in {
    Bernoulli(Array(0, 1)).variance() should be(0.25)
  }

  it should "have a mean of 0.25 with Array(0,1,0,0)" in {
    Bernoulli(Array(0, 1, 0, 0)).mean() should be(0.25)
  }

  it should "have a variance of 3/16 with Array(0,1,0,0)" in {
    Bernoulli(Array(0, 1, 0, 0)).variance() should be(3.0 / 16.0)
  }

  it should "find the proper mean and variance for input data (may fail occasionally)" in {
    val xs = (1 to 100000).map(_ => if(math.random < 0.8) 1.0 else 0.0)
    val g = Bernoulli(xs)
    (g.mean() * 100).round / 100.0 should be(0.8)
    (g.variance() * 100).round / 100.0 should be(0.16)
  }

  it should "generate a proper distribution (may fail occasionally)" in {
    val g = new Bernoulli(0.3)
    val xs = g.pullN(100000)
    val g2 = Bernoulli(xs)
    (g2.mean() * 100).round / 100.0 should be(0.3)
    (g2.variance() * 100).round / 100.0 should be(0.21)
  }

}