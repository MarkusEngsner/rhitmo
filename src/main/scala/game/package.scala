import utils.FixedVector2D

package object game {
  abstract class Square // case class: empty, populated?

  case class Populated(val piece: Piece) extends Square

  case object EmptySquare extends Square

  type Placements = FixedVector2D[Square]

}
