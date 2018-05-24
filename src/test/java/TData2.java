import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TData2 {
        private TData1 td1;
        private ArrayList<TData1> td1List;
        private String date;

        public TData2() {
        }

        public TData2(TData1 td1, ArrayList<TData1> td1List, Date date) {
            this.td1 = td1;
            this.td1List = td1List;
            this.date = SimpleDateFormat.getInstance().format(date);
        }

    public Date getDate() {
        try {
            return SimpleDateFormat.getInstance().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}