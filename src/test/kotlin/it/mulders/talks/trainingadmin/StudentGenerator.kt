package it.mulders.talks.trainingadmin

import net.datafaker.Faker

fun main(args: Array<String>) {
    val faker = Faker()
    val count = args[0].toInt()

    (1..count).forEach {
        val name = faker.name()
        val firstName = name.firstName()
        val lastName = name.lastName()

        val domainName = faker.company().url().replace("""www.""", "")
        val email = "${firstName.lowercase()}.${lastName.lowercase()}@$domainName"

        println("(uuid(), '$lastName', '$firstName', '$email'),");
    }
}
