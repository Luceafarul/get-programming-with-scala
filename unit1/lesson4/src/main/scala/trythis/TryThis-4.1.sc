// Change the code of your mark function to keep track of both the lowest and
// the highest mark computed so far. 


var marksSum = 0 	//#A
var marksCount = 0	//#A

var minMark = 100
var maxMark = -100

def averageMark: Double =
  marksSum.toDouble / marksCount //#B

def mark(q1: Double, q2: Double, q3: Double): Int = {
  val avgScore = (q1 + q2 + q3) / 3
  val scaledScore = avgScore * 10 / 3
  val mark = Math.round(scaledScore).toInt

  marksSum += mark
  marksCount += 1

  trackMinMax(mark)

  mark //#C
}

def trackMinMax(mark: Int) = {
  if (mark < minMark) minMark = mark
  if (mark > maxMark) maxMark = mark
}
