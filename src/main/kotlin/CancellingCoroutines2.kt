
import kotlinx.coroutines.*

/*There are 2 options when cancelling coroutine
* 1. Use a built-in suspend fn(since all built in suspend fn are cooperative)to check for cancellation-->use yield() built-in suspend fn.
* 2. Explicitly inspect cancellation state(cancellation property of Job object) inside our coroutine*/

//OPTION 2:Explicitly inspect cancellation state
fun main(args: Array<String>)= runBlocking{

    val job: Job =launch {
        repeat(1000) {
            if (!isActive) throw  CancellationException()
            //Here, we are checking the 'isActive flag' which is inside the coroutine by ourself: without aid of suspend fns
            //if this property shows its inactive,we throw an exception to tell the coroutine builder to tell the coroutine infrastructure that we've been cancelled.
            //and subsequently cancel the coroutine.
            print(".")
            Thread.sleep(100)
        }
    }
    delay(100)
    job.cancelAndJoin()
    println("DONE!")
}