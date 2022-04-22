import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread

fun main(){
    val results= AtomicInteger()
    for (i in 1..1_500_000) {

        GlobalScope.launch{
            results.getAndIncrement()
        }
    }
    Thread.sleep(1000)
    println(results.get())
}

//Conclusion: We can create millions of coroutines since they are lightweight than threads are