import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args:Array<String>){
    GlobalScope.launch {
        delay(1000)//non-blocking code:It only suspends the coroutine,
        // ...hence the thread that the coroutine was using can be used to schedule other tasks.
        //launch(Coroutine builder) allows us to execute code in this 'non-blocking world' whereby the coroutine only suspended and not block
        print(" World")
    }

    print("Hello")
    Thread.sleep(1500)//blocking code

}