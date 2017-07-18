object Solution {
  def main(args: Array[String]): Unit = {
    val filename = "AddressBook"

    AddressBook.loadFile(filename) match {
      case Left(err) => println(s"problems loading addressbook:\n$err")
      case Right(book) => solutions(book)
    }
  }

  def solutions(book: Seq[AddressBookEntry]): Unit = {
    println("print solutions to problems here")

    // How many males are in the address book?
    // select where male, count
    println("1. unsolved")

    // Who is the oldest person in the address book?
    // order by birth date, take 1
    // what if multiple people have the same age?
    println("2. unsolved")

    // How many days older is Bill than Paul?
    // find bill
    // find paul
    // find difference in age
    // what if we have multiple bill & pauls?
    // what if one or both don't exist?
    println("3. unsolved")
  }
}


