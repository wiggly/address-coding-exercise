import Sex.SexValue
import com.github.tototoshi.csv.CSVReader
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

import scala.util.Try

object Sex {
  sealed trait SexValue
  case object Male extends SexValue
  case object Female extends SexValue
  def fromString(str: String): Option[SexValue] = {
    str match {
      case "Male" => Some(Male)
      case "Female" => Some(Female)
      case _ => None
    }
  }
}

class AddressBookEntry(val name: String, val sex: SexValue, val dob: DateTime) {
  override def toString: String = {
    s"name: $name - sex: $sex - dob: $dob"
  }
}

object AddressBookEntry {
  val dateFormat: DateTimeFormatter = DateTimeFormat.forPattern("dd/MM/yy")

  def fromCsvRow(row: Seq[String]): Either[String,AddressBookEntry] = {
    for {
      // TODO: Left("bad entry") if we don't have enough fields
      name <- Right(row(0))
      sex <- Sex.fromString(row(1).trim).toRight(s"Unknown sex '$row(1)'")
      dob <- Right(dateFormat.parseDateTime(row(2).trim))
    } yield new AddressBookEntry(name, sex, dob)
  }
}

object AddressBook {
  def loadFile(filename: String): Either[String,Seq[AddressBookEntry]] = {
    val reader = CSVReader.open(filename)

    val entries = reader.iterator.map { e: Seq[String] => AddressBookEntry.fromCsvRow(e) }

    println(entries.toSeq)

    entries.foreach(println)

    Right(Seq.empty)
  }
}
