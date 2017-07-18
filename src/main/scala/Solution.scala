object Solution {
  def main(args: Array[String]): Unit = {
    val filename = "AddressBook"

    AddressBook.loadFile(filename) match {
      case Left(err) => println(s"problems loading addressbook:\n$err")
      case Right(book) => solutions(book)
    }
  }

  def solutions(book: AddressBook): Unit = {
    println("print solutions to problems here")
  }
}
