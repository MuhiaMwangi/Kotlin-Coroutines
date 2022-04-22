fun main(){
    //Lists,Maps and Sets:Collections
    // Maps can be read only or mutable types
    //Mutable types ->Can be modified after creation
    //Read-Only:Can't be modified after you create it


    //--mapOf() can have only one value for one key item

    //Map--> Key/Value Pairs
    //Read-0nly Maps
    val ageMapper= mapOf("Joe" to 34,"Mary" to 19,"Henry" to 14,"Mercy" to 22, "Claire" to 54,"Lewis" to 4,"Abbey" to 20,"Doe" to 30)
    println( ageMapper)
    println( ageMapper["Joe"])//We are accessing age value after passing the
    println(ageMapper.size)//to get the size of the ageMapper map
    println(ageMapper.count())
    println(ageMapper.count{it.value >23 })//Custom use of count:We are counting the number of items based on a certain cdn
    //'it' represents an individual item inside ageMapper map

    //Mutable Maps--Can be modified after creation
    val mutableAgeMapper=mutableMapOf("Joe" to 34,"Mary" to 3,"Henry" to 3,"Mercy" to 22, "Claire" to 54,"Lewis" to 4,"Abbey" to 32,"Doe" to 30)
    mutableAgeMapper.put("Felix", 32)
    println(mutableAgeMapper)
    // mutableAgeMapper.clear()//Clear all map
    mutableAgeMapper.remove("Claire")//Remove a particular item
    mutableAgeMapper.containsKey("Felix")//Checking if  certain value or key is present int the map.It returns true or false
    mutableAgeMapper.containsValue(32)
    mutableAgeMapper.filter { it.value==23|| it.key=="Lewis" } //filtering our map to only display items that have a 'value' of 23 OR items that have 'key' as Lewis
    //'||': means OR
    mutableAgeMapper.filter { it.value==33}//Only 'Keys' will be printed
    mutableAgeMapper.filter { it.key=="Lewis" }//Only 'value' will be printed
    mutableAgeMapper.toSortedMap()
    mutableAgeMapper.get("fhdjksl")//Throws exception since its not in our map
    mutableAgeMapper.getOrDefault("fhdjksl",-1)//If the item is not in the map, the default value will be returned avoiding exception  being thrown
    // mutableAgeMapper.getOrElse()
    mutableAgeMapper.keys//Get list of all Keys
    mutableAgeMapper.values//Get list of all the values
    mutableAgeMapper.filterNot { it.key =="Henry"}
}
/*
If two values have same key value , then the map will contain the last value of the those numbers.


fun main(args: Array<String>)
{
    val map = mapOf(1 to "geeks1",
                    2 to "for",
                    1 to "geeks2")
println("Entries of map : " + map.entries)
    }

OUTPUT: Entries of map : [1=geeks2, 2=for]
REASON:
Here key value 1 has been initialized with two values : geeks1 and geeks2,
but as we know that mapOf() can have only one value for one key item,
therefore the last value is only stored by the map and geeks1 is eliminated.
            */