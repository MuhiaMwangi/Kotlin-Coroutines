import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main()=runBlocking{
    //launch has a return type of job
    // we use job object that is returned to join the coroutine

    val job: Job=launch{
        delay (1000)
        println("World")
    }

    println("Hello")
    // delay(1500)----delay() is not a good option since we might not know for how long the coroutine will...
    // ...take to execute(wait for 500ms while coroutine take lets say 1000ms) hence need to use join()
    //using job object that is returned from Coroutine to 'join' the coroutine
    // 'join' will check to see if our coroutine is finished executing and then the necessary step can...
    // be then taken
    job.join()
    //Joining the coroutine, as in, making the caller code(Main thread) to BLOCK and wait for coroutine results
}


//CONCLUSION:
/*By using join we(as main thread)are saying to the coroutine to execute and comeback when done,
to calling code(Main) so that it can continue doing what it was doing*/
