import Sex.Male
import org.joda.time.Days

object Solution {
  def main(args: Array[String]): Unit = {
    val filename = "./AddressBook"

    AddressBook.loadFile(filename) match {
      case Left(err) => println(s"problems loading addressbook:\n$err")
      case Right(book) => solutions(book)
    }
  }

  def solutions(book: Seq[AddressBookEntry]): Unit = {
    // How many males are in the address book?
    // select where male, count
    val maleCount = book.filter(_.sex == Male).length
    println(s"1. $maleCount")

    // Who is the oldest person in the address book?
    // order by birth date, take 1
    // what if multiple people have the same age?
    // find earliest date
    // find all entries with that birthdate
    val date = book.map(_.dob).sortBy(_.getMillis).distinct.headOption

    date match {
      case Some(earliest) => {
        val oldestPeople = book.filter(_.dob == earliest)
        val oldestCount = oldestPeople.length
        println(s"2. number of oldest people: $oldestCount")
        oldestPeople.foreach(println)
    }
      case None => println("2. no data to determine oldest person")
    }

    // How many days older is Bill than Paul?
    // find bill
    // find paul
    // find difference in age
    // what if we have multiple bill & pauls?
    // what if one or both don't exist?
    val combos: Seq[(AddressBookEntry,AddressBookEntry)] = for {
      b <- book.filter(_.name.startsWith("Bill "))
      p <- book.filter(_.name.startsWith("Paul "))
    } yield (b, p)

    val results = combos.map( {case (b: AddressBookEntry, p: AddressBookEntry) => {
      if (b.dob.getMillis < p.dob.getMillis) {
      val diff = Days.daysBetween (b.dob.toLocalDate (), p.dob.toLocalDate () ).getDays ()
      s"${b.name} is $diff days older than ${p.name}"
    } else {
      s"${b.name} is not older than ${p.name}"
    }}})

    println(s"3. numberof combinations of Bill and Paul: ${combos.length}")
    results.foreach(println)
  }
}


