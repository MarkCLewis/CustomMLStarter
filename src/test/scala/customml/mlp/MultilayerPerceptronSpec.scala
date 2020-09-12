package customml.mlp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.stats.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._
import customml.UtilityFunctions._

class MultilayerPerceptronSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 60.seconds

  "A multilayer perceptron" should "do a basic linear fit" in {
    val xr = randomized((0 to 1000).map { _ =>
      val x = math.random *2 - 1
      Seq(x) -> Seq(2.0 - 2 * x) 
    })
    val mlp = new MultilayerPerceptron(xr, 2, Perceptron.Regressor)
    mlp.fit(Seq(-1.0)).foreach(_ should be (4.0 +- 0.25))
    mlp.fit(Seq(-0.5)).foreach(_ should be (3.0 +- 0.25))
    mlp.fit(Seq(0.0)).foreach(_ should be (2.0 +- 0.25))
    mlp.fit(Seq(0.5)).foreach(_ should be (1.0 +- 0.25))
    mlp.fit(Seq(1.0)).foreach(_ should be (0.0 +- 0.25))
  }

  it should "do linear, 3-D fits" in {
    val xr = (0 to 1000).map(_ => {
      val (x1, x2, x3) = (math.random, math.random, math.random)
      Seq(x1, x2, x3) -> Seq(20.0 - 2 * x1 + x2 * 5 + 3 * x3)
    })
    val mlp = new MultilayerPerceptron(xr, 4, Perceptron.Regressor)
    (mlp.fit(Seq(0.0, 0.0, 0.0)), Seq(20.0 +- 0.75)).zipped.foreach((fit, actual) => fit should be (actual))
    (mlp.fit(Seq(1.0, 0.0, 0.0)), Seq(18.0 +- 0.75)).zipped.foreach((fit, actual) => fit should be (actual))
    (mlp.fit(Seq(0.0, 1.0, 0.0)), Seq(25.0 +- 0.75)).zipped.foreach((fit, actual) => fit should be (actual))
    (mlp.fit(Seq(0.0, 0.0, 1.0)), Seq(23.0 +- 0.75)).zipped.foreach((fit, actual) => fit should be (actual))
    (mlp.fit(Seq(0.5, 0.5, 0.5)), Seq(23.0 +- 0.75)).zipped.foreach((fit, actual) => fit should be (actual))
  }

  it should "do a non-linear fit" in {
    val xr = randomized((1 to 1000).map { _ => 
      val x = math.random * 2 - 1
      Seq(x) -> Seq(x * x - 5.0)
    })
    val mlp = new MultilayerPerceptron(xr, 5, Perceptron.Regressor)
    mlp.fit(Seq(-1.0)).foreach(_ should be (-4.0 +- 0.25))
    mlp.fit(Seq(-0.5)).foreach(_ should be (-4.75 +- 0.25))
    mlp.fit(Seq(0.0)).foreach(_ should be (-5.0 +- 0.25))
    mlp.fit(Seq(0.5)).foreach(_ should be (-4.75 +- 0.25))
    mlp.fit(Seq(1.0)).foreach(_ should be (-4.0 +- 0.25))
  }

  it should "do linear discrimination of two classes 2-D" in {
    val xr = (0 to 100).map(x => {
      val (x1, x2) = (math.random, math.random)
      val r1 = if (x1 > x2) 1.0 else 0.0
      Seq(x1, x2) -> Seq(r1, 1.0 - r1)
    })
    val mlp = new MultilayerPerceptron(xr, 3, Perceptron.Classifier)
    (mlp.classify(Seq(1.0, 0.0)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.0, 1.0)), Seq(0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.2, 0.0)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.2, 0.6)), Seq(0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(1.0, 0.8)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
  }

  it should "do linear discrimination of four classes" in {
    val xr = (0 to 100).map(x => {
      val (x1, x2) = (math.random * 2 - 1, math.random * 2 - 1)
      val c = (if (x1 > x2) 2 else 0) + (if (x1 > -x2) 1 else 0)
      Seq(x1, x2) -> Seq.tabulate(4)(i => if (i == c) 1.0 else 0.0)
    })
    val mlp = new MultilayerPerceptron(xr, 3, Perceptron.Classifier)
    (mlp.classify(Seq(1.0, 0.0)), Seq(0.0, 0.0, 0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(-1.0, 0.0)), Seq(1.0, 0.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.0, 1.0)), Seq(0.0, 1.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.0, -1.0)), Seq(0.0, 0.0, 1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.5, 0.1)), Seq(0.0, 0.0, 0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(-0.5, 0.1)), Seq(1.0, 0.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.2, 0.8)), Seq(0.0, 1.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.3, -0.9)), Seq(0.0, 0.0, 1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
  }

  it should "do a non-linear discirmination of two classes" in {
    val xr = (0 to 100).map(x => {
      val (x1, x2) = (math.random * 2 - 1, math.random * 2 - 1)
      val r1 = if (x2 > x1 * x1) 1.0 else 0.0
      Seq(x1, x2) -> Seq(r1, 1.0 - r1)
    })
    val mlp = new MultilayerPerceptron(xr, 5, Perceptron.Classifier)
    (mlp.classify(Seq(1.0, 0.0)), Seq(0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(-1.0, 0.0)), Seq(0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.0, 1.0)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.5, 0.5)), Seq(1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(-0.5, -0.25)), Seq(0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
  }

  it should "do a non-linear discirmination of four classes" in {
    val xr = (0 to 100).map(x => {
      val (x1, x2) = (math.random * 2 - 1, math.random * 2 - 1)
      val c = if (x2 > x1 * x1) 0 else if (x2 < -x1 * x1) 1 else if (x1 > 0) 2 else 3
      Seq(x1, x2) -> Seq.tabulate(4)(i => if (i == c) 1.0 else 0.0)
    })
    val mlp = new MultilayerPerceptron(xr, 7, Perceptron.Classifier)
    (mlp.classify(Seq(1.0, 0.0)), Seq(0.0, 0.0, 1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(-1.0, 0.0)), Seq(0.0, 0.0, 0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.0, 1.0)), Seq(1.0, 0.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.0, -1.0)), Seq(0.0, 1.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.5, 0.1)), Seq(0.0, 0.0, 1.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(-0.6, 0.2)), Seq(0.0, 0.0, 0.0, 1.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.1, 0.3)), Seq(1.0, 0.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
    (mlp.classify(Seq(0.2, -0.4)), Seq(0.0, 1.0, 0.0, 0.0)).zipped.foreach((c, actual) => c should be (actual))
  }
}