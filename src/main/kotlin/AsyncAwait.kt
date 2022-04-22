import kotlinx.coroutines.*
import java.util.*
import kotlin.system.measureTimeMillis
/*

//  APPROACH 1:Synchronous execution
-doWork1 and doWork2 suspend fns runs one after the other.

fun main()= runBlocking{
     val time=measureTimeMillis {
        val res1 = doWork1()
        val res2 = doWork2()
        println("Result is:${res1 + res2}")
    }
    println("Took: $time to run")
}

suspend fun doWork1():Int{
    delay(100)
    println("Do Work-1")
    return Random(System.currentTimeMillis()).nextInt(12)
}

suspend fun doWork2():Int{
delay(200)
    println("Do Work-2")
    return Random(System.currentTimeMillis()).nextInt(42)
}
 */



/*
//APPROACH 2:Here we are using Launch Coroutine builder-
-Time taken is almost similar to that of APPROACH 1
ie doWorks fn ain't running  concurrently-:
-Running the doWork fns inside a coroutine doesn't make them asynchronous

fun main()= runBlocking{

    val job=launch {
       val time=measureTimeMillis {
           val res1 = doWork1()
           val res2 = doWork2()
        println("Result is:${res1 + res2}")
    }

    println("Took: $time to run")
}

    job.join()//to wait for the coroutine to finish
}

suspend fun doWork1():Int{
    delay(100)
    println("Do Work-1")
    return Random(System.currentTimeMillis()).nextInt(12)
}

suspend fun doWork2():Int{
    delay(200)
    println("Do Work-2")
    return Random(System.currentTimeMillis()).nextInt(42)
}
*/



/*
APPROACH 3:ASYNC-WAIT
-To improve Approach 2:Run each suspend fn within it own coroutine fn and get the result of that coroutine back and use that result.
-We can wrap @doWork inside a launch, get the result from that launch,store it elsewhere and then add those results

--IDEAL WAY: use async builder ie.It will call doWork1 and 2 asynchronously

-Launch would just have worked just fine but with ASYNC builder the adv is:
 It returns a Deferred object that we can wait on AND we can use that deferred object to get the data

-Observation is that BY APPROACH 3 ,Time taken is as long as the longest execution time of a method call -->Better than previous Approaches 1&2
 */
fun main()= runBlocking{

    val job=async{
        val time=measureTimeMillis {
            val res1 =async {  doWork1()}//async coroutine builder in action.It will return a Deferred object
            val res2:Deferred<Int> = async {  doWork2()}

            println("Result is:${res1.await()//using await to get the returned data from the coroutine
                    + res2.await()}")//Here, we are calling await() on Deferred object
        }

        println("Took: $time to run")
    }

    //job.join()
}

suspend fun doWork1():Int{
    delay(100)
    println("Do Work-1")
    return Random(System.currentTimeMillis()).nextInt(12)
}

suspend fun doWork2():Int{
    delay(200)
    println("Do Work-2")
    return Random(System.currentTimeMillis()).nextInt(42)
}