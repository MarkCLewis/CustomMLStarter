package customml.association

object Associations {
  case class Association[A](antecedent: Set[A], consequent: Set[A])
  def apriori[A](groupings: Seq[Set[A]], desiredGroupSize: Int, minSupport: Double, minConfidence: Double): Set[Association[A]] = {
    ???
  }
}