package customml.decisiontree

/**
 * This is a decision tree classifier. Note that you don't ever calculate distances between
 * the output values, r, so they aren't one-hot encoded here. You should split the tree
 * until you get to a certain level of entropy. This is theta_I in the pseudocode from the
 * textbook.
 */
class UnivariateDecisionTree[A](xr: Seq[(Seq[Double], A)], minEntropy: Double) {
  import UnivariateDecisionTree._

  val root = buildTree(xr)

  /**
   * This method will build the tree from the data. There should probably be some recursion in here.
   * Note that to pass my simple tests, the location where you do your cut 
   */
  def buildTree(data: Seq[(Seq[Double], A)]): Node[A] = {
    ???
  }

  def classify(x: Seq[Double]): A = {
    ???
  }
}

object UnivariateDecisionTree {
  sealed trait Node[A]
  // Make appropriate subtypes of Node here. Case classes work well.
}