import utils.FixedVector2D

package object game {
  object Player extends Enumeration {
    type Player = Value
    val first = Value("Player 1")
    val second = Value("Player 2")
  }


  abstract class Square // case class: empty, populated?

  case class Populated(piece: Piece, player: Player.Player) extends Square

  case object EmptySquare extends Square

  type Placements = FixedVector2D[Square]
  type PieceMap = Map[(Int, Int), Piece]

}
