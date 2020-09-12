package customml.nonparametricmethods

/**
 * This is a k-nn smoother for non-parametric regression. Use the provided distance function to find the k nearest
 * neighbors and the provided kernel for weighting of points based on that distance.
 */
class KNNSmoother(k: Int, xr: Seq[(Seq[Double], Double)], kernel: Double => Double, distance: (Seq[Double], Seq[Double]) => Double) {
  def fit(x: Seq[Double]): Double = {
    ???
  }
}