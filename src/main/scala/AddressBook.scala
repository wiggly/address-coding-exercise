import Sex.SexValue
import com.github.tototoshi.csv.CSVReader
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

import cats.implicits._

import scala.util.{Failure, Success, Try}

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
    if (row.length != 3) {
      Left(s"row does not have 3 fields '$row'")
    } else {
      for {
        name <- Right(row(0).trim)
        sex <- Sex.fromString(row(1).trim).toRight(s"Unknown sex '$row(1)'")
        dob <- parseDate(row(2).trim)
      } yield new AddressBookEntry(name, sex, dob)
    }
  }

  def parseDate(str: String): Either[String,DateTime] = {
    Try(dateFormat.parseDateTime(str)) match {
      case Success(d) => Right(d)
      case Failure(ex) => Left(ex.getMessage)
    }
  }
}

object AddressBook {
  def loadFile(filename: String): Either[String,Seq[AddressBookEntry]] = {
    try {
      CSVReader.open(filename).all.traverseU(e => AddressBookEntry.fromCsvRow(e))
    } catch {
      case ex: Exception => Left(s"Cannot open file: $filename")
    }
  }
}
