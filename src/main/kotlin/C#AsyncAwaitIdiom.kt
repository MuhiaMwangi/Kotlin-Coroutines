//MORAL LESSON:
// Wrap the method inside an async{} block, when you are calling it and INSTEAD OF using  the async{} block when defining the method

import kotlinx.coroutines.*

fun main()=runBlocking<Unit>{
    val result: Deferred<Int> =doWorkAsync("WORK1")
    val answer: Int = result.await()
    println("Answer is:$answer")

    //BETTER APPROACH 2
    //This is the freedom we were talking about down in the method description
    //in res1- we called doWorkMethod synchronously(called just like a normal fn)
    //in res2- we called doWorkMethod asynchronously(called in a concurrent fashion)
    //as you can see there is that FREEDOM OF CHOICE--Best practice & Common idiom
    val res1: Int =doWorkMethod("WORK2")
    val res2: Deferred<Int> =async{doWorkMethod("WORK3")}//way 2 to call
    res2.await()


}


//APPROACH 1
//Here we are the method asynchronously
//This method ain't right since
// 1.we are breaking structured concurrency principles by using GlobalScope
// 2. We are specifically saying that the method MUST run asynchronously which is not always the case
fun doWorkAsync(msg: String)= GlobalScope.async{
    log1("$msg-Working")
    delay(500)
    log1("$msg-Work Done")
    return@async 42
}


//BETTER APPROACH 2
//Here we have the flexibility or choice or freedom of either running the method asynchronously or synchronously
//We have also tackled the issue where we were breaking structured concurrency principles
suspend fun doWorkMethod(msg: String):Int{
    log1("$msg-Working")
    delay(500)
    log1("$msg-Work Done")
    return 42
}
fun log1(msg:String):Unit{
    println("$msg in ${Thread.currentThread().name}") }


//CONCLUSION:
// If you want work to run concurrently and get data back from it, wrap it in the async coroutine builder.
// Don't make it explicitly asynchronous.
//IN SIMPLE WORDS:wrap the method inside an async{} block, when you are calling it and INSTEAD OF using  the async{} block when defining the method