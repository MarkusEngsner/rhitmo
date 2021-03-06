package controller

import game.{Circle, Piece}
import org.scalajs.dom
import org.scalajs.dom.document
import view.BoardView

object App {

  def main(args: Array[String]): Unit = {
    val state = game.BoardConfigurations.getDefaultGameState
    val boardElement = document.createElement("div")
    boardElement.classList.add("board")
    boardElement.id = "main-board"
    document.getElementById("main-area").appendChild(boardElement)
    setupUI()
//    val boardElement = document.getElementById("main-board")
    println(boardElement)
    val view = BoardView(boardElement, state)
    view.setup()
    println("Main almost done")
    val x = document.getElementById("main-board")
    println(x)
  }




  def setupUI(): Unit = {
    val button = document.createElement("button")
    button.textContent = "Click me!"
    button.addEventListener("click", { (e: dom.MouseEvent) =>
      addClickedMessage()
    })
    document.body.appendChild(button)
    appendPar(document.body, "Hello World")
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    parNode.textContent = text
    targetNode.appendChild(parNode)
  }

  def addClickedMessage(): Unit = {
    appendPar(document.body, "You clicked the button!")
    buildPiece(Circle(10))
  }


  def buildPiece(p: Piece): Unit = {
    //val x = dom.svg.
    //val svg = document.createElement("svg")
    val svg = document.createElementNS("http://www.w3.org/2000/svg", "svg")
    svg.setAttribute("width", "50")
    svg.setAttribute("height", "50")
    p match {
      case Circle(n) => {
        val circle = document.createElementNS("http://www.w3.org/2000/svg", "circle")
        circle.setAttribute("cx", "25")
        circle.setAttribute("cy", "25")
        circle.setAttribute("r", "20")
        circle.setAttribute("stroke", "none")
        circle.setAttribute("fill", "#eeeeee")
        svg.appendChild(circle)
        val text = document.createElementNS("http://www.w3.org/2000/svg", "text")
        text.setAttribute("x", "50%")
        text.setAttribute("y", "50%")
        text.setAttribute("dominant-baseline", "middle")
        text.setAttribute("text-anchor", "middle")
        text.setAttribute("fill", "black")
        text.innerHTML = n.toString
        svg.appendChild(text)
      }
    }
    val x = document.getElementById("main-board")
    println(x)
    document.getElementById("main-board").appendChild(svg)
  }

}
