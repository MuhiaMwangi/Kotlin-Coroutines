import kotlinx.coroutines.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.Executors.newCachedThreadPool
val scope: CoroutineScope = CoroutineScope(Job())
val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
val executor = Executors.newFixedThreadPool(10)

//Launching multiple coroutines using different dispatchers
//We then add all these coroutines to a list then 'wait on' the list to finish
//Within the coroutine we print the name of the DISPATCHER and THREAD that the coroutine is running on


fun main(args:Array<String>):Unit=runBlocking{

    val jobs= arrayListOf<Job>()//many jobs i.e list of jobs
//jobs+= is  adding a job to the jobs-list AFTER launching it
    jobs+=launch{
        // name of the DISPATCHER and THREAD that the coroutine is running on being displayed
        println("'default': In thread ${Thread.currentThread().name}")
    }

    jobs+=launch(Dispatchers.Default){
        println("'Default: In thread ${Thread.currentThread().name}")
    }
    jobs+=launch(Dispatchers.IO){
        println(" 'IO Dispatcher': In thread ${Thread.currentThread().name}")
    }
    jobs+=launch(Dispatchers.Unconfined){
        println("'Unconfined Dispatcher': In thread ${Thread.currentThread().name}")
    }
    //newSTC Dispatcher launches all the coroutines inside the dispatcher onto a single thread meaning that those coroutines will always run one after the other
    jobs+=launch(newSingleThreadContext("ownThread")){//will get its own Thread
        println("'newSTC':  In thread ${Thread.currentThread().name}")
    }

    jobs+=launch(dispatcher){
        println("'dispatcher': In thread ${Thread.currentThread().name}")
    }

    jobs+=launch(executor.asCoroutineDispatcher()){
        println("'executor': In thread ${Thread.currentThread().name}")
    }

    //using join() to wait on the list
    jobs.forEach{it->it.join()}
    println()
    println()

    Thread.sleep(100)

    //job1.join()

    executor.shutdownNow()
    dispatcher.close()
}
