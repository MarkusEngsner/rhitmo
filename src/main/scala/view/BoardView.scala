package view

import game.Player
import game.{Populated, Square}
import org.scalajs.dom
import org.scalajs.dom.Element

case class BoardView(boardDiv: Element, state: game.GameState) {
  // CSS stuff
  type CSSClass = String
  val pieceCSSMap: Map[Player.Player, CSSClass] = Map(Player.first -> "p1-piece",
    Player.second -> "p2-piece")

  def colorizeBoard(): Unit = {
    val primarySquare = dom.document.createElement("div");
    primarySquare.classList.add("primary-square")
    val secondarySquare = dom.document.createElement("div");
    secondarySquare.classList.add("secondary-square")
    println(state.board.width)
    val squares = for {
      y <- 0 until state.board.height
      x <- 0 until state.board.width
      primary = (x + y) % 2 == 0
      square = (if (primary) primarySquare else secondarySquare).cloneNode()
    } yield square
    squares foreach (boardDiv.appendChild(_))

  }

  def setup(): Unit = {
    colorizeBoard()
    populateBoard()
    // add event listener
    boardDiv.addEventListener("click", { (e: dom.MouseEvent) =>
      val rect = boardDiv.getBoundingClientRect()
      // calculate coordinates relative to board
      val x = e.clientX - rect.left
      val y = e.clientY - rect.top
      // calculate square indices
      val i = (x / rect.width * state.board.width).toInt
      val j = (y / rect.height * state.board.height).toInt
      println((i, j))
    })
  }

  // Places the pieces on the board
  def populateBoard(): Unit = {
    val pieceMap = state.board.getPieceMap
    println(boardDiv.childElementCount)
    println("About to add pieces...")
    pieceMap foreach { case ((x: Int, y: Int), sq: Populated) => {
      println((x, y))
      val e: Element = buildPiece(sq.piece)
      e.classList.add(pieceCSSMap(sq.player))
      val squareIndex = x * state.board.width + y
      println(squareIndex)
      boardDiv.children(squareIndex).appendChild(e)
      // add to correct square
    }
    }

  }


  def buildPiece(p: game.Piece): Element = {
    val svg = dom.document.createElementNS("http://www.w3.org/2000/svg", "svg")
    svg.setAttribute("width", "50")
    svg.setAttribute("height", "50")
    svg.classList.add("game-piece")
    p match {
      case game.Circle(_) => {
        val circle = dom.document.createElementNS("http://www.w3.org/2000/svg", "circle")
        circle.setAttribute("cx", "25")
        circle.setAttribute("cy", "25")
        circle.setAttribute("r", "20")
        circle.setAttribute("stroke", "none")
        circle.setAttribute("fill", "#eeeeee")
        svg.appendChild(circle)
      }
      case game.PSquare(_) => {
        val square = dom.document.createElementNS("http://www.w3.org/2000/svg", "rect")
        square.setAttribute("x", "5")
        square.setAttribute("y", "5")
        square.setAttribute("width", "40")
        square.setAttribute("height", "40")
        square.setAttribute("style", "stroke:none;fill:#eeeeee")
        svg.appendChild(square)
      }
      case game.Triangle(_) => {
        // TODO: fix text alignment in piece when triangle
        val triangle = dom.document.createElementNS("http://www.w3.org/2000/svg", "polygon")
        triangle.setAttribute("points", "5,45 45,45 25,5")
        triangle.setAttribute("style", "stroke:none;fill:#eeeeee")
        svg.appendChild(triangle)
      }
    }

    val text = dom.document.createElementNS("http://www.w3.org/2000/svg", "text")
    text.setAttribute("x", "50%")
    text.setAttribute("y", "50%")
    text.setAttribute("dominant-baseline", "middle")
    text.setAttribute("text-anchor", "middle")
    text.setAttribute("fill", "black")
    text.innerHTML = p.n.toString
    svg.appendChild(text)
    svg
  }
}
