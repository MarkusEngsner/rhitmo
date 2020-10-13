package game

abstract class Piece {
  val n: Int
}

case class Triangle(n: Int) extends Piece

case class Circle(n: Int) extends Piece

case class PSquare(n: Int) extends Piece
