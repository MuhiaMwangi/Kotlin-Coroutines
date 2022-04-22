import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main()=runBlocking{

    launch {
        delay(1000)
        println("World")
    }

    print("Hello")
    doWork()
}
suspend fun doWork(){
    delay(1500)
}

//The use of runBlocking in this scenario is purely idiomatic
//You will rarely/Never use it such

//We are able to call the suspend fn(doWork) from main fn since main have been marked with runBlocking-making it a coroutine
//As you already know, SUSPEND FNs(doWork) would only run inside a coroutine...
// shhh{and within suspend fn too}-->Our Little secret fn

