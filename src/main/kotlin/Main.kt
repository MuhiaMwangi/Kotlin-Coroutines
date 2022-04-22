import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private fun log(msg:String)= println("[${Thread.currentThread().name} :$msg]")

fun main(){
    networkRequest()
}
private fun networkRequest(){
    GlobalScope.launch{
        log("Making the network request")
        for(i in 1..3){
            delay(1000)
            println("First: $i")
        }
        log("First network request made!")

    }
    Thread.sleep(4000   )
    log("Done!")
}

//  org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.0