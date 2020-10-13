import game._

val height = 8
val width = 16

val p1 = BoardConfigurations.firstPlayerMappings
val p2 = BoardConfigurations.secondPlayerMappings

val mMap = BoardConfigurations.mergeMappings(p1, p2)

def f(pair: ((Int, Int), Square)): (Int, Square) = pair match {
  case ((x: Int, y: Int), s: Square) => (x * width + y, s)
}


val fMap = mMap map {case ((x: Int, y: Int), s: Square) => (x * width + y, s)}


val tempEmptyPlacements =
  for {
    i <- 0 to height * width
  } yield (i, EmptySquare)

val emptyMap: Map[Int, Square] = Map(tempEmptyPlacements:_*
)


val filledMap = emptyMap ++ fMap // merges maps, prefers the elements in the right one

val sortedVec = filledMap.toVector.sortWith({ case ((lhs, rhs)) => lhs._1 < rhs._1})
val v = sortedVec map {case (i, s) => s}
v filter (_ != EmptySquare)

//for {
//  (i: int, s: square) <- fmap
//}

