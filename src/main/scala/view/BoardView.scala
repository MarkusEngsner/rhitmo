package view

import org.scalajs.dom
import org.scalajs.dom.Element

case class BoardView(boardDiv: Element, state: game.GameState) {

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
    // add event listener
    boardDiv.addEventListener("click", {(e: dom.MouseEvent) =>
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
}
