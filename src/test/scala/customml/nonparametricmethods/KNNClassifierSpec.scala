package customml.nonparametricmethods

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.stats.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import customml.UtilityFunctions._

class KNNClassifierSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds

  "k-nn classifier" should "classify a simple test" in {
    val xr = Seq(
      (Seq(1.0, 1.0), "A"), 
      (Seq(-1.0, -1.0), "B"),
      (Seq(-1.0, 1.0), "C"))
    val c = new KNNClassifier(1, xr, euclidean)
    c.classify(Seq(0.5, 0.75)) should be ("A")
    c.classify(Seq(-0.5, -0.75)) should be ("B")
    c.classify(Seq(-0.5, 0.75)) should be ("C")
  }
  
  it should "classify a bigger test" in {
    val xr = randomized(Seq.fill(100)(Seq(math.random + 3, math.random + 3) -> "A") ++
      Seq.fill(100)(Seq(math.random - 3, math.random - 3) -> "B") ++
      Seq.fill(100)(Seq(math.random + 3, math.random - 3) -> "C") ++
      Seq.fill(100)(Seq(math.random - 3, math.random + 3) -> "D"))
    val c = new KNNClassifier(5, xr, euclidean)
    c.classify(Seq(3.0, 3.0)) should be ("A")
    c.classify(Seq(-3.0, -3.0)) should be ("B")
    c.classify(Seq(-3.0, 3.0)) should be ("D")
    c.classify(Seq(3.0, -3.0)) should be ("C")
  }
}