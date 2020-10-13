package tutorial.webapp

import org.scalajs.dom
import org.scalajs.dom.document
import game.{Circle, Piece, Triangle, PSquare}
import scala.scalajs.js.annotation.JSExportTopLevel

object TutorialApp {
  def main(args: Array[String]): Unit = {
    setupUI()
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
    document.getElementById("main-board").appendChild(svg)
  }

}
