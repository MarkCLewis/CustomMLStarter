package customml.multivariatemethods

import org.scalatest.flatspec._
import org.scalatest.matchers._
import customml.stats.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class NaiveBayesClassifierSpec extends AnyFlatSpec with should.Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  
  "Naive Bayes Classifier" should "properly classify small input" in {
    val (nbc, legend) = NaiveBayesClassifier[String](Seq(
        (Seq(0,1,0), "A"), 
        (Seq(0.1,1.1,0.2), "A"),
        (Seq(-0.1,0.9,-0.2), "A"),
        (Seq(3,0,0), "B"), 
        (Seq(3.1,0.1,0.2), "B")))
    legend(nbc.classify(Seq(0.0,1.0,0.0)).indexOf(1.0)) should be ("A")
    legend(nbc.classify(Seq(0.1,1.1,0.3)).indexOf(1.0)) should be ("A")
    legend(nbc.classify(Seq(-0.1,1.0,0.0)).indexOf(1.0)) should be ("A")
    legend(nbc.classify(Seq(2.9,0.1,0.2)).indexOf(1.0)) should be ("B")
    legend(nbc.classify(Seq(3.0,0.2,0.0)).indexOf(1.0)) should be ("B")
  }
  
  it should "properly classify big inputs" in {
    val ga = Seq(new Gaussian(0.0, 0.25), new Gaussian(0.0, 0.25), new Gaussian(0.0, 0.25))
    val adata = ga.map(_.pullN(10000)).transpose.map(xs => xs -> "a")
    val gb = Seq(new Gaussian(-1.0, 0.25), new Gaussian(0.0, 0.25), new Gaussian(1.0, 0.25))
    val bdata = gb.map(_.pullN(10000)).transpose.map(xs => xs -> "b")
    val gc = Seq(new Gaussian(1.0, 0.25), new Gaussian(0.5, 0.25), new Gaussian(1.0, 0.25))
    val cdata = gc.map(_.pullN(10000)).transpose.map(xs => xs -> "c")
    val (nbc, legend) = NaiveBayesClassifier(adata ++ bdata ++ cdata)
    legend(nbc.classify(Seq(0.0,0.0,0.0)).indexOf(1.0)) should be ("a")
    legend(nbc.classify(Seq(0.1,0.0,0.0)).indexOf(1.0)) should be ("a")
    legend(nbc.classify(Seq(-0.1,0.0,0.0)).indexOf(1.0)) should be ("a")
    legend(nbc.classify(Seq(-0.9,0.0,1.1)).indexOf(1.0)) should be ("b")
    legend(nbc.classify(Seq(-1.0,0.0,0.9)).indexOf(1.0)) should be ("b")
    legend(nbc.classify(Seq(-1.1,0.0,0.8)).indexOf(1.0)) should be ("b")
    legend(nbc.classify(Seq(1.1,0.2,1.2)).indexOf(1.0)) should be ("c")
    legend(nbc.classify(Seq(1.3,0.5,1.0)).indexOf(1.0)) should be ("c")
    legend(nbc.classify(Seq(0.8,0.7,0.8)).indexOf(1.0)) should be ("c")
  }
  
}