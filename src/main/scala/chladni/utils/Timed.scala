package chladni.utils

object Timed {
  def apply[A](name: String)(block: => A): A = {
    val start = System.currentTimeMillis()
    val res   = block
    val end   = System.currentTimeMillis()
    println(s"$name took ${end - start} ms to run")
    res
  }
}
