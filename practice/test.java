import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class test {

  static String getTime() {
    Date now = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss yyyy Z");
    sdf.setTimeZone(TimeZone.getTimeZone("GMT-08:00"));
    return sdf.format(now);
  }

  public static void main(String args[]) {
    System.out.println(getTime());
  }

}
