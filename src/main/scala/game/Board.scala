package game

import game.{Piece, PSquare, Circle, Triangle}
import utils.FixedVector2D


class Board(val width: Int, val height: Int, val placements: Placements) {


  def this(width: Int, height: Int) {
    this(width, height, new Placements(width, height, EmptySquare))
  }

  def this(placements: Placements) {
    this(placements.width, placements.height, placements)
  }

  def addPiece(x: Int, y: Int, square: Square): Board = {
    val newPlacements = placements.updated(x, y, square)
    new Board(newPlacements)
  }

  def addPiece(x: Int, y: Int, piece: Piece, player: Player.Player): Board = {
    addPiece(x, y, Populated(piece, player))
  }


  // Should perhaps return removed piece too?
  def removePiece(x: Int, y: Int): Board = {
    val square = placements(x, y)
    square match {
      case EmptySquare => this // TODO: add failure/exception or something here
      case Populated(_, _) => addPiece(x, y, EmptySquare)
    }
  }


  // TODO: add move validation, removing pieces that are hit
  def movePiece(x0: Int, y0: Int, x1: Int, y1: Int): Board = {
    val piece = placements(x0, y0)
    removePiece(x0, y0).addPiece(x1, y1, piece)
  }

  // Note: due to type erasure, we will for now have to deal with returning a map
  // of type square, and not Populated. Could be solved by putting the piece owner
  // into the piece data.
  def getPieceMap: Map[(Int, Int), Square] = for {
    (index, sq) <- placements.to2DMap
    if (sq match {
      case Populated(_, _) => true
      case _ => false
    })
  } yield (index, sq)
}


object BoardConfigurations {

  val firstPlayerMappings: PieceMap = Map(
    (0, 2) -> PSquare(289),
    (1, 2) -> PSquare(169),
    (0, 3) -> PSquare(153),
    (0, 4) -> Triangle(81),
  )

  val secondPlayerMappings: PieceMap = Map(
    (0, 13) -> PSquare(49),
    (0, 12) -> PSquare(28),
    (3, 5) -> Circle(10), // not a correct placement!
  )


  def mergeMappings(map1: PieceMap, map2: PieceMap): Map[(Int, Int), Square] = {
    def pieceToPopulated(player: Player.Player)(index: (Int, Int), p: Piece): Square =
      Populated(p, player)

    val squareMap1 = map1 transform pieceToPopulated(Player.first)
    val squareMap2 = map2 transform pieceToPopulated(Player.second)
    squareMap1 ++ squareMap2
  }

  def emptyMapping(width: Int, height: Int): Map[Int, Square] =
    Map((for {
      i <- 0 to height * width
    } yield (i, EmptySquare)): _*)


  def flattenMapping(w: Int, h: Int)(p: ((Int, Int), Square)) = p match {
    case ((x: Int, y: Int), s) => (x * w + y, s)
  }

  def constructBoard(map1: PieceMap, map2: PieceMap): Vector[Square] = {
    val width = 16
    val height = 8
    val mMap = mergeMappings(map1, map2)
    val fMap = mMap map flattenMapping(width, height)
    val filledMap = emptyMapping(width, height) ++ fMap
    val sortedVec = filledMap.toVector.sortWith { case ((lhs, rhs)) => lhs._1 < rhs._1 }
    sortedVec map { case (i, s) => s }
  }


  def getDefaultBoard: Board = {
    val vec = constructBoard(firstPlayerMappings, secondPlayerMappings)
    val placements = new Placements(16, 8, vec, EmptySquare)
    new Board(placements)
  }

  def getDefaultGameState: GameState = GameState(getDefaultBoard, Player.first)

  //  val DefaultPlacements: Placements = new Placements(16, 8, Vector[Square](
  //    EmptySquare, EmptySquare, Populated(Square(289)),
  //  ), EmptySquare)
}