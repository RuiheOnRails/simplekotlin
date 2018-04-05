// Explore a simple class
// Ruihe (Jerry) Li
// INFO 448

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String
/*
    "Howdy" to "Say what?",
    "Bonjour */
fun whenFn(a: Any): String {
    when (a) {
        "Hello" -> return "world"
        "Howdy", "Bonjour" -> return "Say what?"
        0 -> return "zero"
        1 -> return "one"
        in 2..10 -> return "low number"
        is Int -> return "a number"
        else -> return "I don't understand"
    }   

}
// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(a: Int, b: Int): Int{
    return a + b
}
// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(a: Int, b: Int): Int {
    return a - b
}
// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(a: Int, b: Int, op: (c: Int, d: Int) -> Int) : Int{
    return op(a,b)
}
// write a class "Person" with first name, last name and age
/*
The third section is to explore classes. You are to create a standard "POJO"-type class called "Person", which should have three properties (firstName, a String; lastName, a String; and age, an Int), provide a constructor that takes all three properties as arguments, provides an "equals" implementation that tests whether two Persons hold the same values, and an appropriate "hashCode" implementation. (See "Effective Java", Item 9, for details if you've never seen this before.) Define a read-only "debugString" property on it that returns a String containing the Person data in a format like this: "[Person firstName:Ted lastName:Neward age:45]".
 */
class Person(firstName: String, lastName: String, age: Int){
    var firstName : String
    var lastName: String
    var age : Int
    val debugString : String 
        get() { 
            return "[Person firstName:$firstName lastName:$lastName age:$age]"
        }

    init{
        this.firstName = firstName
        this.lastName = lastName
        this.age = age
    }

    override fun equals(other: Any?): Boolean{
        if (this === other &&
        this.firstName == other.firstName &&
        this.lastName == other.lastName &&
        this.age == other.age) 
        return true
        return false
    }

    override fun hashCode(): Int{
        return debugString.hashCode()
    }
}
// write a class "Money"
class Money(amount: Int, currency: String) {
    var amount : Int
    var currency: String
    val conversionTable:HashMap<String, Double> = hashMapOf("GBP" to 0.5, "USD" to 1.0, "CAN" to 1.25, "EUR" to 1.5)

    init{
        if (amount >= 0) {
            this.amount = amount
        } else {
            this.amount = 0
        }

        if (currency == "USD" || currency == "GBP" || currency == "EUR" || currency == "CAN") {
            this.currency = currency
        } else {
            this.currency = "USD"
        }
    }

    fun convert(to : String): Money {
        if (to == "USD" || to == "GBP" || to == "EUR" || to == "CAN"){
            val divide = this.conversionTable[this.currency]
            val multi = this.conversionTable[to]
            return Money((this.amount.toDouble()/divide!!*multi!!).toInt(), to)
        }
        return this
    }

    operator fun plus(other: Money): Money {
        val converted = other.convert(this.currency)
        return Money(this.amount + converted.amount, this.currency)
    }
}

// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
