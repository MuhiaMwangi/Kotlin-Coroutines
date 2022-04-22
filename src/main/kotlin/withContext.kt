import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Executors.newCachedThreadPool
/*
withContext:
-It's the responsibility of the suspend fn to dispatch a coroutine correctly
-This is achieved by calling the withContext API
-It's the doWork() suspend fn task to decide on which Dispatcher the coroutine will be run on
 */
val dispatcher1= Executors.newCachedThreadPool().asCoroutineDispatcher()
val executor1 = Executors.newFixedThreadPool(10)

fun main()=runBlocking{

    val jobs= arrayListOf<Job>()

//Here we launch a coroutine, run a suspend function.
    jobs+=launch(){
        //the suspend fn the decides for itself whether it should run itself on a different dispatcher
        doWork("default")
    }

    jobs+=launch(Dispatchers.Default){
        doWork("Default dispatcher")
    }
    jobs+=launch(Dispatchers.IO){
        doWork("Dispatcher IO")
    }
    jobs+=launch(Dispatchers.Unconfined){
        doWork("Unconfined")
    }
    jobs+=launch(executor1.asCoroutineDispatcher()){
        doWork("fixedThreadPool")
    }
    jobs+=launch(dispatcher1){
        doWork("cachedThreadPool")
    }
    jobs+=launch(newSingleThreadContext("newSTC")){
        doWork("newSTC")
    }
    //using join() to wait on the list
    jobs.forEach{it->it.join()}
    println()
    println()

    executor1.shutdownNow()
    dispatcher1.close()
}

suspend fun doWork(dispatcherName: String){
    // using withContext to switch onto a specific dispatcher
    //Regardless of the dispatcher we were launched on, we are always running on the IO dispatcher
    //-REMEMBER: Default and IO dispatchers share the same thread pool
    withContext(Dispatchers.IO) {
        println("$dispatcherName: (But) in thread ${Thread.currentThread().name}")
    }
}

/*
//OUTPUT withContext:
-Regardless of the dispatcher we were launched on, we are always running on the IO dispatcher
-IO Dispatcher is labeled as the default dispatcher()-->Just as structured concurrency advocates
 -REMEMBER: Default and IO dispatchers share the same thread pool

Unconfined: (But) in thread DefaultDispatcher-worker-2
Default dispatcher: (But) in thread DefaultDispatcher-worker-3
Dispatcher IO: (But) in thread DefaultDispatcher-worker-1
fixedThreadPool: (But) in thread DefaultDispatcher-worker-1
cachedThreadPool: (But) in thread DefaultDispatcher-worker-2
newSTC: (But) in thread DefaultDispatcher-worker-4
default: (But) in thread DefaultDispatcher-worker-2
*/


/*
//OUTPUT normal:
-It's the calling coroutine that's determining the dispatcher -This goes against structured concurrency principles
-REMEMBER: Default and IO dispatchers share the same thread pool

cachedThreadPool: (But) in thread pool-1-thread-1
fixedThreadPool: (But) in thread pool-2-thread-1
Dispatcher IO: (But) in thread DefaultDispatcher-worker-1
Unconfined: (But) in thread main
Default dispatcher: (But) in thread DefaultDispatcher-worker-1
newSTC: (But) in thread newSTC
default: (But) in thread main

*/

/*STRUCTURED CONCURRENCY Recommendation: Ideally the calling code should launch its coroutine on whichever dispatcher
it was launched on,and then it's up to the called suspend function to choose the appropriate dispatcher*/