package utils

// a fixed size, immutable 2D vector
class FixedVector2D[+A](val width: Int, val height: Int,
                        private val v: Vector[A], private val emptyVal: A) {

  def this(width: Int, height: Int, defaultVal: A){
    this(width, height, Vector.fill(width * height)(defaultVal), defaultVal)
  }

  def updated[B >: A](x: Int, y: Int, elem: B): FixedVector2D[B] = {
    val newV = v.updated(indexOf(x, y), elem)
    new FixedVector2D[B](width, height, newV, emptyVal)
  }

  def indexOf(x: Int, y: Int): Int = x * width + y

  def apply(x: Int, y: Int): A = v(indexOf(x, y))


  def to2DMap: Map[(Int, Int), A] =
    Map((for {
      (a, z) <- v.zipWithIndex
      x: Int = z / width
      y: Int = z % width
    } yield ((x, y), a)): _*)
}
