import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main(){
    val results= AtomicInteger()
    for (i in 1..15_000) {
        thread(start = true) {
            results.getAndIncrement()
        }
    }

    Thread.sleep(1000)
    println(results.get())
}