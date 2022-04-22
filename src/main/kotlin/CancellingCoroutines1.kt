import kotlinx.coroutines.*

/*There are 2 options when cancelling coroutine
* 1. Use a built-in suspend fn(since all built in suspend fn are cooperative)to check for cancellation-->use yield() built-in suspend fn.
* 2. Explicitly inspect cancellation state(cancellation property of Job object) inside our coroutine*/

//OPTION 1:Use a built-in suspend fn
fun main(args: Array<String>)=runBlocking{

    val job: Job =launch {
        repeat(1000) {
            //yield()
            delay(100) //delay() is a built-in suspend fn:It manages cancellation SINCE it knows how to process cancellation
            // it is cooperative-just like all other built-in suspend fns

            print(".")
        }
    }

    delay(2500)//gives us time to have displayed several dots(for illustration purposes),before waking and continuing to this next line of cancelling that coroutine
    // job.cancel()   //We are cancelling from main thread, but cancellation is cooperative hence we don't know if coroutine will cooperate to cancel
    //job.join()     //join --> Waits for cancellation which takes place inside the delay suspend fn inside the coroutine to succeed
    // inside the job be cancelled inside the coroutine
    job.cancelAndJoin()
    println("DONE!")
}