package customml.association

object Associations {
  /**
   * Represents an association of the form antecedent -> consequent.
   */
  case class Association[A](antecedent: Set[A], consequent: Set[A])

  /**
   * Use the apriori algorithm to calculate the appropriate associations for the specified groupings. The groupings are the
   * items that all come together, for example, orders from customers. We are looking for associations that have a particular
   * number of elements in the antecedent and consequent combined. Every association returned needs to have the specified minimum
   * support and confidence.
   * @param groupings The groups of elements that you should find associations for.
   * @param desiredAssociationSize The number of elements in the associations that you should be building.
   * @param minSupport The minimum required support for all the desired associations.
   * @param minConfidence The minimum required confidence for all the desired associations.
   */
  def apriori[A](groupings: Seq[Set[A]], desiredAssociationSize: Int, minSupport: Double, minConfidence: Double): Set[Association[A]] = {
    ???
  }
}