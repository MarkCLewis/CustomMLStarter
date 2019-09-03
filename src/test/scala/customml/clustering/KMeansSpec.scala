package customml.clustering

import org.scalatest._
import customml.Gaussian
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class KMeansSpec extends FlatSpec with Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  /*
  "k-means" should "properly cluster four points into two groups" in {
    val (centers, map) = KMeans.kMeans(Seq(new NVect(Seq(-1.0,0.1)),new NVect(Seq(-1.0,-0.1)),new NVect(Seq(1.1,0.1)),new NVect(Seq(0.9,-0.1))), 2)
    centers.length should be (2)
    map.length should be (4)
    map.count(_ == 0) should be (2)
    map.count(_ == 1) should be (2)
    val d = centers(0) dist centers(1)
    d should be (2.0)
  }
  
  it should "properly cluster three random groups (might fail occasionally)" in {
    val g = new Gaussian(0.0, 0.01)
    val g1 = Seq.fill(1000)(new NVect(Seq(-1.0+g.next(), 1.0+g.next())))
    val g2 = Seq.fill(1000)(new NVect(Seq(0.0+g.next(), -1.0+g.next())))
    val g3 = Seq.fill(1000)(new NVect(Seq(1.0+g.next(), 1.0+g.next())))
    val (centers, map) = KMeans.kMeans(g1 ++ g2 ++ g3, 3)
    centers.length should be (3)
    map.length should be (3000)
    map.count(_ == 0) should be (1000)
    map.count(_ == 1) should be (1000)
    map.count(_ == 2) should be (1000)
    val dists = List(centers(0) dist centers(1), centers(1) dist centers(2), centers(2) dist centers(0)).sorted.map(x => (x*10).round/10.0)
    dists(0) should be (2.0)
    dists(1) should be (2.2)
    dists(2) should be (2.2)
  }
  */
}