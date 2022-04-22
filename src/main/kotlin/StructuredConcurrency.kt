
import kotlinx.coroutines.*
/*
//Our task at hand is to demonstrate structured concurrency best practice: suspend function should not return before having completed its work

fun main()=runBlocking<Unit>{
    launch {
        runWithGlobalScope() //Here we are calling this suspend fn which is going to launch 2 coroutines in GlobalScope
        println(" runWithGlobalScope returned")
}
}

suspend fun runWithGlobalScope() {
    GlobalScope.launch {
        println ("Launch 1") //A display msg to show to us that this coroutine is running.
                             //It is the Simulated work that the coroutine is purported to be doing
        delay(1000)
        }

    GlobalScope.launch {
          println ("Launch 2")//Simulated work that will be done by the coroutine
          delay(2000)}

    println(" runWithGlobalScope finished")//A display msg to show that the runWithGlobalScope suspend fn has finished executing and is returning
   //Our task at hand is to demonstrate structured concurrency best practice: suspend function should not return before having completed its work
    }


    /*OUTPUT 1:
Launch 1
runWithGlobalScope finished
runWithGlobalScope returned
Launch 2


Process finished with exit code 0

*/
*/

//Demonstrating how coroutineScope helps us manage a group of coroutines together--> It HELPS US ENSURE A SUSPEND FN ONLY RETURNS AFTER FINISHING ALL ITS WORK

fun main()=runBlocking<Unit>{
    launch {
        runWithLocalScope()
        println(" runWithLocalScope returned")}
}
suspend fun runWithLocalScope(){
    //We have wrapped our 2 coroutines inside a Coroutine Scope using coroutineScope{} builder
    //WObserve that: Only when coroutine-Scope ends(i.e When both coroutines inside coroutine scope executes fully), thats only when our suspend fn returns
    //This in line with structure concurrency best practices: suspend fN should not return before having completed its work
    //By using Coroutine scope we are able to decompose our code into parallel coroutines and coroutineScope waits until all coroutines have finished b4 the coroutineScope finishes and therefore, our suspend fn doesnt return until all its work is finished.
    coroutineScope{
        launch{
            println ("Launch 1")
            delay(1000)
        }

        launch {
            println ("Launch 2")
            delay(2000)
        }
    }
    println(" runWithLocalScope finished")
}
/*OUTPUT 2:
Launch 1
Launch 2
runWithLocalScope finished
runWithLocalScope returned

Process finished with exit code 0
*/