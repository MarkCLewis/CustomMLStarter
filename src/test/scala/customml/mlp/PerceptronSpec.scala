package customml.mlp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import customml.UtilityFunctions._

class PerceptronSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds

  "A perceptron" should "do a basic linear fit" in {
    val xr = randomized((0 to 100).map(x => Seq(x.toDouble) -> Seq(20.0 - 2 * x)))
    val perceptron = new Perceptron(xr, Perceptron.Regressor)
    perceptron.fit(Seq(0.0)).foreach(_ should be (20.0 +- 0.1))
    perceptron.fit(Seq(0.5)).foreach(_ should be (19.0 +- 0.1))
    perceptron.fit(Seq(10.0)).foreach(_ should be (0.0 +- 0.1))
    perceptron.fit(Seq(9.5)).foreach(_ should be (1.0 +- 0.1))
  }

  it should "do linear, 3-D fits" in {
    val xr = (0 to 100).map(x => {
      val (x1, x2, x3) = (math.random, math.random, math.random)
      Seq(x1, x2, x3) -> Seq(20.0 - 2 * x1 + x2 * 5 + 3 * x3)
    })
    val perceptron = new Perceptron(xr, Perceptron.Regressor)
    (perceptron.fit(Seq(0.0, 0.0, 0.0)), Seq(20.0 +- 0.1)).zipped.foreach((fit, actual) => fit should be (actual))
    (perceptron.fit(Seq(1.0, 0.0, 0.0)), Seq(18.0 +- 0.1)).zipped.foreach((fit, actual) => fit should be (actual))
    (perceptron.fit(Seq(0.0, 1.0, 0.0)), Seq(25.0 +- 0.1)).zipped.foreach((fit, actual) => fit should be (actual))
    (perceptron.fit(Seq(0.0, 0.0, 1.0)), Seq(23.0 +- 0.1)).zipped.foreach((fit, actual) => fit should be (actual))
    (perceptron.fit(Seq(0.5, 0.5, 0.5)), Seq(23.0 +- 0.1)).zipped.foreach((fit, actual) => fit should be (actual))
  }

  it should "do linear discrimination of two classes 2-D" in {
    val xr = (0 to 100).map(x => {
      val (x1, x2) = (math.random, math.random)
      val r1 = if (x1 > x2) 1.0 else 0.0
      Seq(x1, x2) -> Seq(r1, 1.0 - r1)
    })
    val perceptron = new Perceptron(xr, Perceptron.Classifier)
    (perceptron.classify(Seq(1.0, 0.0)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.0, 1.0)), Seq(0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.2, 0.0)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.2, 0.6)), Seq(0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(1.0, 0.8)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
  }

  it should "do linear discrimination of four classes" in {
    val xr = (0 to 100).map(x => {
      val (x1, x2) = (math.random * 2 - 1, math.random * 2 - 1)
      val c = (if (x1 > x2) 2 else 0) + (if (x1 > -x2) 1 else 0)
      Seq(x1, x2) -> Seq.tabulate(4)(i => if (i == c) 1.0 else 0.0)
    })
    val perceptron = new Perceptron(xr, Perceptron.Classifier)
    (perceptron.classify(Seq(1.0, 0.0)), Seq(0.0, 0.0, 0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(-1.0, 0.0)), Seq(1.0, 0.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.0, 1.0)), Seq(0.0, 1.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.0, -1.0)), Seq(0.0, 0.0, 1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.5, 0.1)), Seq(0.0, 0.0, 0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(-0.5, 0.1)), Seq(1.0, 0.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.2, 0.8)), Seq(0.0, 1.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (perceptron.classify(Seq(0.3, -0.9)), Seq(0.0, 0.0, 1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
  }
}