package customml.decisiontree

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import customml.UtilityFunctions._

class UnivariateDecisionTreeSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds

  "A univeriate decision tree" should "do simple classification" in {
    val xr = Seq(
      (Seq(1.0, 1.0), "A"), 
      (Seq(-1.0, -1.0), "B"),
      (Seq(-0.9, 0.9), "C"))
    val udt = new UnivariateDecisionTree(xr, 0.01)
    udt.classify(Seq(0.5, 0.75)) should be ("A")
    udt.classify(Seq(-1.1, -0.75)) should be ("B")
    udt.classify(Seq(-0.5, 0.75)) should be ("C")
  }

  it should "do larger scale classification" in {
    val xr = randomized(Seq.fill(100)(Seq(math.random + 3, math.random + 3) -> "A") ++
      Seq.fill(100)(Seq(math.random - 3, math.random - 3) -> "B") ++
      Seq.fill(100)(Seq(math.random + 3, math.random - 3) -> "C") ++
      Seq.fill(100)(Seq(math.random - 3, math.random + 3) -> "D"))
    val c = new UnivariateDecisionTree(xr, 0.01)
    c.classify(Seq(3.0, 3.0)) should be ("A")
    c.classify(Seq(-3.0, -3.0)) should be ("B")
    c.classify(Seq(-3.0, 3.0)) should be ("D")
    c.classify(Seq(3.0, -3.0)) should be ("C")
  }
}