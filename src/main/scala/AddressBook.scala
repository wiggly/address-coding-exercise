import java.util.Date

abstract class Sex
case class Male() extends Sex
case class Female() extends Sex

class AddressBookEntry(val name: String, val sex: Sex, val dob: Date)

object AddressBook {
  def loadFile(filename: String): Either[String,Seq[AddressBookEntry]] = Left("FAIL")
}
