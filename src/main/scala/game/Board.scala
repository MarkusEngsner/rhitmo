package game

import game.Piece


class Board(val width: Int, val height: Int, placements: Placements) {


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

  def addPiece(x: Int, y: Int, piece: Piece): Board = {
    addPiece(x, y, Populated(piece))
  }


  // Should perhaps return removed piece too?
  def removePiece(x: Int, y: Int): Board = {
    val square = placements(x, y)
    square match {
      case EmptySquare => this // TODO: add failure/exception or something here
      case Populated(_) => addPiece(x, y, EmptySquare)
    }
  }


  // TODO: add move validation, removing pieces that are hit
  def movePiece(x0: Int, y0: Int, x1: Int, y1: Int): Board = {
    val piece = placements(x0, y0)
    removePiece(x0, y0) addPiece(x1, y1, piece)
  }

}
