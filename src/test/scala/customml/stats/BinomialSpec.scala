package customml.stats

import org.scalatest.flatspec._
import org.scalatest.matchers._
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class BinomialSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  
  "Binomial" should "have a mean of 0.5 with inputs 1, Seq(1,0)" in {
    Binomial(1, Seq(1,0)).mean() should be(0.5 +- 1e-8)
  }

  it should "have a variance of 0.25 with inputs 1, Seq(1,0)" in {
    Binomial(1, Seq(1,0)).variance() should be(0.25 +- 1e-8)
  }
  
  it should "find the proper mean and variance for input data (may fail occasionally)" in {
    val n = 5
    val p = 0.75
    val xs = (1 to 100000).map(_ => (1 to n).map(_ => if(math.random < p) 1.0 else 0.0).sum)
    val g = Binomial(n, xs)
    (g.mean() * 100).round / 100.0 should be ((p*n*100).round / 100.0 +- 1e-8)
    (g.variance() * 100).round / 100.0 should be ((p*(1-p)*n*100).round / 100.0 +- 1e-8)
  }

  it should "generate a proper distribution (may fail occasionally)" in {
    val n = 5
    val p = 0.3
    val g = new Binomial(n, p)
    val xs = g.pullN(100000)
    val g2 = Binomial(n, xs)
    (g2.mean() * 100).round / 100.0 should be (p*n +- 1e-8)
    (g2.variance() * 100).round / 100.0 should be (p*(1-p)*n +- 1e-8)
  }
}