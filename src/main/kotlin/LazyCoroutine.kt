import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
/*
class Person
{
    val children =loadChildren()

    companion object{
        fun loadChildren():List<String>{
            println("Loading children")
            Thread.sleep(4000)//Simulating a database /network call
            return listOf("Joe","Glen","Reus")
        }
    }
}
fun main(){
    println("Creating Person")

   val time= measureTimeMillis {
       val parentKevin = Person()
   }
    println("Person created in $time ms")
}CONCLUSION:loadChildren method costs us time -4048ms by executing BUT we didn't need it,we havent called it in our code
/*
OUTPUT:

Creating Person
Loading children
Person created in 4048 ms

Process finished with exit code 0
 */
*/

/*
class Person
{
    val children by lazy{loadChildren()}//don't call loadChildren until we need to
//by lazy{} -->Means that we only initialize the data when we need to use it
    companion object{
        fun loadChildren():List<String>{
            println("Loading children")
            Thread.sleep(4000)//Simulating a database /network call which would take a lot of time
            return listOf("Joe","Glen","Reus")
        }
    }
}
fun main(){
    println("Creating Person")

    val time= measureTimeMillis {
        val maryClaire = Person()
        maryClaire.children.forEach{println(it)}
    }
    println("Person created in $time ms")

}

*/
/*
OUTPUT:
Creating Person
Person created in 46 ms

 */
/*
//When we use by lazy in the ABOVE CODE,we dont load children until need to
//This has led to significant reduction in execution time-46ms


class Person
{
    val children by lazy{loadChildren()}//cant call loadChildren() inside by lazy{}

    companion object {
    //catch: loadChildren method becomes a suspend fn
        suspend fun loadChildren():List<String>{//ERROR
            println("Loading children")
            Thread.sleep(4000)
            return listOf("Joe","Glen","Reus")
        }
    }
}
fun main(){
    println("Creating Person")

    val time= measureTimeMillis {
        val maryClaire = Person()
        maryClaire.children.forEach{println(it)}
    }
    println("Person created in $time ms")

}
//In above code executes in about 4000ms and its justifiable since we are using the loadChildren method
//We have also decided to make loadChildren a suspend function
//by making loadChildren fn a suspend fn, it means it has to be run inside a coroutine.
//loadChildren fn returns data and so in that case we have to use async coroutine builder
--The Code below demonstrates how we go about everything-:Late Initialization, suspend fn & async coroutine builder relation
*/

class Person
{
    //Marking our async builder with CoroutineStart parameter
    //  'CoroutineStart.LAZY' means that: Set the coroutine up and ready to go, but only start it when we need it.
    val children: Deferred<List<String>> = GlobalScope.async (start=CoroutineStart.LAZY){loadChildren()}
    companion object {

        suspend fun loadChildren():List<String>{
            println("Loading children")
            delay(4000)
            return listOf("Joe","Glen","Reus")
        }
    }
}

fun main()= runBlocking{
    println("Creating Person")

    val time= measureTimeMillis {
        val parentKevin = Person()
        parentKevin.children.start()//starting the coroutine.
        // Ideally we usually start a coroutine before we need it : so that it can go and do its work and by time we are calling it has finished its work/almost done
        Thread.sleep(2000)//this meant to allow for some time for coroutine with loadChildren to execute


        //async coroutine builder returns a Deferred object hence the need to call await()
        //its by calling await() on a deferred object that we are able to get data :if the coroutine has already finished executing returns the dat immediately or it will block and wait for the coroutine to finish and then return the data
        parentKevin.children.await().forEach{println(it)}
    }
    println("Person created in $time ms")


    //CONCLUSION:'CoroutineStart.lAZY' is as a replacement for 'by lazy' if the fn we want to call to initialize the data is a suspend function

}

