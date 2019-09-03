package customml.clustering

import org.scalatest._
import org.scalatest.concurrent.TimeLimitedTests
import org.scalatest.time.SpanSugar._

class HierarchicalClusteringSpec extends FlatSpec with Matchers with TimeLimitedTests {
  val timeLimit = 20.seconds
  /*
  "HierarchicalClustering" should "give a simple tree for a simple input" in {
    val pnts = Seq(NVect(-1, 0.1), NVect(-1, -0.1), NVect(1, 0.1), NVect(1, -0.1))
    val tree = HierarchicalClustering.hClusters(pnts, (g1, g2) => NVect.average(g1).distSqr(NVect.average(g2)))
    tree.center should be (NVect(0.0, 0.0))
    tree.level should be (3)
    val twoClusters = HierarchicalClustering.findKClusters(tree, 2).sortBy(_.center(0))
    twoClusters(0).center should be (NVect(-1, 0))
    twoClusters(1).center should be (NVect(1, 0))
  }
  
  it should "produce a more interesting tree for a larger input" in {
    val c1 = (1 to 50).map(_ => NVect(math.random-5.5, math.random+1.5))
    val c2 = (1 to 50).map(_ => NVect(math.random+4.5, math.random+1.5))
    val c3 = (1 to 50).map(_ => NVect(math.random-5.5, math.random-2.5))
    val c4 = (1 to 50).map(_ => NVect(math.random+4.5, math.random-2.5))
    val allPoints = c1 ++ c2 ++ c3 ++ c4
    val tree = HierarchicalClustering.hClusters(allPoints, (g1, g2) => NVect.average(g1).distSqr(NVect.average(g2)))
    tree.center should be (NVect.average(allPoints))
    tree.level should be (199)
    val twoClusters = HierarchicalClustering.findKClusters(tree, 2).sortBy(_.center(0))
    twoClusters(0).center should be (NVect.average(c1 ++ c3))
    twoClusters(1).center should be (NVect.average(c2 ++ c4))
    val fourClusters = HierarchicalClustering.findKClusters(tree, 4).sortBy { p => val c = p.center; c(0)+c(1) }
    fourClusters(0).center should be (NVect.average(c3))
    fourClusters(1).center should be (NVect.average(c1))
    fourClusters(2).center should be (NVect.average(c4))
    fourClusters(3).center should be (NVect.average(c2))
  }
  */
}