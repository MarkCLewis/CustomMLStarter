package customml.parametricmethods

import org.scalatest._
import customml.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class Classifier1DSpec extends FlatSpec with Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  /*
  "Classifier1D" should "properly classify points with a very simple input" in {
    val (c1d, legend) = Classifier1D(Seq((0.0, "A"), (1.0, "B"), (3.0, "C"),(0.01, "A"), (1.01, "B"), (3.01, "C")))
    legend(c1d.classify(0.0).indexOf(1.0)) should be ("A")
    legend(c1d.classify(0.1).indexOf(1.0)) should be ("A")
    legend(c1d.classify(-0.1).indexOf(1.0)) should be ("A")
    legend(c1d.classify(0.9).indexOf(1.0)) should be ("B")
    legend(c1d.classify(1.0).indexOf(1.0)) should be ("B")
    legend(c1d.classify(1.1).indexOf(1.0)) should be ("B")
    legend(c1d.classify(2.5).indexOf(1.0)) should be ("C")
    legend(c1d.classify(3.0).indexOf(1.0)) should be ("C")
    legend(c1d.classify(10.0).indexOf(1.0)) should be ("C")
  }
  
  it should "properly classify points in the more complex model" in {
    val ga = new Gaussian(0.0, 0.25)
    val adata = ga.pullN(10000).map(x => x -> "a")
    val gb = new Gaussian(1.0, 0.25)
    val bdata = gb.pullN(10000).map(x => x -> "b")
    val gc = new Gaussian(3.0, 0.25)
    val cdata = gc.pullN(20000).map(x => x -> "c")
    val (c1d, legend) = Classifier1D(adata ++ bdata ++ cdata)
    legend(c1d.classify(0.0).indexOf(1.0)) should be ("a")
    legend(c1d.classify(0.1).indexOf(1.0)) should be ("a")
    legend(c1d.classify(-0.1).indexOf(1.0)) should be ("a")
    legend(c1d.classify(0.9).indexOf(1.0)) should be ("b")
    legend(c1d.classify(1.0).indexOf(1.0)) should be ("b")
    legend(c1d.classify(1.1).indexOf(1.0)) should be ("b")
    legend(c1d.classify(2.5).indexOf(1.0)) should be ("c")
    legend(c1d.classify(3.0).indexOf(1.0)) should be ("c")
    legend(c1d.classify(10.0).indexOf(1.0)) should be ("c")
  }
  */
}